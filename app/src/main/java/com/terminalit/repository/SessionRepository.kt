package com.terminalit.repository

import com.terminalit.data.HostKeyStore
import com.terminalit.model.ConnectionConfig
import com.terminalit.model.TerminalBuffer
import com.terminalit.model.TerminalSnapshot
import com.terminalit.ssh.HostKeyInfo
import com.terminalit.ssh.SshEvent
import com.terminalit.ssh.SshManager
import com.terminalit.terminal.AnsiParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

sealed class SessionState {
    data object Disconnected : SessionState()
    data object Connecting : SessionState()
    data object Connected : SessionState()
    data class Error(val message: String) : SessionState()
}

data class HostKeyRequest(
    val info: HostKeyInfo,
    val isKeyChanged: Boolean,
    val onAccept: () -> Unit,
    val onReject: () -> Unit
)

@Singleton
class SessionRepository @Inject constructor(
    private val hostKeyStore: HostKeyStore
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val sshManager = SshManager(hostKeyStore)
    private val terminalBuffer = TerminalBuffer()
    private val ansiParser = AnsiParser(terminalBuffer)

    private var sessionJob: Job? = null
    private var pendingConfig: ConnectionConfig? = null
    private val _currentConfig = MutableStateFlow<ConnectionConfig?>(null)
    val currentConfig: StateFlow<ConnectionConfig?> = _currentConfig.asStateFlow()

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    private val _terminalSnapshot = MutableStateFlow(terminalBuffer.snapshot())
    val terminalSnapshot: StateFlow<TerminalSnapshot> = _terminalSnapshot.asStateFlow()

    private val _hostKeyRequest = MutableSharedFlow<HostKeyRequest>(extraBufferCapacity = 1)
    val hostKeyRequest: SharedFlow<HostKeyRequest> = _hostKeyRequest.asSharedFlow()

    private val inputChannel = Channel<ByteArray>(Channel.BUFFERED)

    fun connect(config: ConnectionConfig) {
        if (_sessionState.value is SessionState.Connected) return
        pendingConfig = config
        _currentConfig.value = config

        _sessionState.value = SessionState.Connecting
        terminalBuffer.reset()

        sessionJob = scope.launch {
            sshManager.connectSession(config) { event ->
                when (event) {
                    is SshEvent.HostKeyVerificationNeeded -> {
                        _hostKeyRequest.emit(HostKeyRequest(
                            info = event.info,
                            isKeyChanged = false,
                            onAccept = {
                                scope.launch {
                                    pendingConfig?.let { cfg ->
                                        sshManager.acceptHostKeyAndSave(
                                            config = cfg,
                                            type = event.info.type,
                                            keyBytes = event.info.key
                                        ) { evt -> handleEvent(evt) }
                                    }
                                }
                            },
                            onReject = {
                                sshManager.disconnect()
                                _sessionState.value = SessionState.Disconnected
                            }
                        ))
                    }
                    is SshEvent.HostKeyChanged -> {
                        _hostKeyRequest.emit(HostKeyRequest(
                            info = event.info,
                            isKeyChanged = true,
                            onAccept = {
                                scope.launch {
                                    pendingConfig?.let { cfg ->
                                        sshManager.acceptHostKeyAndSave(
                                            config = cfg,
                                            type = event.info.type,
                                            keyBytes = event.info.key
                                        ) { evt -> handleEvent(evt) }
                                    }
                                }
                            },
                            onReject = {
                                sshManager.disconnect()
                                _sessionState.value = SessionState.Disconnected
                            }
                        ))
                    }
                    else -> handleEvent(event)
                }
            }
        }
    }

    private fun handleEvent(event: SshEvent) {
        when (event) {
            is SshEvent.Connected -> {
                _sessionState.value = SessionState.Connected
                startInputPipeline()
                startOutputPipeline()
            }
            is SshEvent.Error -> {
                _sessionState.value = SessionState.Error(event.message)
                sshManager.disconnect()
            }
            is SshEvent.Disconnected -> {
                _sessionState.value = SessionState.Disconnected
            }
            is SshEvent.Output -> { /* handled by output pipeline */ }
            is SshEvent.HostKeyVerificationNeeded -> { /* handled in connect() */ }
            is SshEvent.HostKeyChanged -> { /* handled in connect() */ }
        }
    }

    private fun startInputPipeline() {
        scope.launch {
            for (data in inputChannel) {
                try {
                    sshManager.write(data)
                } catch (e: Exception) {
                    _sessionState.value = SessionState.Error(e.message ?: "Write failed")
                }
            }
        }
    }

    private fun startOutputPipeline() {
        scope.launch {
            try {
                sshManager.readOutput { data ->
                    ansiParser.feed(data)
                    _terminalSnapshot.value = terminalBuffer.snapshot()
                }
            } catch (e: Exception) {
                if (sshManager.isConnected()) {
                    _sessionState.value = SessionState.Error(e.message ?: "Read error")
                }
                sshManager.disconnect()
            } finally {
                _sessionState.value = SessionState.Disconnected
            }
        }
    }

    fun sendInput(text: String) {
        scope.launch { inputChannel.send(text.toByteArray(Charsets.UTF_8)) }
    }

    fun sendRaw(data: ByteArray) {
        scope.launch { inputChannel.send(data) }
    }

    fun resizeTerminal(cols: Int, rows: Int) {
        terminalBuffer.resize(cols, rows)
        sshManager.resizePty(cols, rows)
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun scrollBy(offset: Int) {
        terminalBuffer.scrollBy(offset)
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun scrollToBottom() {
        terminalBuffer.scrollToBottom()
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun disconnect() {
        sessionJob?.cancel()
        sessionJob = null
        sshManager.disconnect()
        _sessionState.value = SessionState.Disconnected
    }

    fun cleanup() {
        disconnect()
        scope.cancel()
    }
}

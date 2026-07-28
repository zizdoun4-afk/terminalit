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
    private val _bellFlow = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val bellFlow: SharedFlow<Unit> = _bellFlow.asSharedFlow()

    private val ansiParser = AnsiParser(terminalBuffer) {
        _bellFlow.tryEmit(Unit)
    }

    private var sessionJob: Job? = null
    private var inputJob: Job? = null
    private var outputJob: Job? = null

    private val failedAttempts = java.util.concurrent.ConcurrentHashMap<String, Int>()
    private val lockoutUntil = java.util.concurrent.ConcurrentHashMap<String, Long>()

    private var pendingConfig: ConnectionConfig? = null
    private val _currentConfig = MutableStateFlow<ConnectionConfig?>(null)
    val currentConfig: StateFlow<ConnectionConfig?> = _currentConfig.asStateFlow()

    private val _sessionState = MutableStateFlow<SessionState>(SessionState.Disconnected)
    val sessionState: StateFlow<SessionState> = _sessionState.asStateFlow()

    private val _terminalSnapshot = MutableStateFlow(terminalBuffer.snapshot())
    val terminalSnapshot: StateFlow<TerminalSnapshot> = _terminalSnapshot.asStateFlow()

    private val _hostKeyRequest = MutableSharedFlow<HostKeyRequest>(extraBufferCapacity = 1)
    val hostKeyRequest: SharedFlow<HostKeyRequest> = _hostKeyRequest.asSharedFlow()

    // Sealed command type so the input pipeline can handle both data writes
    // and PTY resize requests on the same serialized IO thread.
    private sealed class IoCommand {
        data class Data(val bytes: ByteArray) : IoCommand()
        data class Resize(val cols: Int, val rows: Int) : IoCommand()
    }

    private val inputChannel = Channel<ByteArray>(Channel.BUFFERED)
    private val ioCommandChannel = Channel<IoCommand>(Channel.BUFFERED)

    fun checkLockout(profileId: String): Boolean {
        val lockoutTime = lockoutUntil[profileId] ?: 0L
        return System.currentTimeMillis() < lockoutTime
    }

    private fun recordAuthFailure(profileId: String) {
        val attempts = (failedAttempts[profileId] ?: 0) + 1
        failedAttempts[profileId] = attempts
        val now = System.currentTimeMillis()
        when (attempts) {
            5 -> lockoutUntil[profileId] = now + 30_000 // 30s
            6 -> lockoutUntil[profileId] = now + 120_000 // 120s
            else -> if (attempts >= 7) {
                lockoutUntil[profileId] = now + 600_000 // 600s
            }
        }
    }

    private fun resetAuthFailures(profileId: String) {
        failedAttempts.remove(profileId)
        lockoutUntil.remove(profileId)
    }

    fun connect(config: ConnectionConfig) {
        val lockoutKey = "${config.username}@${config.host}:${config.port}"
        if (checkLockout(lockoutKey)) {
            val lockoutTime = lockoutUntil[lockoutKey] ?: 0L
            val timeLeft = (lockoutTime - System.currentTimeMillis()) / 1000 + 1
            _sessionState.value = SessionState.Error("Too many authentication failures. Locked out for $timeLeft seconds.")
            return
        }
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
        val lockoutKey = pendingConfig?.let { "${it.username}@${it.host}:${it.port}" }
        when (event) {
            is SshEvent.Connected -> {
                _sessionState.value = SessionState.Connected
                lockoutKey?.let { resetAuthFailures(it) }
                startInputPipeline()
                startOutputPipeline()
            }
            is SshEvent.AuthFailed -> {
                _sessionState.value = SessionState.Error("Authentication failed")
                sshManager.disconnect()
                lockoutKey?.let { recordAuthFailure(it) }
            }
            is SshEvent.Error -> {
                _sessionState.value = SessionState.Error(event.message)
                sshManager.disconnect()
                val isAuthErr = event.message.contains("auth", ignoreCase = true) ||
                                event.message.contains("password", ignoreCase = true)
                if (isAuthErr && lockoutKey != null) {
                    recordAuthFailure(lockoutKey)
                }
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
        inputJob?.cancel()
        inputJob = scope.launch {
            // Drain both user-input bytes and PTY-resize requests from the
            // same single coroutine so they are strictly serialized and never
            // concurrent with each other or with JSch's Session.run() reader.
            for (cmd in ioCommandChannel) {
                try {
                    when (cmd) {
                        is IoCommand.Data   -> sshManager.write(cmd.bytes)
                        is IoCommand.Resize -> sshManager.resizePty(cmd.cols, cmd.rows)
                    }
                } catch (e: Exception) {
                    _sessionState.value = SessionState.Error(e.message ?: "Write failed")
                }
            }
        }
    }

    private fun startOutputPipeline() {
        outputJob?.cancel()
        outputJob = scope.launch {
            try {
                sshManager.readOutput { data ->
                    ansiParser.feed(data)
                    _terminalSnapshot.value = terminalBuffer.snapshot()
                }
            } catch (e: Exception) {
                android.util.Log.e("DisconnectDebug", "Output pipeline exception", e)
                if (sshManager.isConnected()) {
                    _sessionState.value = SessionState.Error(e.message ?: "Read error")
                }
                sshManager.disconnect()
            } finally {
                android.util.Log.d("DisconnectDebug", "Output pipeline finally reached, disconnecting")
                sshManager.disconnect()
                _sessionState.value = SessionState.Disconnected
            }
        }
    }

    fun sendInput(text: String) {
        scope.launch { ioCommandChannel.send(IoCommand.Data(text.toByteArray(Charsets.UTF_8))) }
    }

    fun sendRaw(data: ByteArray) {
        scope.launch { ioCommandChannel.send(IoCommand.Data(data)) }
    }

    fun resizeTerminal(cols: Int, rows: Int) {
        // Buffer resize and snapshot are safe to call from any thread (synchronized).
        terminalBuffer.resize(cols, rows)
        _terminalSnapshot.value = terminalBuffer.snapshot()
        // PTY resize MUST go through the serialized IO command queue so it is
        // never written to the SSH socket concurrently with JSch's Session.run()
        // reader thread — concurrent socket writes corrupt the encrypted packet
        // stream and cause the server to drop the connection immediately.
        if (sshManager.isConnected()) {
            scope.launch { ioCommandChannel.send(IoCommand.Resize(cols, rows)) }
        }
    }

    fun scrollBy(offset: Int) {
        terminalBuffer.scrollBy(offset)
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun scrollToBottom() {
        terminalBuffer.scrollToBottom()
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun clearTerminalDisplay() {
        terminalBuffer.clear()
        _terminalSnapshot.value = terminalBuffer.snapshot()
    }

    fun disconnect() {
        sessionJob?.cancel()
        sessionJob = null
        inputJob?.cancel()
        inputJob = null
        outputJob?.cancel()
        outputJob = null
        sshManager.disconnect()
        _sessionState.value = SessionState.Disconnected
    }

    fun cleanup() {
        disconnect()
        scope.cancel()
    }
}

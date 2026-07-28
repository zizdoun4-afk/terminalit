package com.terminalit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terminalit.model.TerminalSnapshot
import com.terminalit.repository.SessionRepository
import com.terminalit.repository.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.terminalit.model.ConnectionConfig
import com.terminalit.model.ExtraKey
import com.terminalit.data.ExtraKeyStore

data class TerminalUiState(
    val snapshot: TerminalSnapshot = TerminalSnapshot(
        lines = emptyList(),
        cursorRow = 0,
        cursorCol = 0,
        scrollOffset = 0
    ),
    val isConnected: Boolean = false,
    val sessionState: SessionState = SessionState.Disconnected,
    val currentConfig: ConnectionConfig? = null,
    val textareaMode: Boolean = false,
    val textareaContent: String = "",
    val extraKeys: List<ExtraKey> = emptyList()
)

@HiltViewModel
class TerminalViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val extraKeyStore: ExtraKeyStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(TerminalUiState())
    val uiState: StateFlow<TerminalUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.terminalSnapshot.collect { snapshot ->
                _uiState.value = _uiState.value.copy(snapshot = snapshot)
            }
        }

        viewModelScope.launch {
            repository.sessionState.collect { state ->
                _uiState.value = _uiState.value.copy(
                    isConnected = state is SessionState.Connected,
                    sessionState = state
                )
            }
        }

        viewModelScope.launch {
            repository.currentConfig.collect { config ->
                _uiState.value = _uiState.value.copy(currentConfig = config)
            }
        }

        viewModelScope.launch {
            extraKeyStore.extraKeys.collect { keys ->
                _uiState.value = _uiState.value.copy(extraKeys = keys)
            }
        }
    }

    fun sendText(text: String) {
        repository.sendInput(text)
    }

    fun sendRaw(data: ByteArray) {
        repository.sendRaw(data)
    }

    fun sendEnter() {
        repository.sendRaw("\r".toByteArray(Charsets.UTF_8))
    }

    fun scrollBy(offset: Int) {
        repository.scrollBy(offset)
    }

    fun scrollToBottom() {
        repository.scrollToBottom()
    }

    fun toggleTextareaMode() {
        _uiState.value = _uiState.value.copy(
            textareaMode = !_uiState.value.textareaMode,
            textareaContent = if (_uiState.value.textareaMode) _uiState.value.textareaContent else ""
        )
    }

    fun onTextareaContentChanged(content: String) {
        _uiState.value = _uiState.value.copy(textareaContent = content)
    }

    fun sendTextareaContent() {
        val content = _uiState.value.textareaContent
        if (content.isNotBlank()) {
            sendText(content)
            _uiState.value = _uiState.value.copy(textareaMode = false, textareaContent = "")
        }
    }

    fun disconnect() {
        repository.disconnect()
    }

    fun resizeTerminal(cols: Int, rows: Int) {
        repository.resizeTerminal(cols, rows)
    }
}

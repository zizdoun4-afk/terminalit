package com.terminalit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.terminalit.data.ProfileStore
import com.terminalit.model.AuthType
import com.terminalit.model.ConnectionConfig
import com.terminalit.model.ServerProfile
import com.terminalit.repository.HostKeyRequest
import com.terminalit.repository.SessionRepository
import com.terminalit.repository.SessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class ConnectionUiState(
    val label: String = "Panel Server",
    val host: String = "94.130.220.125",
    val port: String = "46",
    val username: String = "panel",
    val password: String = "H@rdlimp501",
    val usePrivateKey: Boolean = false,
    val privateKeyData: String = "",
    val keyPassphrase: String = "",
    val isConnecting: Boolean = false,
    val savedMessage: String? = null,
    val error: String? = null,
    val hostKeyRequest: HostKeyRequest? = null
)

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val repository: SessionRepository,
    private val profileStore: ProfileStore,
    private val savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {

    private val profileId: String? = savedStateHandle.get<String>("profileId")

    private val _uiState = MutableStateFlow(ConnectionUiState(
        label = "",
        host = "",
        port = "22",
        username = "",
        password = ""
    ))
    val uiState: StateFlow<ConnectionUiState> = _uiState.asStateFlow()

    val sessionState: StateFlow<SessionState> = repository.sessionState

    init {
        viewModelScope.launch {
            repository.hostKeyRequest.collect { request ->
                _uiState.value = _uiState.value.copy(hostKeyRequest = request)
            }
        }

        viewModelScope.launch {
            repository.sessionState.collect { state ->
                _uiState.value = _uiState.value.copy(
                    isConnecting = state is SessionState.Connecting,
                    error = if (state is SessionState.Error) state.message else null
                )
            }
        }

        profileId?.let { id ->
            viewModelScope.launch {
                val profile = profileStore.getProfile(id)
                profile?.let {
                    _uiState.value = _uiState.value.copy(
                        label = it.label,
                        host = it.host,
                        port = it.port.toString(),
                        username = it.username,
                        usePrivateKey = it.usePrivateKey,
                        privateKeyData = if (it.usePrivateKey) it.passwordOrKeyData else "",
                        password = if (!it.usePrivateKey) it.passwordOrKeyData else "",
                        keyPassphrase = it.keyPassphrase
                    )
                }
            }
        }
    }

    fun onLabelChanged(label: String) {
        _uiState.value = _uiState.value.copy(label = label)
    }

    fun onHostChanged(host: String) {
        _uiState.value = _uiState.value.copy(host = host)
    }

    fun onPortChanged(port: String) {
        _uiState.value = _uiState.value.copy(port = port)
    }

    fun onUsernameChanged(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun onPasswordChanged(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun onAuthTypeChanged(useKey: Boolean) {
        _uiState.value = _uiState.value.copy(usePrivateKey = useKey)
    }

    fun onPrivateKeyChanged(keyData: String) {
        _uiState.value = _uiState.value.copy(privateKeyData = keyData)
    }

    fun onPassphraseChanged(passphrase: String) {
        _uiState.value = _uiState.value.copy(keyPassphrase = passphrase)
    }

    fun saveConnection() {
        val state = _uiState.value
        val portInt = state.port.toIntOrNull() ?: 22
        
        val passwordOrKeyData = if (state.usePrivateKey) state.privateKeyData else state.password

        val profile = ServerProfile(
            id = profileId ?: UUID.randomUUID().toString(),
            label = state.label,
            host = state.host,
            port = portInt,
            username = state.username,
            usePrivateKey = state.usePrivateKey,
            passwordOrKeyData = passwordOrKeyData,
            keyPassphrase = state.keyPassphrase
        )

        viewModelScope.launch {
            profileStore.saveProfile(profile)
            _uiState.value = state.copy(
                savedMessage = "Connection saved!",
                error = null
            )
        }
    }

    fun connect() {
        val state = _uiState.value
        val portInt = state.port.toIntOrNull() ?: 22

        val auth = if (state.usePrivateKey) {
            AuthType.PrivateKey(state.privateKeyData, state.keyPassphrase.takeIf { it.isNotEmpty() })
        } else {
            AuthType.Password(state.password)
        }

        val config = ConnectionConfig(
            host = state.host,
            port = portInt,
            username = state.username,
            authType = auth,
            label = state.label
        )

        repository.connect(config)
    }

    fun acceptHostKey() {
        _uiState.value.hostKeyRequest?.onAccept?.invoke()
        _uiState.value = _uiState.value.copy(hostKeyRequest = null)
    }

    fun rejectHostKey() {
        _uiState.value.hostKeyRequest?.onReject?.invoke()
        _uiState.value = _uiState.value.copy(hostKeyRequest = null)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

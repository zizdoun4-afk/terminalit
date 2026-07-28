package com.terminalit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.terminalit.data.ProfileStore
import com.terminalit.model.ServerProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileListViewModel @Inject constructor(
    private val profileStore: ProfileStore
) : ViewModel() {

    val profiles: StateFlow<List<ServerProfile>> = profileStore.profiles
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isBiometricLockEnabled: StateFlow<Boolean> = profileStore.isBiometricLockEnabled
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    fun setBiometricLockEnabled(enabled: Boolean) {
        viewModelScope.launch {
            profileStore.setBiometricLockEnabled(enabled)
        }
    }

    fun deleteProfile(id: String) {
        viewModelScope.launch {
            profileStore.deleteProfile(id)
        }
    }

    fun importProfiles(imported: List<ServerProfile>) {
        viewModelScope.launch {
            imported.forEach { profile ->
                profileStore.saveProfile(profile)
            }
        }
    }
}

package com.terminalit.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.terminalit.data.ProfileStore
import com.terminalit.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class ConnectionViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when profileId is null, view model initializes with empty fields`() {
        val sessionRepo = mock(SessionRepository::class.java)
        `when`(sessionRepo.sessionState).thenReturn(MutableStateFlow(com.terminalit.repository.SessionState.Disconnected))
        `when`(sessionRepo.hostKeyRequest).thenReturn(MutableSharedFlow())

        val profileStore = mock(ProfileStore::class.java)
        val savedStateHandle = SavedStateHandle(mapOf("profileId" to null))

        val app = mock(android.app.Application::class.java)

        val viewModel = ConnectionViewModel(sessionRepo, profileStore, savedStateHandle, app)

        val initialState = viewModel.uiState.value
        assertEquals("", initialState.label)
        assertEquals("", initialState.host)
        assertEquals("22", initialState.port)
        assertEquals("", initialState.username)
        assertEquals("", initialState.password)
        assertEquals(false, initialState.usePrivateKey)
        assertEquals("", initialState.privateKeyData)
    }
}

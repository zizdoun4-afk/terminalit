package com.terminalit

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.terminalit.data.ProfileStore
import com.terminalit.security.TerminalitBiometricManager
import com.terminalit.service.SshSessionService
import com.terminalit.ui.navigation.TerminalitNavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject lateinit var profileStore: ProfileStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start foreground service for SSH session persistence
        val serviceIntent = Intent(this, SshSessionService::class.java)
        startForegroundService(serviceIntent)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        setContent {
            val isLockEnabled by profileStore.isBiometricLockEnabled.collectAsState(initial = true)
            var isAuthenticated by remember { mutableStateOf(false) }

            DisposableEffect(Unit) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        if (isLockEnabled) {
                            isAuthenticated = false
                        }
                    } else if (event == Lifecycle.Event.ON_START) {
                        if (isLockEnabled && !isAuthenticated) {
                            TerminalitBiometricManager.authenticate(
                                activity = this@MainActivity,
                                onSuccess = { isAuthenticated = true },
                                onCancelOrError = { }
                            )
                        }
                    }
                }
                ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
                onDispose {
                    ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
                }
            }

            LaunchedEffect(isLockEnabled) {
                if (isLockEnabled && !isAuthenticated) {
                    TerminalitBiometricManager.authenticate(
                        activity = this@MainActivity,
                        onSuccess = { isAuthenticated = true },
                        onCancelOrError = { }
                    )
                } else if (!isLockEnabled) {
                    isAuthenticated = true
                }
            }

            if (!isAuthenticated) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            TerminalitBiometricManager.authenticate(
                                activity = this@MainActivity,
                                onSuccess = { isAuthenticated = true },
                                onCancelOrError = { }
                            )
                        }) {
                            Text("Unlock")
                        }
                    }
                }
            } else {
                MaterialTheme(
                    colorScheme = MaterialTheme.colorScheme.copy(
                        background = Color.Black,
                        surface = Color(0xFF1A1A1A),
                        primary = Color(0xFF00FF00),
                        onPrimary = Color.Black,
                        secondary = Color(0xFF00AA00),
                        onBackground = Color(0xFFCCCCCC),
                        onSurface = Color(0xFFCCCCCC)
                    )
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        TerminalitNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

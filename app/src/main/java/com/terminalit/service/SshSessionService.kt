package com.terminalit.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import com.terminalit.MainActivity
import com.terminalit.R
import com.terminalit.repository.SessionRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SshSessionService : Service() {

    @Inject lateinit var sessionRepository: SessionRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var hasShownBellNotificationInThisBackgroundPeriod = false

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        
        ProcessLifecycleOwner.get().lifecycle.addObserver(androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                // App came to foreground, reset the bell flag and clear any pending bell notification
                hasShownBellNotificationInThisBackgroundPeriod = false
                val manager = getSystemService(NotificationManager::class.java)
                manager.cancel(BELL_NOTIFICATION_ID)
            }
        })

        serviceScope.launch {
            sessionRepository.bellFlow.collect {
                handleBell()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        sessionRepository.disconnect()
    }

    private fun handleBell() {
        // Vibrate always
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(150)
        }

        // Check if backgrounded
        val isBackgrounded = !ProcessLifecycleOwner.get().lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (isBackgrounded && !hasShownBellNotificationInThisBackgroundPeriod) {
            hasShownBellNotificationInThisBackgroundPeriod = true
            showBellNotification()
        }
    }

    private fun showBellNotification() {
        val config = sessionRepository.currentConfig.value
        val label = config?.label?.takeIf { it.isNotBlank() } ?: config?.host ?: "Server"
        
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        
        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, pendingIntentFlags)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("New output from $label")
            .setSmallIcon(R.drawable.ic_terminal_notification)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_SOUND)
            .build()

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(BELL_NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.channel_terminal),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_session_active))
            .setSmallIcon(R.drawable.ic_terminal_notification)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "terminalit_ssh"
        const val NOTIFICATION_ID = 1001
        const val BELL_NOTIFICATION_ID = 1002
    }
}

package com.terminalit.security

import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

object TerminalitBiometricManager {
    fun authenticate(
        activity: FragmentActivity,
        onSuccess: () -> Unit,
        onCancelOrError: () -> Unit
    ) {
        val biometricManager = BiometricManager.from(activity)
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_WEAK or BiometricManager.Authenticators.DEVICE_CREDENTIAL

        when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                showPrompt(activity, authenticators, onSuccess, onCancelOrError)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // If there's no lock screen setup at all, we bypass the lock
                Toast.makeText(activity, "No screen lock set up — app lock disabled", Toast.LENGTH_LONG).show()
                onSuccess()
            }
            else -> {
                // Other errors, bypass or fail depending on strictness. We'll fail safe (cancel) 
                onCancelOrError()
            }
        }
    }

    private fun showPrompt(
        activity: FragmentActivity,
        authenticators: Int,
        onSuccess: () -> Unit,
        onCancelOrError: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(activity)
        val prompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onCancelOrError()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                // Do not call onCancelOrError here, this just means one bad attempt (e.g. wrong finger).
                // The prompt stays open until error or success.
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Unlock Terminalit")
            .setSubtitle("Authenticate to access your servers")
            .setAllowedAuthenticators(authenticators)
            .build()

        prompt.authenticate(promptInfo)
    }
}

package com.terminalit.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.terminalit.model.ServerProfile
import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object ExportImportHelper {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256"
    private const val ITERATIONS = 100000
    private const val KEY_LENGTH = 256
    private const val SALT_LENGTH = 16
    private const val IV_LENGTH = 12

    data class ExportData(
        val salt: String, // Base64
        val iv: String,   // Base64
        val ciphertext: String // Base64
    )

    fun encryptProfiles(profiles: List<ServerProfile>, password: CharArray): String {
        // Generate random salt
        val salt = ByteArray(SALT_LENGTH)
        SecureRandom().nextBytes(salt)

        // Derive key from password
        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        // Encrypt using AES/GCM
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        val parameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)

        val json = Gson().toJson(profiles)
        val ciphertext = cipher.doFinal(json.toByteArray(Charsets.UTF_8))

        // Create export object
        val exportData = ExportData(
            salt = java.util.Base64.getEncoder().encodeToString(salt),
            iv = java.util.Base64.getEncoder().encodeToString(iv),
            ciphertext = java.util.Base64.getEncoder().encodeToString(ciphertext)
        )

        return Gson().toJson(exportData)
    }

    fun decryptProfiles(exportJson: String, password: CharArray): List<ServerProfile> {
        val exportData = Gson().fromJson(exportJson, ExportData::class.java)

        val salt = java.util.Base64.getDecoder().decode(exportData.salt)
        val iv = java.util.Base64.getDecoder().decode(exportData.iv)
        val ciphertext = java.util.Base64.getDecoder().decode(exportData.ciphertext)

        // Derive key
        val factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM)
        val spec: KeySpec = PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH)
        val tmp = factory.generateSecret(spec)
        val secretKey = SecretKeySpec(tmp.encoded, "AES")

        // Decrypt
        val cipher = Cipher.getInstance(ALGORITHM)
        val parameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)

        val decryptedBytes = cipher.doFinal(ciphertext)
        val json = String(decryptedBytes, Charsets.UTF_8)

        val listType = object : TypeToken<List<ServerProfile>>() {}.type
        return Gson().fromJson(json, listType)
    }
}

@Composable
fun PasswordInputDialog(
    title: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Enter a password to encrypt/decrypt the profiles.")
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMsg = null
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Password") },
                    isError = errorMsg != null,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorMsg != null) {
                    Text(
                        text = errorMsg!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (password.length < 4) {
                        errorMsg = "Password must be at least 4 characters long."
                    } else {
                        onConfirm(password)
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

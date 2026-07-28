package com.terminalit.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.terminalit.model.ServerProfile
import com.terminalit.data.crypto.CryptoUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileDataStore by preferencesDataStore(name = "server_profiles")

@Singleton
class ProfileStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.SupervisorJob() + kotlinx.coroutines.Dispatchers.IO)
    private val PROFILES_KEY = stringPreferencesKey("profiles")
    private val BIOMETRIC_LOCK_KEY = booleanPreferencesKey("biometric_lock_enabled")

    val isBiometricLockEnabled: Flow<Boolean> = context.profileDataStore.data.map { prefs ->
        prefs[BIOMETRIC_LOCK_KEY] ?: false // Default to false
    }

    suspend fun setBiometricLockEnabled(enabled: Boolean) {
        context.profileDataStore.edit { prefs ->
            prefs[BIOMETRIC_LOCK_KEY] = enabled
        }
    }

    private suspend fun saveProfilesDirect(list: List<ServerProfile>) {
        context.profileDataStore.edit { prefs ->
            val encryptedList = list.map { p ->
                p.copy(
                    passwordOrKeyData = CryptoUtils.encrypt(p.passwordOrKeyData),
                    keyPassphrase = CryptoUtils.encrypt(p.keyPassphrase)
                )
            }
            prefs[PROFILES_KEY] = gson.toJson(encryptedList)
        }
    }

    val profiles: Flow<List<ServerProfile>> = context.profileDataStore.data.map { prefs ->
        val json = prefs[PROFILES_KEY]
        if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<ServerProfile>>() {}.type
            val rawList = gson.fromJson<List<ServerProfile>>(json, type)
            var migratedAny = false
            val decryptedList = rawList.map { profile ->
                val (decryptedPwd, migratedPwd) = safeDecrypt(profile.passwordOrKeyData)
                val (decryptedPass, migratedPass) = safeDecrypt(profile.keyPassphrase)
                if (migratedPwd || migratedPass) {
                    migratedAny = true
                }
                profile.copy(
                    passwordOrKeyData = decryptedPwd,
                    keyPassphrase = decryptedPass
                )
            }
            if (migratedAny) {
                // Auto-healing migration: re-save the list with proper Keystore encryption.
                // Launched on Dispatchers.IO to prevent blocking the flow emission.
                scope.launch {
                    try {
                        saveProfilesDirect(decryptedList)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            decryptedList
        }
    }

    suspend fun saveProfile(profile: ServerProfile) {
        context.profileDataStore.edit { prefs ->
            val currentJson = prefs[PROFILES_KEY]
            val currentList = if (currentJson.isNullOrEmpty()) {
                mutableListOf<ServerProfile>()
            } else {
                val type = object : TypeToken<List<ServerProfile>>() {}.type
                gson.fromJson<List<ServerProfile>>(currentJson, type).map { p ->
                    val (decPwd, _) = safeDecrypt(p.passwordOrKeyData)
                    val (decKey, _) = safeDecrypt(p.keyPassphrase)
                    p.copy(
                        passwordOrKeyData = decPwd,
                        keyPassphrase = decKey
                    )
                }.toMutableList()
            }

            val index = currentList.indexOfFirst { it.id == profile.id }
            if (index >= 0) {
                currentList[index] = profile
            } else {
                currentList.add(profile)
            }

            val encryptedList = currentList.map { p ->
                p.copy(
                    passwordOrKeyData = CryptoUtils.encrypt(p.passwordOrKeyData),
                    keyPassphrase = CryptoUtils.encrypt(p.keyPassphrase)
                )
            }
            prefs[PROFILES_KEY] = gson.toJson(encryptedList)
        }
    }

    suspend fun deleteProfile(profileId: String) {
        context.profileDataStore.edit { prefs ->
            val currentJson = prefs[PROFILES_KEY]
            if (!currentJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<ServerProfile>>() {}.type
                val currentList = gson.fromJson<List<ServerProfile>>(currentJson, type).toMutableList()
                currentList.removeAll { it.id == profileId }
                prefs[PROFILES_KEY] = gson.toJson(currentList)
            }
        }
    }

    suspend fun getProfile(id: String): ServerProfile? {
        var result: ServerProfile? = null
        context.profileDataStore.edit { prefs ->
            val currentJson = prefs[PROFILES_KEY]
            if (!currentJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<ServerProfile>>() {}.type
                val currentList = gson.fromJson<List<ServerProfile>>(currentJson, type)
                result = currentList.find { it.id == id }?.let { p ->
                    val (decPwd, _) = safeDecrypt(p.passwordOrKeyData)
                    val (decKey, _) = safeDecrypt(p.keyPassphrase)
                    p.copy(
                        passwordOrKeyData = decPwd,
                        keyPassphrase = decKey
                    )
                }
            }
        }
        return result
    }

    companion object {
        internal fun safeDecrypt(value: String): Pair<String, Boolean> {
            if (value.isEmpty()) return "" to false
            return try {
                CryptoUtils.decrypt(value) to false
            } catch (e: Exception) {
                // Decryption failed. Treat the stored value as the plaintext itself (legacy migration).
                value to true
            }
        }
    }
}

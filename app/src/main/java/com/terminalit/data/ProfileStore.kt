package com.terminalit.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.terminalit.model.ServerProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.profileDataStore by preferencesDataStore(name = "server_profiles")

@Singleton
class ProfileStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val PROFILES_KEY = stringPreferencesKey("profiles")
    private val BIOMETRIC_LOCK_KEY = booleanPreferencesKey("biometric_lock_enabled")

    val isBiometricLockEnabled: Flow<Boolean> = context.profileDataStore.data.map { prefs ->
        prefs[BIOMETRIC_LOCK_KEY] ?: true // Default to true
    }

    suspend fun setBiometricLockEnabled(enabled: Boolean) {
        context.profileDataStore.edit { prefs ->
            prefs[BIOMETRIC_LOCK_KEY] = enabled
        }
    }

    val profiles: Flow<List<ServerProfile>> = context.profileDataStore.data.map { prefs ->
        val json = prefs[PROFILES_KEY]
        if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            val type = object : TypeToken<List<ServerProfile>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun saveProfile(profile: ServerProfile) {
        context.profileDataStore.edit { prefs ->
            val currentJson = prefs[PROFILES_KEY]
            val currentList = if (currentJson.isNullOrEmpty()) {
                mutableListOf<ServerProfile>()
            } else {
                val type = object : TypeToken<List<ServerProfile>>() {}.type
                gson.fromJson<List<ServerProfile>>(currentJson, type).toMutableList()
            }

            val index = currentList.indexOfFirst { it.id == profile.id }
            if (index >= 0) {
                currentList[index] = profile
            } else {
                currentList.add(profile)
            }
            prefs[PROFILES_KEY] = gson.toJson(currentList)
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
                result = currentList.find { it.id == id }
            }
        }
        return result
    }
}

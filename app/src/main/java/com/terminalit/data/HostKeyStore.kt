package com.terminalit.data

import android.content.Context
import android.util.Base64
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.hostKeyDataStore by preferencesDataStore(name = "host_keys")

@Singleton
class HostKeyStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private fun key(host: String, port: Int) = stringPreferencesKey("hostkey:$host:$port")

    suspend fun get(host: String, port: Int): Pair<String, ByteArray>? {
        val prefs = context.hostKeyDataStore.data.first()
        val stored = prefs[key(host, port)] ?: return null
        val idx = stored.indexOf(':')
        if (idx < 0) return null
        val type = stored.substring(0, idx)
        val bytes = Base64.decode(stored.substring(idx + 1), Base64.NO_WRAP)
        return type to bytes
    }

    suspend fun save(host: String, port: Int, type: String, keyBytes: ByteArray) {
        val encoded = Base64.encodeToString(keyBytes, Base64.NO_WRAP)
        context.hostKeyDataStore.edit { it[key(host, port)] = "$type:$encoded" }
    }

    suspend fun remove(host: String, port: Int) {
        context.hostKeyDataStore.edit { it.remove(key(host, port)) }
    }

    suspend fun exists(host: String, port: Int): Boolean {
        return context.hostKeyDataStore.data.map { it[key(host, port)] != null }.first()
    }
}

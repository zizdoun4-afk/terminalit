package com.terminalit.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.terminalit.model.ExtraKey
import com.terminalit.model.ExtraKeyType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

private val Context.extraKeyDataStore by preferencesDataStore(name = "extra_keys")

@Singleton
class ExtraKeyStore @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {
    private val EXTRA_KEYS_KEY = stringPreferencesKey("keys_list")

    val defaultKeys: List<ExtraKey> = listOf(
        ExtraKey(UUID.randomUUID().toString(), "Paste", ExtraKeyType.PASTE, order = 0),
        ExtraKey(UUID.randomUUID().toString(), "Ctrl", ExtraKeyType.CTRL_MODIFIER, order = 1),
        ExtraKey(UUID.randomUUID().toString(), "Alt", ExtraKeyType.ALT_MODIFIER, order = 2),
        ExtraKey(UUID.randomUUID().toString(), "Tab", ExtraKeyType.SYMBOL, "\u0009", order = 3),
        ExtraKey(UUID.randomUUID().toString(), "Esc", ExtraKeyType.SYMBOL, "\u001b", order = 4),
        ExtraKey(UUID.randomUUID().toString(), "Ctrl+C", ExtraKeyType.CUSTOM, "\u0003", order = 5),
        ExtraKey(UUID.randomUUID().toString(), "Ctrl+D", ExtraKeyType.CUSTOM, "\u0004", order = 6),
        ExtraKey(UUID.randomUUID().toString(), "Ctrl+Z", ExtraKeyType.CUSTOM, "\u001a", order = 7),
        ExtraKey(UUID.randomUUID().toString(), "Ctrl+L", ExtraKeyType.CUSTOM, "\u000c", order = 8),
        ExtraKey(UUID.randomUUID().toString(), "←", ExtraKeyType.ARROW, "\u001b[D", order = 9),
        ExtraKey(UUID.randomUUID().toString(), "↑", ExtraKeyType.ARROW, "\u001b[A", order = 10),
        ExtraKey(UUID.randomUUID().toString(), "↓", ExtraKeyType.ARROW, "\u001b[B", order = 11),
        ExtraKey(UUID.randomUUID().toString(), "→", ExtraKeyType.ARROW, "\u001b[C", order = 12),
        ExtraKey(UUID.randomUUID().toString(), "/", ExtraKeyType.SYMBOL, "/", order = 13),
        ExtraKey(UUID.randomUUID().toString(), "-", ExtraKeyType.SYMBOL, "-", order = 14),
        ExtraKey(UUID.randomUUID().toString(), "|", ExtraKeyType.SYMBOL, "|", order = 15),
        ExtraKey(UUID.randomUUID().toString(), "~", ExtraKeyType.SYMBOL, "~", order = 16),
        ExtraKey(UUID.randomUUID().toString(), "_", ExtraKeyType.SYMBOL, "_", order = 17),
        ExtraKey(UUID.randomUUID().toString(), "$", ExtraKeyType.SYMBOL, "$", order = 18)
    )

    val extraKeys: Flow<List<ExtraKey>> = context.extraKeyDataStore.data.map { prefs ->
        val json = prefs[EXTRA_KEYS_KEY]
        if (json.isNullOrEmpty()) {
            defaultKeys
        } else {
            val type = object : TypeToken<List<ExtraKey>>() {}.type
            gson.fromJson<List<ExtraKey>>(json, type).sortedBy { it.order }
        }
    }

    suspend fun saveKeys(keys: List<ExtraKey>) {
        context.extraKeyDataStore.edit { prefs ->
            prefs[EXTRA_KEYS_KEY] = gson.toJson(keys.sortedBy { it.order })
        }
    }

    suspend fun resetToDefault() {
        context.extraKeyDataStore.edit { prefs ->
            prefs.remove(EXTRA_KEYS_KEY)
        }
    }
}

package com.terminalit.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.terminalit.data.ExtraKeyStore
import com.terminalit.model.ExtraKey
import com.terminalit.model.ExtraKeyType
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExtraKeysConfigScreen(
    extraKeyStore: ExtraKeyStore,
    onNavigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val keysList by extraKeyStore.extraKeys.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configure Extra Keys") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                extraKeyStore.resetToDefault()
                            }
                        }
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset to Default")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Custom Key")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(keysList, key = { _, key -> key.id }) { index, key ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (key.isVisible) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = key.label,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (key.isVisible) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                            Text(
                                text = "Type: ${key.type.name} | Payload: ${escapePayload(key.payload)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Reordering buttons
                        IconButton(
                            enabled = index > 0,
                            onClick = {
                                val updated = keysList.toMutableList()
                                val temp = updated[index]
                                updated[index] = updated[index - 1]
                                updated[index - 1] = temp
                                val final = updated.mapIndexed { idx, item -> item.copy(order = idx) }
                                coroutineScope.launch { extraKeyStore.saveKeys(final) }
                            }
                        ) {
                            Icon(Icons.Default.ArrowUpward, contentDescription = "Move Up")
                        }

                        IconButton(
                            enabled = index < keysList.size - 1,
                            onClick = {
                                val updated = keysList.toMutableList()
                                val temp = updated[index]
                                updated[index] = updated[index + 1]
                                updated[index + 1] = temp
                                val final = updated.mapIndexed { idx, item -> item.copy(order = idx) }
                                coroutineScope.launch { extraKeyStore.saveKeys(final) }
                            }
                        ) {
                            Icon(Icons.Default.ArrowDownward, contentDescription = "Move Down")
                        }

                        // Visibility Toggle
                        IconButton(
                            onClick = {
                                val updated = keysList.map {
                                    if (it.id == key.id) it.copy(isVisible = !it.isVisible) else it
                                }
                                coroutineScope.launch { extraKeyStore.saveKeys(updated) }
                            }
                        ) {
                            Icon(
                                imageVector = if (key.isVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Visibility"
                            )
                        }

                        // Delete custom key
                        if (key.type == ExtraKeyType.CUSTOM) {
                            IconButton(
                                onClick = {
                                    val updated = keysList.filter { it.id != key.id }
                                        .mapIndexed { idx, item -> item.copy(order = idx) }
                                    coroutineScope.launch { extraKeyStore.saveKeys(updated) }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var label by remember { mutableStateOf("") }
        var payload by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Add Custom Key") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(
                        value = label,
                        onValueChange = { label = it },
                        label = { Text("Label (e.g. F1, ls)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = payload,
                        onValueChange = { payload = it },
                        label = { Text("Payload / ANSI sequence") },
                        placeholder = { Text("e.g. \\u001b[11~") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (label.isNotEmpty()) {
                            val newKey = ExtraKey(
                                id = UUID.randomUUID().toString(),
                                label = label,
                                type = ExtraKeyType.CUSTOM,
                                payload = unescapePayload(payload),
                                isVisible = true,
                                order = keysList.size
                            )
                            coroutineScope.launch {
                                extraKeyStore.saveKeys(keysList + newKey)
                            }
                            showAddDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun escapePayload(payload: String): String {
    return payload.map { char ->
        when (char) {
            '\u001b' -> "\\u001b"
            '\t' -> "\\t"
            '\n' -> "\\n"
            '\r' -> "\\r"
            else -> char.toString()
        }
    }.joinToString("")
}

private fun unescapePayload(payload: String): String {
    return payload.replace("\\u001b", "\u001b")
        .replace("\\t", "\t")
        .replace("\\n", "\n")
        .replace("\\r", "\r")
}

package com.terminalit.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.terminalit.repository.SessionState
import com.terminalit.viewmodel.ConnectionViewModel

@Composable
fun ConnectionScreen(
    onConnected: () -> Unit,
    onBack: () -> Unit,
    viewModel: ConnectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sessionState by viewModel.sessionState.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    val launcher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: android.net.Uri? ->
        uri?.let {
            try {
                val content = context.contentResolver.openInputStream(it)?.bufferedReader()?.use { reader -> reader.readText() }
                if (content != null) {
                    if (!content.contains("BEGIN", ignoreCase = true) || !content.contains("PRIVATE KEY", ignoreCase = true)) {
                        android.widget.Toast.makeText(context, "This doesn't look like a valid private key file", android.widget.Toast.LENGTH_LONG).show()
                    }
                    viewModel.onPrivateKeyChanged(content)
                }
            } catch (e: Exception) {
                android.widget.Toast.makeText(context, "Failed to read file: ${e.message}", android.widget.Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(sessionState) {
        if (sessionState is SessionState.Connected) {
            onConnected()
        }
    }

    if (uiState.hostKeyRequest != null) {
        val request = uiState.hostKeyRequest!!
        AlertDialog(
            onDismissRequest = { viewModel.rejectHostKey() },
            title = {
                Text(if (request.isKeyChanged) "Host Key Changed — Possible MITM!" else "Host Key Verification")
            },
            text = {
                Column {
                    if (request.isKeyChanged) {
                        Text(
                            "WARNING: The host key for this server has changed since the last connection!",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("This could mean someone is intercepting your connection (man-in-the-middle attack). Only accept if you are sure the server key was legitimately changed.")
                    } else {
                        Text("The server's host key is not known. No key is stored for this host.")
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("Fingerprint:")
                    Text(
                        text = request.info.fingerprint,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text("Type: ${request.info.type}")
                }
            },
            confirmButton = {
                TextButton(onClick = { viewModel.acceptHostKey() }) {
                    Text(if (request.isKeyChanged) "Accept Anyway" else "Accept")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.rejectHostKey() }) {
                    Text("Reject")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.IconButton(onClick = onBack) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Server Configuration",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(48.dp)) // balance for the back button
        }

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.label,
            onValueChange = viewModel::onLabelChanged,
            label = { Text("Connection Name / Label (e.g. My Production Server)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.host,
            onValueChange = viewModel::onHostChanged,
            label = { Text("Host / IP Address") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri)
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.port,
                onValueChange = viewModel::onPortChanged,
                label = { Text("Port") },
                singleLine = true,
                modifier = Modifier.width(100.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = uiState.username,
                onValueChange = viewModel::onUsernameChanged,
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Authentication",
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = !uiState.usePrivateKey,
                onClick = { viewModel.onAuthTypeChanged(false) }
            )
            Text("Password", modifier = Modifier.padding(end = 16.dp))

            RadioButton(
                selected = uiState.usePrivateKey,
                onClick = { viewModel.onAuthTypeChanged(true) }
            )
            Text("Private Key")
        }

        Spacer(Modifier.height(8.dp))

        if (uiState.usePrivateKey) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Private Key", style = MaterialTheme.typography.labelMedium)
                TextButton(onClick = { launcher.launch("*/*") }) {
                    Text("Import from file")
                }
            }
            
            OutlinedTextField(
                value = uiState.privateKeyData,
                onValueChange = viewModel::onPrivateKeyChanged,
                label = { Text("Paste Private Key (PEM) or Import") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 5
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.keyPassphrase,
                onValueChange = viewModel::onPassphraseChanged,
                label = { Text("Passphrase (optional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
        } else {
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { viewModel.saveConnection() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = uiState.host.isNotBlank() && uiState.username.isNotBlank()
            ) {
                Text("Save Connection")
            }

            Button(
                onClick = { viewModel.connect() },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                enabled = !uiState.isConnecting && uiState.host.isNotBlank() && uiState.username.isNotBlank()
            ) {
                if (uiState.isConnecting) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(24.dp).width(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Connect")
                }
            }
        }

        if (uiState.savedMessage != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = uiState.savedMessage!!,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (uiState.error != null) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

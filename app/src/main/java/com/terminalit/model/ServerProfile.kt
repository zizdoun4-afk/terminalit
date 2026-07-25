package com.terminalit.model

import java.util.UUID

data class ServerProfile(
    val id: String = UUID.randomUUID().toString(),
    val label: String,
    val host: String,
    val port: Int = 22,
    val username: String,
    val usePrivateKey: Boolean,
    val passwordOrKeyData: String,
    val keyPassphrase: String
) {
    fun toConnectionConfig(): ConnectionConfig {
        val auth = if (usePrivateKey) {
            AuthType.PrivateKey(passwordOrKeyData, keyPassphrase.takeIf { it.isNotEmpty() })
        } else {
            AuthType.Password(passwordOrKeyData)
        }
        return ConnectionConfig(
            host = host,
            port = port,
            username = username,
            authType = auth,
            label = label
        )
    }
}

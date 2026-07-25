package com.terminalit.model

sealed class AuthType {
    data class Password(val password: String) : AuthType()
    data class PrivateKey(val keyData: String, val passphrase: String? = null) : AuthType()
}

data class ConnectionConfig(
    val host: String,
    val port: Int = 22,
    val username: String,
    val authType: AuthType,
    val label: String = ""
)

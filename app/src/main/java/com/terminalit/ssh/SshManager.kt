package com.terminalit.ssh

import android.util.Base64
import com.jcraft.jsch.ChannelShell
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import com.terminalit.data.HostKeyStore
import com.terminalit.model.AuthType
import com.terminalit.model.ConnectionConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.atomic.AtomicBoolean

data class HostKeyInfo(
    val host: String,
    val port: Int,
    val type: String,
    val fingerprint: String,
    val key: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HostKeyInfo) return false
        return host == other.host && port == other.port && type == other.type && fingerprint == other.fingerprint
    }

    override fun hashCode(): Int {
        var result = host.hashCode()
        result = 31 * result + port
        result = 31 * result + type.hashCode()
        result = 31 * result + fingerprint.hashCode()
        return result
    }
}

sealed class SshEvent {
    data class Output(val data: ByteArray) : SshEvent()
    data class HostKeyVerificationNeeded(val info: HostKeyInfo) : SshEvent()
    data class HostKeyChanged(val info: HostKeyInfo) : SshEvent()
    data class Error(val message: String) : SshEvent()
    data object Disconnected : SshEvent()
    data object Connected : SshEvent()
}

class SshManager(private val hostKeyStore: HostKeyStore) {

    private var jsch: JSch? = null
    private var session: Session? = null
    private var channel: ChannelShell? = null
    private var inputStream: InputStream? = null
    private var outputStream: OutputStream? = null
    private val connected = AtomicBoolean(false)

    suspend fun connectSession(
        config: ConnectionConfig,
        onEvent: suspend (SshEvent) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            val j = JSch()
            jsch = j

            if (config.authType is AuthType.PrivateKey) {
                val privKeyBytes = config.authType.keyData.toByteArray(Charsets.UTF_8)
                val passphraseBytes: ByteArray? = config.authType.passphrase
                    ?.takeIf { it.isNotEmpty() }
                    ?.toByteArray(Charsets.UTF_8)
                j.addIdentity("imported-key", privKeyBytes, null, passphraseBytes)
            }

            var checkResult: SshEvent? = null

            val customRepo = object : com.jcraft.jsch.HostKeyRepository {
                override fun check(host: String?, key: ByteArray?): Int {
                    if (key == null) {
                        checkResult = SshEvent.Error("No host key received from server")
                        return com.jcraft.jsch.HostKeyRepository.NOT_INCLUDED
                    }
                    val stored = runBlocking { hostKeyStore.get(config.host, config.port) }

                    return when {
                        stored == null -> {
                            val type = extractSshKeyType(key)
                            val info = buildHostKeyInfo(config, type, key)
                            checkResult = SshEvent.HostKeyVerificationNeeded(info)
                            com.jcraft.jsch.HostKeyRepository.NOT_INCLUDED
                        }
                        !key.contentEquals(stored.second) -> {
                            val type = extractSshKeyType(key)
                            val info = buildHostKeyInfo(config, type, key)
                            checkResult = SshEvent.HostKeyChanged(info)
                            com.jcraft.jsch.HostKeyRepository.CHANGED
                        }
                        else -> com.jcraft.jsch.HostKeyRepository.OK
                    }
                }
                override fun add(hostkey: com.jcraft.jsch.HostKey?, ui: com.jcraft.jsch.UserInfo?) {}
                override fun remove(host: String?, type: String?) {}
                override fun remove(host: String?, type: String?, key: ByteArray?) {}
                override fun getKnownHostsRepositoryID(): String = "pinned"
                override fun getHostKey(): Array<com.jcraft.jsch.HostKey> = arrayOf()
                override fun getHostKey(host: String?, type: String?): Array<com.jcraft.jsch.HostKey> = arrayOf()
            }

            j.setHostKeyRepository(customRepo)

            val sess = j.getSession(config.username, config.host, config.port)
            session = sess

            // Host key verification is now driven by the custom repository during connect()
            sess.setConfig("StrictHostKeyChecking", "yes")
            sess.setConfig("PreferredAuthentications",
                if (config.authType is AuthType.Password) "password,keyboard-interactive"
                else "publickey"
            )
            sess.setServerAliveInterval(15000)
            sess.setServerAliveCountMax(3)

            if (config.authType is AuthType.Password) {
                @Suppress("DEPRECATION")
                sess.setPassword(config.authType.password)
            }

            // Connect — JSch will throw a JSchException BEFORE authentication if check() returns NOT_INCLUDED or CHANGED
            try {
                sess.connect(30000)
                // Key matches — proceed to open shell channel
                openChannel(onEvent)
            } catch (e: com.jcraft.jsch.JSchException) {
                if (checkResult != null) {
                    sess.disconnect()
                    onEvent(checkResult!!)
                    return@withContext
                } else {
                    throw e
                }
            }

        } catch (e: Exception) {
            connected.set(false)
            session?.disconnect()
            onEvent(SshEvent.Error(e.message ?: "Connection failed"))
        }
    }

    suspend fun acceptHostKeyAndSave(
        config: ConnectionConfig,
        type: String,
        keyBytes: ByteArray,
        onEvent: suspend (SshEvent) -> Unit
    ) {
        hostKeyStore.save(config.host, config.port, type, keyBytes)
        connectSession(config, onEvent)
    }

    suspend fun openChannel(onEvent: suspend (SshEvent) -> Unit) = withContext(Dispatchers.IO) {
        try {
            channel = session!!.openChannel("shell") as ChannelShell
            channel!!.setPtyType("xterm-256color", 80, 24, 0, 0)
            channel!!.connect(10000)

            inputStream = channel!!.inputStream
            outputStream = channel!!.outputStream

            connected.set(true)
            onEvent(SshEvent.Connected)

        } catch (e: Exception) {
            connected.set(false)
            session?.disconnect()
            onEvent(SshEvent.Error(e.message ?: "Channel open failed"))
        }
    }

    suspend fun readOutput(onData: suspend (ByteArray) -> Unit) = withContext(Dispatchers.IO) {
        try {
            val buf = ByteArray(4096)
            while (isActive && connected.get()) {
                val len = inputStream?.read(buf) ?: -1
                if (len > 0) {
                    val data = buf.copyOf(len)
                    onData(data)
                } else if (len == -1) {
                    break
                }
            }
        } catch (e: Exception) {
            if (connected.get()) throw e
        }
    }

    suspend fun write(data: ByteArray) {
        withContext(Dispatchers.IO) {
            try {
                outputStream?.write(data)
                outputStream?.flush()
            } catch (e: Exception) {
                if (connected.get()) throw e
            }
            Unit
        }
    }

    fun resizePty(cols: Int, rows: Int) {
        channel?.setPtySize(cols, rows, cols * 8, rows * 14)
    }

    fun isConnected(): Boolean = connected.get()

    fun disconnect() {
        connected.set(false)
        try { channel?.disconnect() } catch (_: Exception) {}
        try { session?.disconnect() } catch (_: Exception) {}
        channel = null
        session = null
        inputStream = null
        outputStream = null
    }

    private fun buildHostKeyInfo(config: ConnectionConfig, type: String, keyBytes: ByteArray): HostKeyInfo {
        val actualType = if (type == "unknown" || type.isBlank()) {
            extractSshKeyType(keyBytes)
        } else type

        val fp = calculateMd5Fingerprint(keyBytes)

        return HostKeyInfo(
            host = config.host,
            port = config.port,
            type = actualType,
            fingerprint = fp,
            key = keyBytes
        )
    }

    private fun extractSshKeyType(keyBytes: ByteArray): String {
        if (keyBytes.size < 4) return "ssh-rsa"
        try {
            val len = ((keyBytes[0].toInt() and 0xFF) shl 24) or
                      ((keyBytes[1].toInt() and 0xFF) shl 16) or
                      ((keyBytes[2].toInt() and 0xFF) shl 8) or
                      (keyBytes[3].toInt() and 0xFF)
            if (len > 0 && len + 4 <= keyBytes.size) {
                return String(keyBytes, 4, len, Charsets.US_ASCII)
            }
        } catch (_: Exception) {}
        return "ssh-rsa"
    }

    private fun calculateMd5Fingerprint(keyBytes: ByteArray): String {
        if (keyBytes.isEmpty()) return "unknown"
        return try {
            val md = java.security.MessageDigest.getInstance("MD5")
            val digest = md.digest(keyBytes)
            digest.joinToString(":") { "%02x".format(it) }
        } catch (_: Exception) {
            "unknown"
        }
    }
}

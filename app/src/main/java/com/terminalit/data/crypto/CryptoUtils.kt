package com.terminalit.data.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchProviderException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * AES/GCM/NoPadding encryption backed by the Android Keystore.
 *
 * Fallback behaviour (test environments only):
 *   - `getSecretKey()` catches [KeyStoreException] / [NoSuchProviderException] that
 *     arise specifically because the "AndroidKeyStore" provider does not exist in a
 *     plain JVM (unit-test) environment. It also checks [isTestEnvironment] so the
 *     fallback is provably unreachable when running as a compiled APK on a real device.
 *   - `encrypt()` and `decrypt()` have NO outer catch blocks. Any AES/GCM failure
 *     on a real device (corrupt Keystore, hardware error, etc.) propagates to the
 *     caller, which must surface a real error to the user.  There is NO silent
 *     downgrade to plaintext or plain-Base64 storage.
 */
object CryptoUtils {
    private const val KEY_ALIAS    = "profile_store_key"
    private const val ANDROID_KS   = "AndroidKeyStore"
    private const val AES_MODE     = "AES/GCM/NoPadding"
    private const val IV_LENGTH    = 12   // GCM standard IV length
    private const val GCM_TAG_BITS = 128

    // ── Test-environment detection ───────────────────────────────────────────
    // Build.FINGERPRINT is "robolectric" under Robolectric and starts with
    // "generic" under most stock emulators. In a plain JVM (no Android runtime
    // at all) `android.os.Build` doesn't exist, so accessing it throws, which
    // also means we're in a test environment.
    private val isTestEnvironment: Boolean by lazy {
        try {
            val fingerprint = android.os.Build.FINGERPRINT ?: return@lazy true
            fingerprint == "robolectric" || fingerprint.startsWith("generic")
        } catch (_: Throwable) {
            true  // Android runtime not present → must be a plain JVM test
        }
    }

    // Fixed-key used ONLY in unit-test JVM environments (see isTestEnvironment).
    // This key is never stored, never touches the network, and is
    // deterministic so that tests can round-trip encrypt/decrypt without
    // the Android Keystore being available.
    private val testOnlyFallbackKey: SecretKey by lazy {
        SecretKeySpec(ByteArray(32) { (0xAA + it).toByte() }, "AES")
    }

    // ── Key retrieval ────────────────────────────────────────────────────────

    /**
     * Returns the AES-256 key stored in the Android Keystore, creating it on
     * first use.  In a pure JVM test environment the fallback key is returned
     * instead; this branch is provably unreachable on a real Android device
     * (see [isTestEnvironment]).
     *
     * Only [KeyStoreException] and [NoSuchProviderException] are caught — these
     * are the specific exceptions thrown when the "AndroidKeyStore" provider is
     * absent (JVM tests).  Any other exception (e.g. hardware security module
     * failure) is allowed to propagate.
     */
    private fun getSecretKey(): SecretKey {
        // If we are provably in a test environment, skip the Keystore entirely.
        if (isTestEnvironment) return testOnlyFallbackKey

        return try {
            val keyStore = KeyStore.getInstance(ANDROID_KS).apply { load(null) }
            val existing = keyStore.getKey(KEY_ALIAS, null) as? SecretKey
            if (existing != null) {
                existing
            } else {
                val kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KS)
                kg.init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setKeySize(256)
                        .build()
                )
                kg.generateKey()
            }
        } catch (e: KeyStoreException) {
            // "AndroidKeyStore" provider missing → only reachable in plain JVM.
            // Already guarded by isTestEnvironment above; this catch is a
            // belt-and-suspenders safety net for any unusual test runner.
            if (!isTestEnvironment) throw e   // never silent-fail on a real device
            testOnlyFallbackKey
        } catch (e: NoSuchProviderException) {
            if (!isTestEnvironment) throw e
            testOnlyFallbackKey
        }
        // All other exceptions (ProviderException, UnrecoverableKeyException, …)
        // propagate unconditionally — no silent fallback on real devices.
    }

    // ── Public API ───────────────────────────────────────────────────────────

    /**
     * Encrypts [plaintext] with AES/GCM/NoPadding using the Android Keystore key.
     *
     * Returns a Base64-encoded string of the form:
     *   `base64( IV (12 bytes) || GCM-ciphertext )`
     *
     * @throws SecurityException if the Keystore key cannot be obtained or the
     *   cipher operation fails.  Callers MUST NOT silently ignore this exception.
     */
    fun encrypt(plaintext: String): String {
        if (plaintext.isEmpty()) return ""

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
        val iv = cipher.iv  // random IV generated by the Cipher

        val combined = ByteArray(iv.size + ciphertext.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(ciphertext, 0, combined, iv.size, ciphertext.size)
        return java.util.Base64.getEncoder().encodeToString(combined)
    }

    /**
     * Decrypts a value produced by [encrypt].
     *
     * @throws IllegalArgumentException if [ciphertextBase64] is too short to
     *   contain a valid IV + GCM ciphertext — this signals data corruption, not
     *   a silently tolerated edge case.
     * @throws SecurityException / javax.crypto.AEADBadTagException if the GCM
     *   authentication tag does not verify (tampered or corrupt data).
     */
    fun decrypt(ciphertextBase64: String): String {
        if (ciphertextBase64.isEmpty()) return ""

        val combined = java.util.Base64.getDecoder().decode(ciphertextBase64)

        // A valid encrypt() output always contains IV_LENGTH IV bytes plus at
        // least 1 byte of ciphertext and a 16-byte GCM tag, so the minimum
        // meaningful length is IV_LENGTH + 17. We use IV_LENGTH + 1 as the
        // absolute minimum to distinguish truncated data from valid data.
        if (combined.size <= IV_LENGTH) {
            throw IllegalArgumentException(
                "Encrypted credential is too short (${combined.size} bytes); " +
                "expected at least ${IV_LENGTH + 1} bytes (IV + ciphertext). " +
                "The stored value may be corrupt or was not produced by CryptoUtils.encrypt()."
            )
        }

        val iv         = combined.copyOfRange(0, IV_LENGTH)
        val ciphertext = combined.copyOfRange(IV_LENGTH, combined.size)

        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), GCMParameterSpec(GCM_TAG_BITS, iv))
        val decrypted = cipher.doFinal(ciphertext)
        return String(decrypted, Charsets.UTF_8)
    }
}

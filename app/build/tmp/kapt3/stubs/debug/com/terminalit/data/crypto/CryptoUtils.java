package com.terminalit.data.crypto;

/**
 * AES/GCM/NoPadding encryption backed by the Android Keystore.
 *
 * Fallback behaviour (test environments only):
 *  - `getSecretKey()` catches [KeyStoreException] / [NoSuchProviderException] that
 *    arise specifically because the "AndroidKeyStore" provider does not exist in a
 *    plain JVM (unit-test) environment. It also checks [isTestEnvironment] so the
 *    fallback is provably unreachable when running as a compiled APK on a real device.
 *  - `encrypt()` and `decrypt()` have NO outer catch blocks. Any AES/GCM failure
 *    on a real device (corrupt Keystore, hardware error, etc.) propagates to the
 *    caller, which must surface a real error to the user.  There is NO silent
 *    downgrade to plaintext or plain-Base64 storage.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0004J\b\u0010\u0018\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\n\u0010\fR\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0013\u0010\u000e\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0019"}, d2 = {"Lcom/terminalit/data/crypto/CryptoUtils;", "", "()V", "AES_MODE", "", "ANDROID_KS", "GCM_TAG_BITS", "", "IV_LENGTH", "KEY_ALIAS", "isTestEnvironment", "", "()Z", "isTestEnvironment$delegate", "Lkotlin/Lazy;", "testOnlyFallbackKey", "Ljavax/crypto/SecretKey;", "getTestOnlyFallbackKey", "()Ljavax/crypto/SecretKey;", "testOnlyFallbackKey$delegate", "decrypt", "ciphertextBase64", "encrypt", "plaintext", "getSecretKey", "app_debug"})
public final class CryptoUtils {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ALIAS = "profile_store_key";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ANDROID_KS = "AndroidKeyStore";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String AES_MODE = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int GCM_TAG_BITS = 128;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy isTestEnvironment$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.Lazy testOnlyFallbackKey$delegate = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.terminalit.data.crypto.CryptoUtils INSTANCE = null;
    
    private CryptoUtils() {
        super();
    }
    
    private final boolean isTestEnvironment() {
        return false;
    }
    
    private final javax.crypto.SecretKey getTestOnlyFallbackKey() {
        return null;
    }
    
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
    private final javax.crypto.SecretKey getSecretKey() {
        return null;
    }
    
    /**
     * Encrypts [plaintext] with AES/GCM/NoPadding using the Android Keystore key.
     *
     * Returns a Base64-encoded string of the form:
     *  `base64( IV (12 bytes) || GCM-ciphertext )`
     *
     * @throws SecurityException if the Keystore key cannot be obtained or the
     *  cipher operation fails.  Callers MUST NOT silently ignore this exception.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String encrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String plaintext) {
        return null;
    }
    
    /**
     * Decrypts a value produced by [encrypt].
     *
     * @throws IllegalArgumentException if [ciphertextBase64] is too short to
     *  contain a valid IV + GCM ciphertext — this signals data corruption, not
     *  a silently tolerated edge case.
     * @throws SecurityException / javax.crypto.AEADBadTagException if the GCM
     *  authentication tag does not verify (tampered or corrupt data).
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String decrypt(@org.jetbrains.annotations.NotNull()
    java.lang.String ciphertextBase64) {
        return null;
    }
}
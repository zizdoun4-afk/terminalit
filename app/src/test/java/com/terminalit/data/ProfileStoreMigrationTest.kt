package com.terminalit.data

import com.terminalit.data.crypto.CryptoUtils
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileStoreMigrationTest {

    @Test
    fun testSafeDecryptWithValidEncryptedValue() {
        val originalText = "mySecurePassword123"
        val encrypted = CryptoUtils.encrypt(originalText)
        
        val (decrypted, migrated) = ProfileStore.safeDecrypt(encrypted)
        
        assertEquals("Decrypted value should match original text", originalText, decrypted)
        assertFalse("Valid ciphertext should not trigger migration", migrated)
    }

    @Test
    fun testSafeDecryptWithLegacyPlaintextValue() {
        val plaintext = "myLegacyPlaintextPassword"
        
        val (decrypted, migrated) = ProfileStore.safeDecrypt(plaintext)
        
        assertEquals("Decrypted value should fall back to legacy plaintext", plaintext, decrypted)
        assertTrue("Legacy plaintext should trigger migration", migrated)
    }

    @Test
    fun testSafeDecryptWithEmptyValue() {
        val (decrypted, migrated) = ProfileStore.safeDecrypt("")
        
        assertEquals("Empty string should return empty string", "", decrypted)
        assertFalse("Empty string should not trigger migration", migrated)
    }
}

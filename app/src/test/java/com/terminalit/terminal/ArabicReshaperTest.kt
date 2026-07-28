package com.terminalit.terminal

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ArabicReshaperTest {

    // Helper: reshape a string and return the shaped code points as a list
    private fun codePoints(text: String): List<Int> {
        val shaped = ArabicReshaper.reshape(text)
        val result = mutableListOf<Int>()
        var i = 0
        while (i < shaped.length) {
            val cp = shaped.codePointAt(i)
            result.add(cp)
            i += Character.charCount(cp)
        }
        return result
    }

    // Test 1: isolated Ba reshapes to isolated presentation form (U+FE8F)
    @Test
    fun isolatedBaReshapesToIsolatedPresentationForm() {
        val input = "\u0628"  // ب (Ba)
        val shaped = codePoints(input)
        assertEquals("Should have one code point", 1, shaped.size)
        assertEquals("Isolated Ba should become FE8F", 0xFE8F, shaped[0])
    }

    // Test 2: Lam followed by Alef produces Lam-Alef ligature (U+FEFB)
    @Test
    fun lamFollowedByAlefProducesLigature() {
        val input = "\u0644\u0627"  // لا
        val shaped = codePoints(input)
        assertEquals("Lam-Alef should produce exactly 1 ligature code point", 1, shaped.size)
        assertEquals("Lam-Alef isolated ligature should be U+FEFB", 0xFEFB, shaped[0])
    }

    // Test 3: Lam + Alef with Madda produces U+FEF5
    @Test
    fun lamFollowedByAlefMaddaProducesCorrectLigature() {
        val input = "\u0644\u0622"  // لآ
        val shaped = codePoints(input)
        assertEquals("Should be 1 ligature code point", 1, shaped.size)
        assertEquals("Lam-Alef-Madda isolated should be U+FEF5", 0xFEF5, shaped[0])
    }

    // Test 4: Medial form — middle Ba takes medial form (U+FE92) between two Ba's
    @Test
    fun middleLetterTakesMedialFormWhenSurroundedByDualJoiningLetters() {
        val input = "\u0628\u0628\u0628"  // بيب (three Ba's)
        val shaped = codePoints(input)
        assertEquals("Should have 3 shaped code points", 3, shaped.size)
        assertEquals("First Ba should be initial form U+FE91", 0xFE91, shaped[0])
        assertEquals("Middle Ba should be medial form U+FE92", 0xFE92, shaped[1])
        assertEquals("Last Ba should be final form U+FE90", 0xFE90, shaped[2])
    }

    // Test 5: Non-Arabic text is returned unchanged
    @Test
    fun nonArabicTextIsReturnedUnchanged() {
        val input = "Hello, World! 123"
        val output = ArabicReshaper.reshape(input)
        assertEquals("Non-Arabic text should not change", input, output)
    }

    // Test 6: Right-joining Alef breaks joining context; Ba after it is isolated (not initial)
    // Alef is type R (right-joining only), so it does NOT provide a D-joining left neighbour
    // for Ba. Ba therefore falls through to ISOLATED form (0xFE8F).
    @Test
    fun rightJoiningLetterBreaksJoiningContext() {
        val input = "\u0627\u0628"  // Alef then Ba
        val shaped = codePoints(input)
        assertEquals("Should have 2 code points", 2, shaped.size)
        // Alef: prev=none, next=Ba(D) → INITIAL; but Alef's INITIAL == ISOLATED == U+FE8D
        assertEquals("Alef isolated/initial form U+FE8D", 0xFE8D, shaped[0])
        // Ba: prev=Alef(R, not D), next=none → ISOLATED = U+FE8F
        assertEquals("Ba after right-joining Alef should be isolated form U+FE8F", 0xFE8F, shaped[1])
    }

    // Test 7: Mixed Arabic + Latin text — Arabic is shaped, Latin is unchanged
    @Test
    fun mixedArabicAndLatinTextArabicIsShapedLatinIsUnchanged() {
        val latin = "abc"
        val arabicBa = "\u0628"  // ب
        val input = "$latin$arabicBa"
        val output = ArabicReshaper.reshape(input)
        assert(output.startsWith(latin)) { "Latin prefix should be unchanged" }
        val arabicPart = output.substring(latin.length)
        val arabicCp = arabicPart.codePointAt(0)
        assertNotEquals("Arabic letter should be reshaped", 0x0628, arabicCp)
    }
}

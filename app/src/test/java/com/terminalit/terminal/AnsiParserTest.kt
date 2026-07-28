package com.terminalit.terminal

import com.terminalit.model.TerminalBuffer
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AnsiParserTest {

    private lateinit var buffer: TerminalBuffer
    private lateinit var parser: AnsiParser

    @Before
    fun setUp() {
        buffer = TerminalBuffer(initialCols = 80, initialRows = 24)
        parser = AnsiParser(buffer)
    }

    // Helper: read the first line of the buffer as a plain string (trimmed trailing spaces)
    private fun lineText(row: Int = 0): String {
        val snap = buffer.snapshot()
        val line = snap.lines.getOrNull(row) ?: return ""
        return line.map { cell ->
            if (cell.char.code == 0 || cell.char == '\u0000') ' ' else cell.char
        }.joinToString("").trimEnd()
    }

    // Test 1: UTF-8 two-byte sequence split across two feed() calls
    // U+00E9 = é = 0xC3 0xA9
    @Test
    fun split2ByteUtf8SequenceDecodesCorrectly() {
        parser.feed(byteArrayOf(0xC3.toByte()))  // first byte only
        parser.feed(byteArrayOf(0xA9.toByte()))  // continuation
        val text = lineText()
        assertEquals("First character should be é", 'é', text[0])
    }

    // Test 2: UTF-8 three-byte sequence split across two feed() calls
    // U+4E2D = 中 = 0xE4 0xB8 0xAD
    @Test
    fun split3ByteUtf8SequenceDecodesCorrectly() {
        parser.feed(byteArrayOf(0xE4.toByte(), 0xB8.toByte()))  // lead + 1 continuation
        parser.feed(byteArrayOf(0xAD.toByte()))                  // final continuation
        val text = lineText()
        assertEquals("First character should be 中", '中', text[0])
    }

    // Test 3: three-byte sequence split byte-by-byte
    @Test
    fun split3ByteUtf8OneByteAtATimeDecodesCorrectly() {
        parser.feed(byteArrayOf(0xE4.toByte()))
        parser.feed(byteArrayOf(0xB8.toByte()))
        parser.feed(byteArrayOf(0xAD.toByte()))
        val text = lineText()
        assertEquals("First character should be 中", '中', text[0])
    }

    // Test 4: CSI sequence split across two feed() calls
    // ESC [ 2 J = Erase Display (mode 2)
    @Test
    fun splitCsiEraseDisplaySequenceStillParsesCorrectly() {
        // Write "Hello" first
        parser.feed("Hello".toByteArray(Charsets.US_ASCII))
        assertEquals("Hello", lineText())

        // Send ESC [ as first chunk, then "2J" as second chunk
        parser.feed(byteArrayOf(0x1B.toByte(), 0x5B.toByte()))  // ESC [
        parser.feed("2J".toByteArray(Charsets.US_ASCII))         // 2 J (Erase Display)

        val allBlank = buffer.snapshot().lines.all { line ->
            line.all { cell -> cell.char.code == 0 || cell.char == '\u0000' || cell.char == ' ' }
        }
        assertEquals("Screen should be blank after ED(2)", true, allBlank)
    }

    // Test 5: CSI cursor movement split across feeds
    // ESC [ 3 ; 5 H = Move cursor to row 3, col 5 (1-based)
    @Test
    fun splitCsiCursorPositionCommandParsesCorrectly() {
        // First part: ESC [
        parser.feed(byteArrayOf(0x1B.toByte(), 0x5B.toByte()))
        // Second part: 3;5H
        parser.feed("3;5H".toByteArray(Charsets.US_ASCII))

        val snap = buffer.snapshot()
        // cursor row is 0-indexed internally (3-1=2), col (5-1=4)
        assertEquals("Cursor row should be 2 (0-indexed)", 2, snap.cursorRow)
        assertEquals("Cursor col should be 4 (0-indexed)", 4, snap.cursorCol)
    }

    // Test 6: Multiple split sequences interleaved
    @Test
    fun multipleSequentialSplitUtf8SequencesDecodeInOrder() {
        // Write 'é' (U+00E9) then '中' (U+4E2D) split across many feeds
        parser.feed(byteArrayOf(0xC3.toByte()))
        parser.feed(byteArrayOf(0xA9.toByte()))
        parser.feed(byteArrayOf(0xE4.toByte(), 0xB8.toByte()))
        parser.feed(byteArrayOf(0xAD.toByte()))

        val text = lineText()
        assertEquals("First char should be é", 'é', text[0])
        assertEquals("Second char should be 中", '中', text[1])
    }
}

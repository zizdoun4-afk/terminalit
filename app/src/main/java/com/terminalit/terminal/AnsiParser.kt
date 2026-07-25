package com.terminalit.terminal

import com.terminalit.model.TerminalBuffer
import com.terminalit.model.TerminalStyle
import kotlin.math.min

class AnsiParser(private val buffer: TerminalBuffer) {

    private enum class State {
        GROUND, ESCAPE, CSI_ENTRY, CSI_PARAM, CSI_INTERMEDIATE, CSI_IGNORE,
        OSC_STRING, DCS_ENTRY, DCS_INTERMEDIATE, DCS_PARAM, DCS_PASS_THROUGH,
        SOS_PM_APC_STRING
    }

    private var state = State.GROUND
    private val params = mutableListOf<Int>()
    private val intermediates = mutableListOf<Byte>()
    private var currentParam = 0
    private val oscStringBuilder = StringBuilder()

    private var utf8BytesNeeded = 0
    private var utf8CodePoint = 0

    private fun startUtf8Sequence(lead: Int) {
        when {
            lead in 0xC2..0xDF -> { utf8BytesNeeded = 1; utf8CodePoint = lead and 0x1F }
            lead in 0xE0..0xEF -> { utf8BytesNeeded = 2; utf8CodePoint = lead and 0x0F }
            lead in 0xF0..0xF4 -> { utf8BytesNeeded = 3; utf8CodePoint = lead and 0x07 }
            else -> utf8BytesNeeded = 0
        }
    }

    private fun feedUtf8Continuation(c: Int) {
        if (c !in 0x80..0xBF) {
            utf8BytesNeeded = 0
            processByte(c)
            return
        }
        utf8CodePoint = (utf8CodePoint shl 6) or (c and 0x3F)
        utf8BytesNeeded--
        if (utf8BytesNeeded > 0) return
        val valid = when (utf8CodePoint) {
            in 0x80..0x7FF -> true
            in 0x800..0xFFFF -> utf8CodePoint !in 0xD800..0xDFFF
            in 0x10000..0x10FFFF -> true
            else -> false
        }
        if (valid) writeCodePoint(utf8CodePoint)
    }

    private fun writeCodePoint(cp: Int) {
        if (cp < 0x10000) { buffer.writeChar(cp.toChar()); return }
        val high = ((cp - 0x10000) ushr 10) + 0xD800
        val low = (cp and 0x3FF) + 0xDC00
        buffer.writeChar(high.toChar())
        buffer.writeChar(low.toChar())
    }

    fun feed(data: ByteArray) {
        for (b in data) {
            val c = b.toInt() and 0xFF
            processByte(c)
        }
    }

    private fun processByte(c: Int) {
        when (state) {
            State.GROUND -> handleGround(c)
            State.ESCAPE -> handleEscape(c)
            State.CSI_ENTRY -> handleCsiEntry(c)
            State.CSI_PARAM -> handleCsiParam(c)
            State.CSI_INTERMEDIATE -> handleCsiIntermediate(c)
            State.CSI_IGNORE -> handleCsiIgnore(c)
            State.OSC_STRING -> handleOscString(c)
            State.DCS_ENTRY -> handleDcsEntry(c)
            State.DCS_PARAM -> handleDcsParam(c)
            State.DCS_INTERMEDIATE -> handleDcsIntermediate(c)
            State.DCS_PASS_THROUGH -> handleDcsPassThrough(c)
            State.SOS_PM_APC_STRING -> handleSosPmApc(c)
        }
    }

    private fun handleGround(c: Int) {
        if (utf8BytesNeeded > 0) {
            feedUtf8Continuation(c)
            return
        }
        when {
            c == 0x1B -> state = State.ESCAPE
            c == 0x0A -> buffer.writeChar('\n')
            c == 0x0D -> buffer.writeChar('\r')
            c == 0x08 -> buffer.writeChar('\b')
            c == 0x09 -> buffer.writeChar('\t')
            c == 0x07 -> buffer.writeChar('\u0007')
            c in 0x20..0x7E -> buffer.writeChar(c.toChar())
            c in 0x00..0x06 -> { /* control chars - ignore most */ }
            c in 0x0B..0x0C -> buffer.writeChar('\n')
            c in 0x0E..0x1F -> { /* ignore other controls */ }
            c in 0x80..0x9F -> handleC1(c)
            c in 0xC0..0xF7 -> startUtf8Sequence(c)
            c in 0xA0..0xBF -> buffer.writeChar(c.toChar())
            /* c >= 0xF8: invalid UTF-8, ignore */
        }
    }

    private fun handleC1(c: Int) {
        when (c) {
            0x84 -> buffer.index()          // IND
            0x85 -> buffer.nextLine()        // NEL
            0x88 -> buffer.tabSet()          // HTS
            0x8D -> buffer.reverseIndex()    // RI
            0x8E -> state = State.DCS_ENTRY  // SS2
            0x8F -> state = State.DCS_ENTRY  // SS3
            0x90 -> state = State.DCS_ENTRY  // DCS
            0x9B -> state = State.CSI_ENTRY  // CSI
            0x9D -> {                        // OSC
                state = State.OSC_STRING
                oscStringBuilder.clear()
            }
            0x98 -> state = State.SOS_PM_APC_STRING // SOS
            0x9E -> state = State.SOS_PM_APC_STRING // PM
            0x9F -> state = State.SOS_PM_APC_STRING // APC
            else -> { /* ignore */ }
        }
    }

    private fun handleEscape(c: Int) {
        state = State.GROUND
        when {
            c == 0x5B -> state = State.CSI_ENTRY  // ESC [
            c == 0x5D -> {                         // ESC ]
                state = State.OSC_STRING
                oscStringBuilder.clear()
            }
            c == 0x50 -> state = State.DCS_ENTRY   // ESC P
            c == 0x58 -> state = State.SOS_PM_APC_STRING // ESC X
            c == 0x5E -> state = State.SOS_PM_APC_STRING // ESC ^
            c == 0x5F -> state = State.SOS_PM_APC_STRING // ESC _
            c == 0x63 -> buffer.reset()            // RIS (full reset)
            c == 0x37 -> buffer.cursorSave()       // DECSC
            c == 0x38 -> buffer.cursorRestore()    // DECRC
            c == 0x44 -> buffer.index()            // IND
            c == 0x45 -> buffer.nextLine()         // NEL
            c == 0x48 -> buffer.tabSet()           // HTS
            c == 0x4D -> buffer.reverseIndex()     // RI
            c == 0x4C -> buffer.eraseLine(2)       // Clear line - undocumented but sometimes used
            c == 0x36 -> buffer.cursorSave()       // DECSC (alt)
            c == 0x3F -> buffer.cursorSave()       // DECSC (alt 2)
            c in 0x30..0x4F || c in 0x51..0x57 || c in 0x59..0x5A -> { /* 2-char sequence, already handled single char */ }
            c in 0x20..0x2F -> { /* intermediate char, collect */ }
            c in 0x60..0x7E -> { /* private sequence, ignore for now */ }
        }
    }

    private fun handleCsiEntry(c: Int) {
        when {
            c in 0x30..0x39 || c == 0x3B -> { // digits and semicolon
                state = State.CSI_PARAM
                params.clear()
                intermediates.clear()
                currentParam = 0
                handleCsiParam(c)
            }
            c == 0x3F || c == 0x3E -> { // private markers
                state = State.CSI_PARAM
                params.clear()
                intermediates.clear()
                currentParam = 0
                intermediates.add(c.toByte())
            }
            c in 0x20..0x2F -> { // intermediate
                state = State.CSI_INTERMEDIATE
                intermediates.clear()
                intermediates.add(c.toByte())
            }
            c in 0x40..0x7E -> { // final byte directly
                params.clear()
                intermediates.clear()
                executeCsi(c.toChar())
                state = State.GROUND
            }
            else -> state = State.GROUND
        }
    }

    private fun handleCsiParam(c: Int) {
        when {
            c in 0x30..0x39 -> {
                currentParam = currentParam * 10 + (c - 0x30)
            }
            c == 0x3B -> {
                params.add(currentParam)
                currentParam = 0
            }
            c in 0x3C..0x3F -> {
                intermediates.add(c.toByte())
            }
            c in 0x20..0x2F -> {
                state = State.CSI_INTERMEDIATE
                intermediates.add(c.toByte())
            }
            c in 0x40..0x7E -> {
                params.add(currentParam)
                executeCsi(c.toChar())
                state = State.GROUND
            }
            else -> state = State.CSI_IGNORE
        }
    }

    private fun handleCsiIntermediate(c: Int) {
        when {
            c in 0x20..0x2F -> intermediates.add(c.toByte())
            c in 0x40..0x7E -> {
                executeCsi(c.toChar())
                state = State.GROUND
            }
            else -> state = State.GROUND
        }
    }

    private fun handleCsiIgnore(c: Int) {
        if (c in 0x40..0x7E) state = State.GROUND
    }

    private fun executeCsi(final: Char) {
        val p = if (params.isEmpty()) listOf(0) else params.toList()
        val hasPrivateMarker = intermediates.contains(0x3F.toByte()) // '?' marker

        @Suppress("UNUSED_EXPRESSION")
        when (final) {
            'A' -> buffer.cursorUp(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'B' -> buffer.cursorDown(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'C' -> buffer.cursorForward(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'D' -> buffer.cursorBack(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'E' -> buffer.cursorDown(p.firstOrNull()?.takeIf { it > 0 } ?: 1).also { buffer.carriageReturn() }
            'F' -> buffer.cursorUp(p.firstOrNull()?.takeIf { it > 0 } ?: 1).also { buffer.carriageReturn() }
            'G' -> buffer.horizontalPosition((p.firstOrNull() ?: 1) - 1)
            'H' -> buffer.cursorPosition(
                p.getOrElse(0) { 1 } - 1,
                p.getOrElse(1) { 1 } - 1
            )
            'f' -> buffer.cursorPosition(
                p.getOrElse(0) { 1 } - 1,
                p.getOrElse(1) { 1 } - 1
            )
            'J' -> buffer.eraseDisplay(p.firstOrNull() ?: 0)
            'K' -> buffer.eraseLine(p.firstOrNull() ?: 0)
            'L' -> buffer.insertLines(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'M' -> buffer.deleteLines(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'P' -> buffer.deleteChars(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            '@' -> buffer.insertBlankChars(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'X' -> buffer.eraseChars(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'S' -> buffer.scrollUp(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'T' -> buffer.scrollDown(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'd' -> buffer.verticalPosition((p.firstOrNull() ?: 1) - 1)
            'e' -> buffer.verticalRelative(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'a' -> buffer.horizontalRelative(p.firstOrNull()?.takeIf { it > 0 } ?: 1)
            'm' -> executeSgr(p)
            's' -> buffer.cursorSave()
            'u' -> buffer.cursorRestore()
            'h' -> {
                if (hasPrivateMarker) {
                    p.forEach { executeDecPrivateSet(it) }
                }
            }
            'l' -> {
                if (hasPrivateMarker) {
                    p.forEach { executeDecPrivateReset(it) }
                }
            }
            'c' -> { /* Device Attributes - respond with terminal identity */ }
            'n' -> { /* Device Status Report - respond */ }
            'q' -> { /* LED/Keyboard LEDs - ignore */ }
            'r' -> { /* Set scroll region - simplify by ignoring */ }
        }
    }

    private fun executeSgr(params: List<Int>) {
        if (params.isEmpty() || (params.size == 1 && params[0] == 0)) {
            buffer.setStyle(TerminalStyle())
            return
        }

        var style = TerminalStyle()
        var i = 0
        while (i < params.size) {
            val p = params[i]
            when {
                p == 0 -> style = TerminalStyle()
                p == 1 -> style = style.copy(bold = true)
                p == 3 -> style = style.copy(italic = true)
                p == 4 -> style = style.copy(underline = true)
                p == 5 || p == 6 -> style = style.copy(blink = true)
                p == 7 -> style = style.copy(reverse = true)
                p == 9 -> style = style.copy(strikethrough = true)
                p == 22 -> style = style.copy(bold = false)
                p == 23 -> style = style.copy(italic = false)
                p == 24 -> style = style.copy(underline = false)
                p == 25 -> style = style.copy(blink = false)
                p == 27 -> style = style.copy(reverse = false)
                p == 29 -> style = style.copy(strikethrough = false)
                p in 30..37 -> {
                    val color = ansiToColor(p - 30, false)
                    style = style.copy(foreground = color)
                }
                p == 38 -> {
                    if (i + 1 < params.size) {
                        when (params[i + 1]) {
                            2 -> {
                                if (i + 4 < params.size) {
                                    val r = params[i + 2]
                                    val g = params[i + 3]
                                    val b = params[i + 4]
                                    style = style.copy(foreground = rgb(r, g, b))
                                    i += 4
                                }
                            }
                            5 -> {
                                if (i + 2 < params.size) {
                                    style = style.copy(foreground = index256(params[i + 2]))
                                    i += 2
                                }
                            }
                        }
                    }
                }
                p == 39 -> style = style.copy(foreground = TerminalStyle.DEFAULT_FG)
                p in 40..47 -> {
                    val color = ansiToColor(p - 40, false)
                    style = style.copy(background = color)
                }
                p == 48 -> {
                    if (i + 1 < params.size) {
                        when (params[i + 1]) {
                            2 -> {
                                if (i + 4 < params.size) {
                                    val r = params[i + 2]
                                    val g = params[i + 3]
                                    val b = params[i + 4]
                                    style = style.copy(background = rgb(r, g, b))
                                    i += 4
                                }
                            }
                            5 -> {
                                if (i + 2 < params.size) {
                                    style = style.copy(background = index256(params[i + 2]))
                                    i += 2
                                }
                            }
                        }
                    }
                }
                p == 49 -> style = style.copy(background = TerminalStyle.DEFAULT_BG)
                p in 90..97 -> {
                    val color = ansiToColor(p - 90, true)
                    style = style.copy(foreground = color)
                }
                p in 100..107 -> {
                    val color = ansiToColor(p - 100, true)
                    style = style.copy(background = color)
                }
            }
            i++
        }
        buffer.setStyle(style)
    }

    private fun executeDecPrivateSet(p: Int) {
        when (p) {
            25 -> { /* cursor visible — ignore for now */ }
            1049 -> { /* alt buffer — ignore (simplified) */ }
            2004 -> { /* bracketed paste mode — ignore */ }
        }
    }

    private fun executeDecPrivateReset(p: Int) {
        when (p) {
            25 -> { /* cursor hidden */ }
            1049 -> { /* return from alt buffer */ }
        }
    }

    private fun handleOscString(c: Int) {
        when {
            c == 0x07 -> { // BEL terminates OSC
                executeOsc(oscStringBuilder.toString())
                state = State.GROUND
            }
            c == 0x1B -> { // ST via ESC \
                state = State.ESCAPE
            }
            c == 0x9C -> { // ST directly
                executeOsc(oscStringBuilder.toString())
                state = State.GROUND
            }
            c >= 0x20 -> oscStringBuilder.append(c.toChar())
        }
    }

    private fun executeOsc(s: String) {
        val idx = s.indexOf(';')
        if (idx < 0) return
        val command = s.substring(0, idx)
        val data = s.substring(idx + 1)
        when (command.toIntOrNull()) {
            0, 1, 2 -> { /* set window title/icon — ignore */ }
            4, 104, 10, 11 -> { /* color palette change — ignore */ }
            112 -> { /* reset colors */ }
        }
    }

    private fun handleDcsEntry(c: Int) {
        when {
            c in 0x30..0x39 || c == 0x3B -> {
                state = State.DCS_PARAM
                params.clear()
                intermediates.clear()
                currentParam = 0
                handleDcsParam(c)
            }
            c in 0x20..0x2F -> {
                state = State.DCS_INTERMEDIATE
                intermediates.clear()
                intermediates.add(c.toByte())
            }
            c in 0x40..0x7E -> state = State.DCS_PASS_THROUGH
            else -> state = State.GROUND
        }
    }

    private fun handleDcsParam(c: Int) {
        when {
            c in 0x30..0x39 -> { currentParam = currentParam * 10 + (c - 0x30) }
            c == 0x3B -> { params.add(currentParam); currentParam = 0 }
            c in 0x20..0x2F -> { state = State.DCS_INTERMEDIATE; intermediates.add(c.toByte()) }
            c in 0x40..0x7E -> { params.add(currentParam); state = State.DCS_PASS_THROUGH }
            else -> state = State.GROUND
        }
    }

    private fun handleDcsIntermediate(c: Int) {
        when {
            c in 0x20..0x2F -> intermediates.add(c.toByte())
            c in 0x40..0x7E -> state = State.DCS_PASS_THROUGH
            else -> state = State.GROUND
        }
    }

    private fun handleDcsPassThrough(c: Int) {
        if (c == 0x9C || c == 0x1B) {
            state = State.GROUND
        }
    }

    private fun handleSosPmApc(c: Int) {
        if (c == 0x9C || (c == 0x1B)) state = State.GROUND
    }

    companion object {
        private fun ansiToColor(index: Int, bright: Boolean): Int {
            if (bright) {
                return when (index) {
                    0 -> 0xFF808080.toInt()
                    1 -> 0xFFFF5555.toInt()
                    2 -> 0xFF55FF55.toInt()
                    3 -> 0xFFFFFF55.toInt()
                    4 -> 0xFF5555FF.toInt()
                    5 -> 0xFFFF55FF.toInt()
                    6 -> 0xFF55FFFF.toInt()
                    7 -> 0xFFFFFFFF.toInt()
                    else -> 0xFFFFFFFF.toInt()
                }
            }
            return when (index) {
                0 -> 0xFF000000.toInt()
                1 -> 0xFFCC0000.toInt()
                2 -> 0xFF00CC00.toInt()
                3 -> 0xFFCCCC00.toInt()
                4 -> 0xFF0000CC.toInt()
                5 -> 0xFFCC00CC.toInt()
                6 -> 0xFF00CCCC.toInt()
                7 -> 0xFFCCCCCC.toInt()
                else -> 0xFFCCCCCC.toInt()
            }
        }

        private fun rgb(r: Int, g: Int, b: Int): Int {
            return (0xFF shl 24) or ((r and 0xFF) shl 16) or ((g and 0xFF) shl 8) or (b and 0xFF)
        }

        private fun index256(index: Int): Int {
            if (index < 16) {
                return ansiToColor(index % 8, index >= 8)
            }
            if (index < 232) {
                val i = index - 16
                val r = (i / 36) * 51
                val g = ((i % 36) / 6) * 51
                val b = (i % 6) * 51
                return rgb(r, g, b)
            }
            val gray = (index - 232) * 10 + 8
            return rgb(gray, gray, gray)
        }
    }
}

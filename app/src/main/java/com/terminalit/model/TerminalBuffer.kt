package com.terminalit.model

import kotlin.math.max
import kotlin.math.min

data class TerminalSnapshot(
    val lines: List<List<TerminalCell>>,
    val cursorRow: Int,
    val cursorCol: Int,
    val scrollOffset: Int
)

class TerminalBuffer(
    initialCols: Int = 80,
    initialRows: Int = 24
) {
    var cols = initialCols
        private set
    var rows = initialRows
        private set

    private val _lines = mutableListOf<MutableList<TerminalCell>>()
    private var cursorRow = 0
    private var cursorCol = 0
    private var currentStyle = TerminalStyle()
    private var scrollbackTop = 0
    private var scrollOffset = 0

    private var savedCursorRow = 0
    private var savedCursorCol = 0
    private var savedStyle = TerminalStyle()
    private var wrapNext = false

    private val scrollbackLimit = 10000

    init {
        resize(initialCols, initialRows)
    }

    fun resize(newCols: Int, newRows: Int) {
        cols = newCols
        rows = newRows
        while (_lines.size < newRows) {
            _lines.add(emptyLine())
        }
        while (_lines.size > newRows) {
            _lines.removeAt(0)
        }
        cursorRow = min(cursorRow, newRows - 1)
        cursorCol = min(cursorCol, newCols - 1)
    }

    fun snapshot(): TerminalSnapshot {
        val startIdx = max(0, _lines.size - rows - scrollOffset)
        val endIdx = min(_lines.size, startIdx + rows)
        val visible = _lines.subList(startIdx, endIdx).map { it.toList() }
        val padded = if (visible.size < rows) {
            visible + List(rows - visible.size) { emptyLine() }
        } else visible
        return TerminalSnapshot(
            lines = padded,
            cursorRow = if (scrollOffset == 0) cursorRow else -1,
            cursorCol = if (scrollOffset == 0) cursorCol else -1,
            scrollOffset = scrollOffset
        )
    }

    fun scrollBy(offset: Int) {
        val maxScroll = max(0, _lines.size - rows)
        scrollOffset = max(0, min(maxScroll, scrollOffset + offset))
    }

    fun scrollToBottom() {
        scrollOffset = 0
    }

    fun setStyle(style: TerminalStyle) {
        currentStyle = style
    }

    fun writeChar(c: Char) {
        when (c) {
            '\r' -> {
                cursorCol = 0
                wrapNext = false
            }
            '\n' -> {
                cursorRow++
                wrapNext = false
                scrollIfNeeded()
            }
            '\t' -> {
                val tabStop = ((cursorCol / 8) + 1) * 8
                cursorCol = min(tabStop, cols - 1)
            }
            '\b' -> {
                if (cursorCol > 0) cursorCol--
            }
            '\u0007' -> { /* BEL — ignore */ }
            else -> {
                if (wrapNext) {
                    cursorRow++
                    cursorCol = 0
                    scrollIfNeeded()
                    wrapNext = false
                }
                if (cursorCol >= cols) {
                    cursorRow++
                    cursorCol = 0
                    scrollIfNeeded()
                }
                ensureLineExists()
                val line = _lines[cursorRow]
                while (line.size <= cursorCol) {
                    line.add(TerminalCell.EMPTY)
                }
                line[cursorCol] = TerminalCell(c, currentStyle)
                cursorCol++
                if (cursorCol >= cols) {
                    wrapNext = true
                }
            }
        }
    }

    fun writeString(s: String) {
        for (c in s) writeChar(c)
    }

    fun cursorUp(n: Int = 1) {
        cursorRow = max(0, cursorRow - n)
        wrapNext = false
    }

    fun cursorDown(n: Int = 1) {
        if (_lines.isEmpty()) return
        cursorRow = min(_lines.size - 1, cursorRow + n)
        wrapNext = false
    }

    fun cursorForward(n: Int = 1) {
        cursorCol = min(cols - 1, cursorCol + n)
        wrapNext = false
    }

    fun cursorBack(n: Int = 1) {
        cursorCol = max(0, cursorCol - n)
        wrapNext = false
    }

    fun cursorPosition(row: Int, col: Int) {
        cursorRow = max(0, min(rows - 1, row))
        cursorCol = max(0, min(cols - 1, col))
        wrapNext = false
    }

    fun cursorSave() {
        savedCursorRow = cursorRow
        savedCursorCol = cursorCol
        savedStyle = currentStyle
    }

    fun cursorRestore() {
        cursorRow = savedCursorRow
        cursorCol = savedCursorCol
        currentStyle = savedStyle
    }

    fun eraseDisplay(mode: Int = 0) {
        when (mode) {
            0 -> {
                eraseLine(0)
                for (r in cursorRow + 1 until _lines.size) {
                    _lines[r] = emptyLine()
                }
            }
            1 -> {
                eraseLine(1)
                for (r in 0 until cursorRow) {
                    _lines[r] = emptyLine()
                }
            }
            2, 3 -> {
                for (r in _lines.indices) {
                    _lines[r] = emptyLine()
                }
            }
        }
    }

    fun eraseLine(mode: Int = 0) {
        ensureLineExists()
        val line = _lines[cursorRow]
        when (mode) {
            0 -> {
                for (c in cursorCol until cols) {
                    if (c < line.size) line[c] = TerminalCell.EMPTY
                    else line.add(TerminalCell.EMPTY)
                }
            }
            1 -> {
                for (c in 0..cursorCol) {
                    if (c < line.size) line[c] = TerminalCell.EMPTY
                    else line.add(TerminalCell.EMPTY)
                }
            }
            2 -> {
                _lines[cursorRow] = emptyLine()
            }
        }
    }

    fun eraseChars(n: Int = 1) {
        ensureLineExists()
        val line = _lines[cursorRow]
        for (i in 0 until n) {
            val idx = cursorCol + i
            if (idx < line.size) line[idx] = TerminalCell.EMPTY
        }
    }

    fun insertLines(n: Int = 1) {
        for (i in 0 until n) {
            _lines.add(cursorRow, emptyLine())
            if (_lines.size > rows + maxOf(0, scrollbackTop)) {
                _lines.removeAt(0)
                if (scrollbackTop > 0) scrollbackTop--
            }
        }
        while (_lines.size > rows + maxOf(0, scrollbackTop)) {
            _lines.removeAt(_lines.lastIndex)
        }
    }

    fun deleteLines(n: Int = 1) {
        for (i in 0 until n) {
            if (cursorRow < _lines.size) {
                _lines.removeAt(cursorRow)
                _lines.add(emptyLine())
            }
        }
    }

    fun scrollUp(n: Int = 1) {
        for (i in 0 until n) {
            _lines.add(emptyLine())
            _lines.removeAt(0)
        }
    }

    fun scrollDown(n: Int = 1) {
        for (i in 0 until n) {
            _lines.add(0, emptyLine())
            _lines.removeAt(_lines.lastIndex)
        }
    }

    fun insertBlankChars(n: Int = 1) {
        ensureLineExists()
        val line = _lines[cursorRow]
        for (i in 0 until n) {
            if (cursorCol < line.size) line.add(cursorCol, TerminalCell.EMPTY)
        }
        while (line.size > cols) line.removeAt(line.lastIndex)
    }

    fun deleteChars(n: Int = 1) {
        ensureLineExists()
        val line = _lines[cursorRow]
        for (i in 0 until n) {
            if (cursorCol < line.size) line.removeAt(cursorCol)
        }
        while (line.size < cols) line.add(TerminalCell.EMPTY)
    }

    fun newline() {
        cursorRow++
        cursorCol = 0
        wrapNext = false
        scrollIfNeeded()
    }

    fun carriageReturn() {
        cursorCol = 0
        wrapNext = false
    }

    fun horizontalPosition(col: Int) {
        cursorCol = max(0, min(cols - 1, col))
    }

    fun verticalPosition(row: Int) {
        cursorRow = max(0, min(rows - 1, row))
    }

    fun verticalRelative(n: Int) {
        cursorRow = max(0, min(_lines.size - 1, cursorRow + n))
    }

    fun horizontalRelative(n: Int) {
        cursorCol = max(0, min(cols - 1, cursorCol + n))
    }

    fun index() {
        cursorRow++
        scrollIfNeeded()
    }

    fun reverseIndex() {
        cursorRow--
        if (cursorRow < 0) {
            cursorRow = 0
            _lines.add(0, emptyLine())
            if (_lines.size > rows + scrollbackLimit) {
                _lines.removeAt(_lines.lastIndex)
            }
        }
    }

    fun nextLine() {
        cursorRow++
        cursorCol = 0
        scrollIfNeeded()
    }

    fun tabSet() {
        // Simplified: no tab stops, use fixed 8-char tabs
    }

    fun reset() {
        _lines.clear()
        cursorRow = 0
        cursorCol = 0
        currentStyle = TerminalStyle()
        scrollbackTop = 0
        scrollOffset = 0
        resize(cols, rows)
    }

    private fun ensureLineExists() {
        while (_lines.size <= cursorRow) {
            _lines.add(emptyLine())
        }
    }

    private fun scrollIfNeeded() {
        while (cursorRow >= _lines.size) {
            _lines.add(emptyLine())
        }
        while (cursorRow >= rows) {
            _lines.removeAt(0)
            cursorRow--
            scrollbackTop = max(0, scrollbackTop - 1)
        }
        while (_lines.size > rows + scrollbackLimit) {
            _lines.removeAt(0)
            scrollbackTop--
        }
    }

    private fun emptyLine(): MutableList<TerminalCell> {
        return MutableList(cols) { TerminalCell.EMPTY }
    }
}

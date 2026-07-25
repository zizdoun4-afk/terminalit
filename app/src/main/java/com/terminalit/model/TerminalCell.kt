package com.terminalit.model

data class TerminalStyle(
    val foreground: Int = DEFAULT_FG,
    val background: Int = DEFAULT_BG,
    val bold: Boolean = false,
    val italic: Boolean = false,
    val underline: Boolean = false,
    val strikethrough: Boolean = false,
    val reverse: Boolean = false,
    val blink: Boolean = false
) {
    companion object {
        const val DEFAULT_FG = -1
        const val DEFAULT_BG = -1
    }
}

data class TerminalCell(
    val char: Char = ' ',
    val style: TerminalStyle = TerminalStyle()
) {
    companion object {
        val EMPTY = TerminalCell(' ', TerminalStyle())
    }
}

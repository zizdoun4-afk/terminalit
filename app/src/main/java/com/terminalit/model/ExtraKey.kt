package com.terminalit.model

enum class ExtraKeyType {
    CTRL_MODIFIER,
    ALT_MODIFIER,
    ARROW,
    SYMBOL,
    PASTE,
    CUSTOM
}

data class ExtraKey(
    val id: String,
    val label: String,
    val type: ExtraKeyType,
    val payload: String = "",
    val isVisible: Boolean = true,
    val order: Int
)

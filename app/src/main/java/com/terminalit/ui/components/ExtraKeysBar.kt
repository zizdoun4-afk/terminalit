package com.terminalit.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.terminalit.model.ExtraKey
import com.terminalit.model.ExtraKeyType

@Composable
fun ExtraKeysBar(
    keys: List<ExtraKey>,
    onKey: (String) -> Unit,
    onPaste: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
    var isCtrlActive by remember { mutableStateOf(false) }
    var isAltActive by remember { mutableStateOf(false) }

    fun sendChar(charStr: String) {
        if (isCtrlActive && charStr.isNotEmpty()) {
            val first = charStr[0].uppercaseChar()
            if (first in 'A'..'Z') {
                val code = (first - 'A' + 1).toChar().toString()
                onKey(code)
            } else {
                onKey(charStr)
            }
            isCtrlActive = false
        } else if (isAltActive && charStr.isNotEmpty()) {
            onKey("\u001b$charStr")
            isAltActive = false
        } else {
            onKey(charStr)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        keys.filter { it.isVisible }.forEach { key ->
            when (key.type) {
                ExtraKeyType.PASTE -> {
                    IconButton(
                        onClick = {
                            val text = clipboardManager.getText()?.text
                            if (!text.isNullOrEmpty()) {
                                onPaste(text)
                            }
                        },
                        modifier = Modifier.height(36.dp).width(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentPaste,
                            contentDescription = "Paste",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                ExtraKeyType.CTRL_MODIFIER -> {
                    ExtraKeyButton(
                        label = key.label,
                        isActive = isCtrlActive,
                        onClick = { isCtrlActive = !isCtrlActive }
                    )
                }
                ExtraKeyType.ALT_MODIFIER -> {
                    ExtraKeyButton(
                        label = key.label,
                        isActive = isAltActive,
                        onClick = { isAltActive = !isAltActive }
                    )
                }
                ExtraKeyType.ARROW, ExtraKeyType.SYMBOL, ExtraKeyType.CUSTOM -> {
                    ExtraKeyButton(key.label) {
                        sendChar(key.payload)
                    }
                }
            }
        }
    }
}

@Composable
private fun ExtraKeyButton(
    label: String,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    val containerColor = if (isActive) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Button(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 8.dp, vertical = 2.dp
        )
    ) {
        Text(
            text = label,
            fontSize = 12.sp
        )
    }
}

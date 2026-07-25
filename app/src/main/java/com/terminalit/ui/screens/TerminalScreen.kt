package com.terminalit.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font as GoogleFontFont
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.terminalit.model.TerminalCell
import com.terminalit.model.TerminalSnapshot
import com.terminalit.model.TerminalStyle
import com.terminalit.repository.SessionState
import com.terminalit.terminal.ArabicReshaper
import com.terminalit.ui.components.ExtraKeysBar
import com.terminalit.viewmodel.TerminalViewModel

private val fontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = com.terminalit.R.array.com_google_android_gms_fonts_certs
)

private val monoGoogleFont = GoogleFont("Roboto Mono")
private val arabicGoogleFont = GoogleFont("Noto Sans Arabic")

private val terminalFontFamily = FontFamily(
    GoogleFontFont(googleFont = monoGoogleFont, fontProvider = fontProvider),
    GoogleFontFont(googleFont = arabicGoogleFont, fontProvider = fontProvider)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(
    viewModel: TerminalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var isFocused by remember { mutableStateOf(false) }

    val dummyText = " ".repeat(100)
    var textFieldValue by remember { mutableStateOf(TextFieldValue(dummyText, TextRange(dummyText.length))) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(uiState.isConnected) {
        if (uiState.isConnected) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D11))
            .statusBarsPadding()
            .imePadding()
    ) {
        // Sleek Top Header Bar
        TerminalTopBar(
            sessionState = uiState.sessionState,
            config = uiState.currentConfig,
            onDisconnect = { viewModel.disconnect() },
            onToggleTextarea = { viewModel.toggleTextareaMode() },
            onShowKeyboard = {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(keyboardController) {
                    detectTapGestures {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                }
        ) {
            TerminalView(
                snapshot = uiState.snapshot,
                modifier = Modifier.fillMaxSize()
            )

            // Invisible text input field to capture keyboard
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val oldText = textFieldValue.text
                    val newText = newValue.text

                    if (newText.length < oldText.length) {
                        val deleted = oldText.length - newText.length
                        viewModel.sendRaw(ByteArray(deleted) { 0x7F.toByte() })
                    } else if (newText.length > oldText.length) {
                        val added = newText.substring(oldText.length)
                        viewModel.sendText(added)
                    }

                    if (newText.length < 10 || newText.length > 200) {
                        textFieldValue = TextFieldValue(dummyText, TextRange(dummyText.length))
                    } else {
                        textFieldValue = newValue
                    }
                },
                modifier = Modifier
                    .size(1.dp)
                    .alpha(0f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = TextStyle(color = Color.Transparent, fontSize = 1.sp),
                cursorBrush = SolidColor(Color.Transparent),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.None
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                decorationBox = { _ -> }
            )
        }

        ExtraKeysBar(
            onKey = { key -> viewModel.sendText(key) },
            onPaste = { text ->
                val convertedText = text.replace("\n", "\r")
                viewModel.sendText(convertedText)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (uiState.textareaMode) {
        val sheetState = rememberModalBottomSheetState()
        ModalBottomSheet(
            onDismissRequest = { viewModel.toggleTextareaMode() },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = uiState.textareaContent,
                    onValueChange = viewModel::onTextareaContentChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    maxLines = 10,
                    label = { Text("Command") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Default
                    )
                )

                OutlinedButton(
                    onClick = { viewModel.sendTextareaContent() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("Send / Execute")
                }
            }
        }
    }
}

@Composable
private fun TerminalTopBar(
    sessionState: SessionState,
    config: com.terminalit.model.ConnectionConfig?,
    onDisconnect: () -> Unit,
    onToggleTextarea: () -> Unit,
    onShowKeyboard: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF181A20),
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                StatusBadge(sessionState)
                Spacer(modifier = Modifier.width(6.dp))
                
                val targetText = if (config != null) {
                    if (config.label.isNotBlank()) {
                        "${config.label} — ${config.username}@${config.host}"
                    } else {
                        "${config.username}@${config.host}"
                    }
                } else {
                    "Not connected"
                }

                Text(
                    text = targetText,
                    style = TextStyle(
                        fontFamily = terminalFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFF9E9E9E)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onShowKeyboard,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Keyboard,
                        contentDescription = "Show Keyboard",
                        tint = Color(0xFF64B5F6),
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onToggleTextarea,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Cmd Mode",
                        tint = Color(0xFF64B5F6),
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onDisconnect,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Disconnect",
                        tint = Color(0xFFEF5350),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(sessionState: SessionState) {
    val (statusColor, statusText) = when (sessionState) {
        is SessionState.Connected -> Color(0xFF4CAF50) to "Connected"
        is SessionState.Connecting -> Color(0xFFFFB300) to "Connecting..."
        is SessionState.Disconnected -> Color(0xFFE53935) to "Disconnected"
        is SessionState.Error -> Color(0xFFE53935) to "Error"
    }

    Surface(
        color = statusColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(color = statusColor, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = statusText,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    color = statusColor
                )
            )
        }
    }
}

@Composable
private fun TerminalView(
    snapshot: TerminalSnapshot,
    modifier: Modifier = Modifier
) {
    val verticalScroll = rememberScrollState()
    val horizontalScroll = rememberScrollState()

    // Auto-scroll to bottom when new output arrives or when viewport size changes (e.g. keyboard opens)
    LaunchedEffect(snapshot, verticalScroll.maxValue) {
        if (snapshot.scrollOffset == 0) {
            verticalScroll.scrollTo(verticalScroll.maxValue)
        }
    }

    Box(
        modifier = modifier
            .verticalScroll(verticalScroll)
            .horizontalScroll(horizontalScroll)
            .background(Color.Black)
    ) {
        SelectionContainer {
            Column(
                modifier = Modifier.padding(6.dp)
            ) {
                snapshot.lines.forEachIndexed { rowIndex, line ->
                    val isCursorLine = rowIndex == snapshot.cursorRow

                    TerminalLine(
                        cells = line,
                        cursorCol = if (isCursorLine) snapshot.cursorCol else -1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun TerminalLine(
    cells: List<TerminalCell>,
    cursorCol: Int,
    modifier: Modifier = Modifier
) {
    val annotatedString = androidx.compose.ui.text.AnnotatedString.Builder().apply {
        var currentStyle: TerminalStyle? = null
        var currentIsCursor = false
        val buffer = java.lang.StringBuilder()

        fun flush() {
            if (buffer.isEmpty()) return
            val style = currentStyle ?: return
            val isCursor = currentIsCursor

            val effectiveFg = if (style.reverse) style.background else style.foreground
            val effectiveBg = if (style.reverse) style.foreground else style.background

            val fgColor = if (effectiveFg != TerminalStyle.DEFAULT_FG) Color(effectiveFg) else Color(0xFFCCCCCC)
            val bgColor = if (effectiveBg != TerminalStyle.DEFAULT_BG && effectiveBg != TerminalStyle.DEFAULT_FG) Color(effectiveBg) else Color.Black

            if (isCursor) {
                pushStyle(
                    androidx.compose.ui.text.SpanStyle(
                        color = Color.Black,
                        background = Color(0xFFCCCCCC),
                        fontSize = 14.sp,
                        fontFamily = terminalFontFamily
                    )
                )
            } else {
                pushStyle(
                    androidx.compose.ui.text.SpanStyle(
                        color = fgColor,
                        background = bgColor,
                        fontSize = 14.sp,
                        fontFamily = terminalFontFamily,
                        fontWeight = if (style.bold) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                        fontStyle = if (style.italic) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal,
                        textDecoration = if (style.underline) androidx.compose.ui.text.style.TextDecoration.Underline else null
                    )
                )
            }
            append(buffer.toString())
            pop()
            buffer.clear()
        }

        cells.forEachIndexed { col, cell ->
            val isCursor = (col == cursorCol)
            
            if (currentStyle == null) {
                currentStyle = cell.style
                currentIsCursor = isCursor
            } else if (currentStyle != cell.style || currentIsCursor != isCursor) {
                flush()
                currentStyle = cell.style
                currentIsCursor = isCursor
            }

            val displayChar = if (isCursor) '\u2588' else cell.char
            val charStr = if (displayChar.code == 0 || displayChar == '\u0000') " " else displayChar.toString()
            buffer.append(charStr)
        }
        flush()
    }

    Text(
        text = annotatedString.toAnnotatedString(),
        style = TextStyle(
            fontFamily = terminalFontFamily,
            fontSize = 14.sp,
            lineHeight = 17.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Visible,
        modifier = modifier
    )
}

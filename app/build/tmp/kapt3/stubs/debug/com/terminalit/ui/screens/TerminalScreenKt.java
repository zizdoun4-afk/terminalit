package com.terminalit.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0003\u001a0\u0010\u000e\u001a\u00020\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00012\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0003\u001a\u0012\u0010\u0017\u001a\u00020\u000b2\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007\u001ah\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0014\u001a\u00020\u00012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001e2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u000b0\u001eH\u0003\u001a\"\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020%2\u0006\u0010\u0014\u001a\u00020\u00012\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"LINE_HEIGHT_RATIO", "", "MONO_CHAR_WIDTH_RATIO", "arabicGoogleFont", "Landroidx/compose/ui/text/googlefonts/GoogleFont;", "fontProvider", "Landroidx/compose/ui/text/googlefonts/GoogleFont$Provider;", "monoGoogleFont", "terminalFontFamily", "Landroidx/compose/ui/text/font/FontFamily;", "StatusBadge", "", "sessionState", "Lcom/terminalit/repository/SessionState;", "TerminalLine", "cells", "", "Lcom/terminalit/model/TerminalCell;", "cursorCol", "", "fontSizeSp", "modifier", "Landroidx/compose/ui/Modifier;", "TerminalScreen", "viewModel", "Lcom/terminalit/viewmodel/TerminalViewModel;", "TerminalTopBar", "config", "Lcom/terminalit/model/ConnectionConfig;", "onDisconnect", "Lkotlin/Function0;", "onToggleTextarea", "onShowKeyboard", "onFontIncrease", "onFontDecrease", "TerminalView", "snapshot", "Lcom/terminalit/model/TerminalSnapshot;", "app_debug"})
public final class TerminalScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont.Provider fontProvider = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont monoGoogleFont = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont arabicGoogleFont = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.font.FontFamily terminalFontFamily = null;
    private static final float MONO_CHAR_WIDTH_RATIO = 0.601F;
    private static final float LINE_HEIGHT_RATIO = 1.214F;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TerminalScreen(@org.jetbrains.annotations.NotNull()
    com.terminalit.viewmodel.TerminalViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalTopBar(com.terminalit.repository.SessionState sessionState, com.terminalit.model.ConnectionConfig config, float fontSizeSp, kotlin.jvm.functions.Function0<kotlin.Unit> onDisconnect, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleTextarea, kotlin.jvm.functions.Function0<kotlin.Unit> onShowKeyboard, kotlin.jvm.functions.Function0<kotlin.Unit> onFontIncrease, kotlin.jvm.functions.Function0<kotlin.Unit> onFontDecrease) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusBadge(com.terminalit.repository.SessionState sessionState) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalView(com.terminalit.model.TerminalSnapshot snapshot, float fontSizeSp, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalLine(java.util.List<com.terminalit.model.TerminalCell> cells, int cursorCol, float fontSizeSp, androidx.compose.ui.Modifier modifier) {
    }
}
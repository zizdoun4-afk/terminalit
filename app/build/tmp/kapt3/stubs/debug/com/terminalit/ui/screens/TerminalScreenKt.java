package com.terminalit.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0003\u001a(\u0010\u000b\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\u001a\u0012\u0010\u0013\u001a\u00020\b2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001aD\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u001a2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0003\u001a\u001a\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u0003\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"arabicGoogleFont", "Landroidx/compose/ui/text/googlefonts/GoogleFont;", "fontProvider", "Landroidx/compose/ui/text/googlefonts/GoogleFont$Provider;", "monoGoogleFont", "terminalFontFamily", "Landroidx/compose/ui/text/font/FontFamily;", "StatusBadge", "", "sessionState", "Lcom/terminalit/repository/SessionState;", "TerminalLine", "cells", "", "Lcom/terminalit/model/TerminalCell;", "cursorCol", "", "modifier", "Landroidx/compose/ui/Modifier;", "TerminalScreen", "viewModel", "Lcom/terminalit/viewmodel/TerminalViewModel;", "TerminalTopBar", "config", "Lcom/terminalit/model/ConnectionConfig;", "onDisconnect", "Lkotlin/Function0;", "onToggleTextarea", "onShowKeyboard", "TerminalView", "snapshot", "Lcom/terminalit/model/TerminalSnapshot;", "app_debug"})
public final class TerminalScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont.Provider fontProvider = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont monoGoogleFont = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.googlefonts.GoogleFont arabicGoogleFont = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.ui.text.font.FontFamily terminalFontFamily = null;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void TerminalScreen(@org.jetbrains.annotations.NotNull()
    com.terminalit.viewmodel.TerminalViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalTopBar(com.terminalit.repository.SessionState sessionState, com.terminalit.model.ConnectionConfig config, kotlin.jvm.functions.Function0<kotlin.Unit> onDisconnect, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleTextarea, kotlin.jvm.functions.Function0<kotlin.Unit> onShowKeyboard) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusBadge(com.terminalit.repository.SessionState sessionState) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalView(com.terminalit.model.TerminalSnapshot snapshot, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TerminalLine(java.util.List<com.terminalit.model.TerminalCell> cells, int cursorCol, androidx.compose.ui.Modifier modifier) {
    }
}
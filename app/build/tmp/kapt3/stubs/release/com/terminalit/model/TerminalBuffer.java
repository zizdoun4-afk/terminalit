package com.terminalit.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\'\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u0010\u001f\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u0010 \u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0016\u0010!\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u0003J\u0006\u0010$\u001a\u00020\u001cJ\u0006\u0010%\u001a\u00020\u001cJ\u0010\u0010&\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u0010\'\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u0010(\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u000e\u0010)\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0002J\b\u0010*\u001a\u00020\u001cH\u0002J\u0010\u0010+\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u0010,\u001a\u00020\u001c2\b\b\u0002\u0010-\u001a\u00020\u0003J\u0010\u0010.\u001a\u00020\u001c2\b\b\u0002\u0010-\u001a\u00020\u0003J\u000e\u0010/\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020\u0003J\u000e\u00100\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0003J\u0006\u00101\u001a\u00020\u001cJ\u0010\u00102\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0010\u00103\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u0006\u00104\u001a\u00020\u001cJ\u0006\u00105\u001a\u00020\u001cJ\u0006\u00106\u001a\u00020\u001cJ\u0016\u00107\u001a\u00020\u001c2\u0006\u00108\u001a\u00020\u00032\u0006\u00109\u001a\u00020\u0003J\u0006\u0010:\u001a\u00020\u001cJ\u000e\u0010;\u001a\u00020\u001c2\u0006\u0010<\u001a\u00020\u0003J\u0010\u0010=\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\b\u0010>\u001a\u00020\u001cH\u0002J\u0006\u0010?\u001a\u00020\u001cJ\u0010\u0010@\u001a\u00020\u001c2\b\b\u0002\u0010\u001e\u001a\u00020\u0003J\u000e\u0010A\u001a\u00020\u001c2\u0006\u0010B\u001a\u00020\u000eJ\u0006\u0010C\u001a\u00020DJ\u0006\u0010E\u001a\u00020\u001cJ\u000e\u0010F\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u0003J\u000e\u0010G\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0003J\u000e\u0010H\u001a\u00020\u001c2\u0006\u0010I\u001a\u00020JJ\u000e\u0010K\u001a\u00020\u001c2\u0006\u0010L\u001a\u00020MR\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0011\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0003@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\fR\u000e\u0010\u0013\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0003X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006N"}, d2 = {"Lcom/terminalit/model/TerminalBuffer;", "", "initialCols", "", "initialRows", "(II)V", "_lines", "", "Lcom/terminalit/model/TerminalCell;", "<set-?>", "cols", "getCols", "()I", "currentStyle", "Lcom/terminalit/model/TerminalStyle;", "cursorCol", "cursorRow", "rows", "getRows", "savedCursorCol", "savedCursorRow", "savedStyle", "scrollOffset", "scrollbackLimit", "scrollbackTop", "wrapNext", "", "carriageReturn", "", "cursorBack", "n", "cursorDown", "cursorForward", "cursorPosition", "row", "col", "cursorRestore", "cursorSave", "cursorUp", "deleteChars", "deleteLines", "emptyLine", "ensureLineExists", "eraseChars", "eraseDisplay", "mode", "eraseLine", "horizontalPosition", "horizontalRelative", "index", "insertBlankChars", "insertLines", "newline", "nextLine", "reset", "resize", "newCols", "newRows", "reverseIndex", "scrollBy", "offset", "scrollDown", "scrollIfNeeded", "scrollToBottom", "scrollUp", "setStyle", "style", "snapshot", "Lcom/terminalit/model/TerminalSnapshot;", "tabSet", "verticalPosition", "verticalRelative", "writeChar", "c", "", "writeString", "s", "", "app_release"})
public final class TerminalBuffer {
    private int cols;
    private int rows;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.util.List<com.terminalit.model.TerminalCell>> _lines = null;
    private int cursorRow = 0;
    private int cursorCol = 0;
    @org.jetbrains.annotations.NotNull()
    private com.terminalit.model.TerminalStyle currentStyle;
    private int scrollbackTop = 0;
    private int scrollOffset = 0;
    private int savedCursorRow = 0;
    private int savedCursorCol = 0;
    @org.jetbrains.annotations.NotNull()
    private com.terminalit.model.TerminalStyle savedStyle;
    private boolean wrapNext = false;
    private final int scrollbackLimit = 10000;
    
    public TerminalBuffer(int initialCols, int initialRows) {
        super();
    }
    
    public final int getCols() {
        return 0;
    }
    
    public final int getRows() {
        return 0;
    }
    
    public final void resize(int newCols, int newRows) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.terminalit.model.TerminalSnapshot snapshot() {
        return null;
    }
    
    public final void scrollBy(int offset) {
    }
    
    public final void scrollToBottom() {
    }
    
    public final void setStyle(@org.jetbrains.annotations.NotNull()
    com.terminalit.model.TerminalStyle style) {
    }
    
    public final void writeChar(char c) {
    }
    
    public final void writeString(@org.jetbrains.annotations.NotNull()
    java.lang.String s) {
    }
    
    public final void cursorUp(int n) {
    }
    
    public final void cursorDown(int n) {
    }
    
    public final void cursorForward(int n) {
    }
    
    public final void cursorBack(int n) {
    }
    
    public final void cursorPosition(int row, int col) {
    }
    
    public final void cursorSave() {
    }
    
    public final void cursorRestore() {
    }
    
    public final void eraseDisplay(int mode) {
    }
    
    public final void eraseLine(int mode) {
    }
    
    public final void eraseChars(int n) {
    }
    
    public final void insertLines(int n) {
    }
    
    public final void deleteLines(int n) {
    }
    
    public final void scrollUp(int n) {
    }
    
    public final void scrollDown(int n) {
    }
    
    public final void insertBlankChars(int n) {
    }
    
    public final void deleteChars(int n) {
    }
    
    public final void newline() {
    }
    
    public final void carriageReturn() {
    }
    
    public final void horizontalPosition(int col) {
    }
    
    public final void verticalPosition(int row) {
    }
    
    public final void verticalRelative(int n) {
    }
    
    public final void horizontalRelative(int n) {
    }
    
    public final void index() {
    }
    
    public final void reverseIndex() {
    }
    
    public final void nextLine() {
    }
    
    public final void tabSet() {
    }
    
    public final void reset() {
    }
    
    private final void ensureLineExists() {
    }
    
    private final void scrollIfNeeded() {
    }
    
    private final java.util.List<com.terminalit.model.TerminalCell> emptyLine() {
        return null;
    }
    
    public TerminalBuffer() {
        super();
    }
}
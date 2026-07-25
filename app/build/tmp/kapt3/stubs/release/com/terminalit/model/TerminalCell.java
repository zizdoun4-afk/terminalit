package com.terminalit.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0016"}, d2 = {"Lcom/terminalit/model/TerminalCell;", "", "char", "", "style", "Lcom/terminalit/model/TerminalStyle;", "(CLcom/terminalit/model/TerminalStyle;)V", "getChar", "()C", "getStyle", "()Lcom/terminalit/model/TerminalStyle;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Companion", "app_release"})
public final class TerminalCell {
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.model.TerminalStyle style = null;
    @org.jetbrains.annotations.NotNull()
    private static final com.terminalit.model.TerminalCell EMPTY = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.terminalit.model.TerminalCell.Companion Companion = null;
    
    public TerminalCell(char p0_1526187, @org.jetbrains.annotations.NotNull()
    com.terminalit.model.TerminalStyle style) {
        super();
    }
    
    public final char getChar() {
        return '\u0000';
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.terminalit.model.TerminalStyle getStyle() {
        return null;
    }
    
    public TerminalCell() {
        super();
    }
    
    public final char component1() {
        return '\u0000';
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.terminalit.model.TerminalStyle component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.terminalit.model.TerminalCell copy(char p0_1526187, @org.jetbrains.annotations.NotNull()
    com.terminalit.model.TerminalStyle style) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/terminalit/model/TerminalCell$Companion;", "", "()V", "EMPTY", "Lcom/terminalit/model/TerminalCell;", "getEMPTY", "()Lcom/terminalit/model/TerminalCell;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.terminalit.model.TerminalCell getEMPTY() {
            return null;
        }
    }
}
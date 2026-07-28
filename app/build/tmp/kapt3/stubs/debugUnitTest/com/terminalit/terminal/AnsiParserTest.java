package com.terminalit.terminal;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\fH\u0007J\b\u0010\u000e\u001a\u00020\fH\u0007J\b\u0010\u000f\u001a\u00020\fH\u0007J\b\u0010\u0010\u001a\u00020\fH\u0007J\b\u0010\u0011\u001a\u00020\fH\u0007J\b\u0010\u0012\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/terminalit/terminal/AnsiParserTest;", "", "()V", "buffer", "Lcom/terminalit/model/TerminalBuffer;", "parser", "Lcom/terminalit/terminal/AnsiParser;", "lineText", "", "row", "", "multipleSequentialSplitUtf8SequencesDecodeInOrder", "", "setUp", "split2ByteUtf8SequenceDecodesCorrectly", "split3ByteUtf8OneByteAtATimeDecodesCorrectly", "split3ByteUtf8SequenceDecodesCorrectly", "splitCsiCursorPositionCommandParsesCorrectly", "splitCsiEraseDisplaySequenceStillParsesCorrectly", "app_debugUnitTest"})
public final class AnsiParserTest {
    private com.terminalit.model.TerminalBuffer buffer;
    private com.terminalit.terminal.AnsiParser parser;
    
    public AnsiParserTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    private final java.lang.String lineText(int row) {
        return null;
    }
    
    @org.junit.Test()
    public final void split2ByteUtf8SequenceDecodesCorrectly() {
    }
    
    @org.junit.Test()
    public final void split3ByteUtf8SequenceDecodesCorrectly() {
    }
    
    @org.junit.Test()
    public final void split3ByteUtf8OneByteAtATimeDecodesCorrectly() {
    }
    
    @org.junit.Test()
    public final void splitCsiEraseDisplaySequenceStillParsesCorrectly() {
    }
    
    @org.junit.Test()
    public final void splitCsiCursorPositionCommandParsesCorrectly() {
    }
    
    @org.junit.Test()
    public final void multipleSequentialSplitUtf8SequencesDecodeInOrder() {
    }
}
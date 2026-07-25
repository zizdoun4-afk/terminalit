package com.terminalit.terminal;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0010\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u0007H\u0002J\u0018\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0007H\u0002J\u0018\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0007H\u0002J\u0018\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0007H\u0002J\u000e\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0014R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00100\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/terminalit/terminal/ArabicReshaper;", "", "()V", "ARABIC_LETTERS", "", "Lkotlin/ranges/IntRange;", "DIACRITICS", "", "FINAL", "INITIAL", "ISOLATED", "LAM_ALEF_FORMS", "", "", "MEDIAL", "joiningTypes", "", "presentationForms", "classifyDirection", "text", "", "containsArabic", "", "getJoiningType", "cp", "getNextNonDiacriticChar", "chars", "", "i", "getPrevChar", "getPrevNonDiacriticChar", "reshape", "app_release"})
public final class ArabicReshaper {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<kotlin.ranges.IntRange> ARABIC_LETTERS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.Integer, java.lang.Character> joiningTypes = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.Integer, java.util.List<java.lang.Integer>> presentationForms = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.Integer, java.util.List<java.lang.Integer>> LAM_ALEF_FORMS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Set<java.lang.Integer> DIACRITICS = null;
    private static final int ISOLATED = 0;
    private static final int FINAL = 1;
    private static final int INITIAL = 2;
    private static final int MEDIAL = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.terminalit.terminal.ArabicReshaper INSTANCE = null;
    
    private ArabicReshaper() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String reshape(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    public final int classifyDirection(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return 0;
    }
    
    private final boolean containsArabic(java.lang.String text) {
        return false;
    }
    
    private final char getJoiningType(int cp) {
        return '\u0000';
    }
    
    private final int getPrevChar(int[] chars, int i) {
        return 0;
    }
    
    private final int getPrevNonDiacriticChar(int[] chars, int i) {
        return 0;
    }
    
    private final int getNextNonDiacriticChar(int[] chars, int i) {
        return 0;
    }
}
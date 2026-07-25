package com.terminalit.security;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\bJ4\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\bH\u0002\u00a8\u0006\r"}, d2 = {"Lcom/terminalit/security/TerminalitBiometricManager;", "", "()V", "authenticate", "", "activity", "Landroidx/fragment/app/FragmentActivity;", "onSuccess", "Lkotlin/Function0;", "onCancelOrError", "showPrompt", "authenticators", "", "app_debug"})
public final class TerminalitBiometricManager {
    @org.jetbrains.annotations.NotNull()
    public static final com.terminalit.security.TerminalitBiometricManager INSTANCE = null;
    
    private TerminalitBiometricManager() {
        super();
    }
    
    public final void authenticate(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.FragmentActivity activity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancelOrError) {
    }
    
    private final void showPrompt(androidx.fragment.app.FragmentActivity activity, int authenticators, kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess, kotlin.jvm.functions.Function0<kotlin.Unit> onCancelOrError) {
    }
}
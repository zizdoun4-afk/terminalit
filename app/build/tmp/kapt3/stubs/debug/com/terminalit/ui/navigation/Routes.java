package com.terminalit.ui.navigation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\b\u001a\u00020\u00042\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/terminalit/ui/navigation/Routes;", "", "()V", "CONNECTION", "", "EXTRA_KEYS_CONFIG", "PROFILE_LIST", "TERMINAL", "connection", "profileId", "app_debug"})
public final class Routes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PROFILE_LIST = "profile_list";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CONNECTION = "connection?profileId={profileId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TERMINAL = "terminal";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_KEYS_CONFIG = "extra_keys_config";
    @org.jetbrains.annotations.NotNull()
    public static final com.terminalit.ui.navigation.Routes INSTANCE = null;
    
    private Routes() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String connection(@org.jetbrains.annotations.Nullable()
    java.lang.String profileId) {
        return null;
    }
}
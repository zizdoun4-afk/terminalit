package com.terminalit.ssh;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JJ\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\"\u0010\u0019\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001aH\u0086@\u00a2\u0006\u0002\u0010\u001dJ \u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010 \u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J:\u0010!\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\"\u0010\u0019\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001aH\u0086@\u00a2\u0006\u0002\u0010\"J\u0006\u0010#\u001a\u00020\u0012J\u0010\u0010$\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010%\u001a\u00020&J2\u0010\'\u001a\u00020\u00122\"\u0010\u0019\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u001b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001aH\u0086@\u00a2\u0006\u0002\u0010(J2\u0010)\u001a\u00020\u00122\"\u0010*\u001a\u001e\b\u0001\u0012\u0004\u0012\u00020\u0018\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u001c\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u001aH\u0086@\u00a2\u0006\u0002\u0010(J\u0016\u0010+\u001a\u00020\u00122\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-J\u0016\u0010/\u001a\u00020\u00122\u0006\u00100\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u00101R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/terminalit/ssh/SshManager;", "", "hostKeyStore", "Lcom/terminalit/data/HostKeyStore;", "(Lcom/terminalit/data/HostKeyStore;)V", "channel", "Lcom/jcraft/jsch/ChannelShell;", "connected", "Ljava/util/concurrent/atomic/AtomicBoolean;", "inputStream", "Ljava/io/InputStream;", "jsch", "Lcom/jcraft/jsch/JSch;", "outputStream", "Ljava/io/OutputStream;", "session", "Lcom/jcraft/jsch/Session;", "acceptHostKeyAndSave", "", "config", "Lcom/terminalit/model/ConnectionConfig;", "type", "", "keyBytes", "", "onEvent", "Lkotlin/Function2;", "Lcom/terminalit/ssh/SshEvent;", "Lkotlin/coroutines/Continuation;", "(Lcom/terminalit/model/ConnectionConfig;Ljava/lang/String;[BLkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buildHostKeyInfo", "Lcom/terminalit/ssh/HostKeyInfo;", "calculateMd5Fingerprint", "connectSession", "(Lcom/terminalit/model/ConnectionConfig;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "extractSshKeyType", "isConnected", "", "openChannel", "(Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "readOutput", "onData", "resizePty", "cols", "", "rows", "write", "data", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class SshManager {
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.data.HostKeyStore hostKeyStore = null;
    @org.jetbrains.annotations.Nullable()
    private com.jcraft.jsch.JSch jsch;
    @org.jetbrains.annotations.Nullable()
    private com.jcraft.jsch.Session session;
    @org.jetbrains.annotations.Nullable()
    private com.jcraft.jsch.ChannelShell channel;
    @org.jetbrains.annotations.Nullable()
    private java.io.InputStream inputStream;
    @org.jetbrains.annotations.Nullable()
    private java.io.OutputStream outputStream;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.atomic.AtomicBoolean connected = null;
    
    public SshManager(@org.jetbrains.annotations.NotNull()
    com.terminalit.data.HostKeyStore hostKeyStore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object connectSession(@org.jetbrains.annotations.NotNull()
    com.terminalit.model.ConnectionConfig config, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.terminalit.ssh.SshEvent, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> onEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object acceptHostKeyAndSave(@org.jetbrains.annotations.NotNull()
    com.terminalit.model.ConnectionConfig config, @org.jetbrains.annotations.NotNull()
    java.lang.String type, @org.jetbrains.annotations.NotNull()
    byte[] keyBytes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.terminalit.ssh.SshEvent, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> onEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object openChannel(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.terminalit.ssh.SshEvent, ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> onEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object readOutput(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super byte[], ? super kotlin.coroutines.Continuation<? super kotlin.Unit>, ? extends java.lang.Object> onData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object write(@org.jetbrains.annotations.NotNull()
    byte[] data, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void resizePty(int cols, int rows) {
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    public final void disconnect() {
    }
    
    private final com.terminalit.ssh.HostKeyInfo buildHostKeyInfo(com.terminalit.model.ConnectionConfig config, java.lang.String type, byte[] keyBytes) {
        return null;
    }
    
    private final java.lang.String extractSshKeyType(byte[] keyBytes) {
        return null;
    }
    
    private final java.lang.String calculateMd5Fingerprint(byte[] keyBytes) {
        return null;
    }
}
package com.terminalit.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\b\u0007\u0018\u00002\u00020\u0001:\u0001OB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u00107\u001a\u0002082\u0006\u00109\u001a\u00020\u001dJ\u0006\u0010:\u001a\u00020\u0007J\u000e\u0010;\u001a\u00020\u00072\u0006\u0010<\u001a\u00020\nJ\u0006\u0010=\u001a\u00020\u0007J\u0010\u0010>\u001a\u00020\u00072\u0006\u0010?\u001a\u00020@H\u0002J\u0010\u0010A\u001a\u00020\u00072\u0006\u00109\u001a\u00020\u001dH\u0002J\u0010\u0010B\u001a\u00020\u00072\u0006\u00109\u001a\u00020\u001dH\u0002J\u0016\u0010C\u001a\u00020\u00072\u0006\u0010D\u001a\u00020\u001e2\u0006\u0010E\u001a\u00020\u001eJ\u000e\u0010F\u001a\u00020\u00072\u0006\u0010G\u001a\u00020\u001eJ\u0006\u0010H\u001a\u00020\u0007J\u000e\u0010I\u001a\u00020\u00072\u0006\u0010J\u001a\u00020\u001dJ\u000e\u0010K\u001a\u00020\u00072\u0006\u0010L\u001a\u00020#J\b\u0010M\u001a\u00020\u0007H\u0002J\b\u0010N\u001a\u00020\u0007H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020#0\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010$\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\'0\"X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020)0\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020-X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010\u001aR\u000e\u00101\u001a\u000202X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u00105\u001a\b\u0012\u0004\u0012\u00020\u00100\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010\u001a\u00a8\u0006P"}, d2 = {"Lcom/terminalit/repository/SessionRepository;", "", "hostKeyStore", "Lcom/terminalit/data/HostKeyStore;", "(Lcom/terminalit/data/HostKeyStore;)V", "_bellFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "_currentConfig", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/terminalit/model/ConnectionConfig;", "_hostKeyRequest", "Lcom/terminalit/repository/HostKeyRequest;", "_sessionState", "Lcom/terminalit/repository/SessionState;", "_terminalSnapshot", "Lcom/terminalit/model/TerminalSnapshot;", "ansiParser", "Lcom/terminalit/terminal/AnsiParser;", "bellFlow", "Lkotlinx/coroutines/flow/SharedFlow;", "getBellFlow", "()Lkotlinx/coroutines/flow/SharedFlow;", "currentConfig", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentConfig", "()Lkotlinx/coroutines/flow/StateFlow;", "failedAttempts", "Ljava/util/concurrent/ConcurrentHashMap;", "", "", "hostKeyRequest", "getHostKeyRequest", "inputChannel", "Lkotlinx/coroutines/channels/Channel;", "", "inputJob", "Lkotlinx/coroutines/Job;", "ioCommandChannel", "Lcom/terminalit/repository/SessionRepository$IoCommand;", "lockoutUntil", "", "outputJob", "pendingConfig", "scope", "Lkotlinx/coroutines/CoroutineScope;", "sessionJob", "sessionState", "getSessionState", "sshManager", "Lcom/terminalit/ssh/SshManager;", "terminalBuffer", "Lcom/terminalit/model/TerminalBuffer;", "terminalSnapshot", "getTerminalSnapshot", "checkLockout", "", "profileId", "cleanup", "connect", "config", "disconnect", "handleEvent", "event", "Lcom/terminalit/ssh/SshEvent;", "recordAuthFailure", "resetAuthFailures", "resizeTerminal", "cols", "rows", "scrollBy", "offset", "scrollToBottom", "sendInput", "text", "sendRaw", "data", "startInputPipeline", "startOutputPipeline", "IoCommand", "app_debug"})
public final class SessionRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.data.HostKeyStore hostKeyStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.ssh.SshManager sshManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.model.TerminalBuffer terminalBuffer = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Unit> _bellFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> bellFlow = null;
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.terminal.AnsiParser ansiParser = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job sessionJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job inputJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job outputJob;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Integer> failedAttempts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Long> lockoutUntil = null;
    @org.jetbrains.annotations.Nullable()
    private com.terminalit.model.ConnectionConfig pendingConfig;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.terminalit.model.ConnectionConfig> _currentConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.model.ConnectionConfig> currentConfig = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.terminalit.repository.SessionState> _sessionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.repository.SessionState> sessionState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.terminalit.model.TerminalSnapshot> _terminalSnapshot = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.model.TerminalSnapshot> terminalSnapshot = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.terminalit.repository.HostKeyRequest> _hostKeyRequest = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.terminalit.repository.HostKeyRequest> hostKeyRequest = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<byte[]> inputChannel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.terminalit.repository.SessionRepository.IoCommand> ioCommandChannel = null;
    
    @javax.inject.Inject()
    public SessionRepository(@org.jetbrains.annotations.NotNull()
    com.terminalit.data.HostKeyStore hostKeyStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<kotlin.Unit> getBellFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.model.ConnectionConfig> getCurrentConfig() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.repository.SessionState> getSessionState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.model.TerminalSnapshot> getTerminalSnapshot() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.terminalit.repository.HostKeyRequest> getHostKeyRequest() {
        return null;
    }
    
    public final boolean checkLockout(@org.jetbrains.annotations.NotNull()
    java.lang.String profileId) {
        return false;
    }
    
    private final void recordAuthFailure(java.lang.String profileId) {
    }
    
    private final void resetAuthFailures(java.lang.String profileId) {
    }
    
    public final void connect(@org.jetbrains.annotations.NotNull()
    com.terminalit.model.ConnectionConfig config) {
    }
    
    private final void handleEvent(com.terminalit.ssh.SshEvent event) {
    }
    
    private final void startInputPipeline() {
    }
    
    private final void startOutputPipeline() {
    }
    
    public final void sendInput(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void sendRaw(@org.jetbrains.annotations.NotNull()
    byte[] data) {
    }
    
    public final void resizeTerminal(int cols, int rows) {
    }
    
    public final void scrollBy(int offset) {
    }
    
    public final void scrollToBottom() {
    }
    
    public final void disconnect() {
    }
    
    public final void cleanup() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/terminalit/repository/SessionRepository$IoCommand;", "", "()V", "Data", "Resize", "Lcom/terminalit/repository/SessionRepository$IoCommand$Data;", "Lcom/terminalit/repository/SessionRepository$IoCommand$Resize;", "app_debug"})
    static abstract class IoCommand {
        
        private IoCommand() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/terminalit/repository/SessionRepository$IoCommand$Data;", "Lcom/terminalit/repository/SessionRepository$IoCommand;", "bytes", "", "([B)V", "getBytes", "()[B", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Data extends com.terminalit.repository.SessionRepository.IoCommand {
            @org.jetbrains.annotations.NotNull()
            private final byte[] bytes = null;
            
            public Data(@org.jetbrains.annotations.NotNull()
            byte[] bytes) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final byte[] getBytes() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final byte[] component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.terminalit.repository.SessionRepository.IoCommand.Data copy(@org.jetbrains.annotations.NotNull()
            byte[] bytes) {
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
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/terminalit/repository/SessionRepository$IoCommand$Resize;", "Lcom/terminalit/repository/SessionRepository$IoCommand;", "cols", "", "rows", "(II)V", "getCols", "()I", "getRows", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
        public static final class Resize extends com.terminalit.repository.SessionRepository.IoCommand {
            private final int cols = 0;
            private final int rows = 0;
            
            public Resize(int cols, int rows) {
            }
            
            public final int getCols() {
                return 0;
            }
            
            public final int getRows() {
                return 0;
            }
            
            public final int component1() {
                return 0;
            }
            
            public final int component2() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.terminalit.repository.SessionRepository.IoCommand.Resize copy(int cols, int rows) {
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
        }
    }
}
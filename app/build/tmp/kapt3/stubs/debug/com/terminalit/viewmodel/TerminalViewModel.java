package com.terminalit.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0012\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u0016J\u0006\u0010\u001a\u001a\u00020\u000fJ\u0006\u0010\u001b\u001a\u00020\u000fJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001eJ\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0013J\u0006\u0010!\u001a\u00020\u000fJ\u0006\u0010\"\u001a\u00020\u000fR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006#"}, d2 = {"Lcom/terminalit/viewmodel/TerminalViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/terminalit/repository/SessionRepository;", "extraKeyStore", "Lcom/terminalit/data/ExtraKeyStore;", "(Lcom/terminalit/repository/SessionRepository;Lcom/terminalit/data/ExtraKeyStore;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/terminalit/viewmodel/TerminalUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearTerminal", "", "disconnect", "onTextareaContentChanged", "content", "", "resizeTerminal", "cols", "", "rows", "scrollBy", "offset", "scrollToBottom", "sendEnter", "sendRaw", "data", "", "sendText", "text", "sendTextareaContent", "toggleTextareaMode", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TerminalViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.repository.SessionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.data.ExtraKeyStore extraKeyStore = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.terminalit.viewmodel.TerminalUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.viewmodel.TerminalUiState> uiState = null;
    
    @javax.inject.Inject()
    public TerminalViewModel(@org.jetbrains.annotations.NotNull()
    com.terminalit.repository.SessionRepository repository, @org.jetbrains.annotations.NotNull()
    com.terminalit.data.ExtraKeyStore extraKeyStore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.viewmodel.TerminalUiState> getUiState() {
        return null;
    }
    
    public final void sendText(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void sendRaw(@org.jetbrains.annotations.NotNull()
    byte[] data) {
    }
    
    public final void sendEnter() {
    }
    
    public final void scrollBy(int offset) {
    }
    
    public final void scrollToBottom() {
    }
    
    public final void toggleTextareaMode() {
    }
    
    public final void onTextareaContentChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String content) {
    }
    
    public final void sendTextareaContent() {
    }
    
    public final void disconnect() {
    }
    
    public final void resizeTerminal(int cols, int rows) {
    }
    
    public final void clearTerminal() {
    }
}
package com.terminalit.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\u0018J\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u000fJ\u000e\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u000fJ\u000e\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u000fJ\u000e\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u00182\u0006\u0010\'\u001a\u00020\u000fJ\u000e\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020\u000fJ\u000e\u0010*\u001a\u00020\u00182\u0006\u0010+\u001a\u00020\u000fJ\u0006\u0010,\u001a\u00020\u0018J\u0006\u0010-\u001a\u00020\u0018R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0014\u00a8\u0006."}, d2 = {"Lcom/terminalit/viewmodel/ConnectionViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "repository", "Lcom/terminalit/repository/SessionRepository;", "profileStore", "Lcom/terminalit/data/ProfileStore;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "application", "Landroid/app/Application;", "(Lcom/terminalit/repository/SessionRepository;Lcom/terminalit/data/ProfileStore;Landroidx/lifecycle/SavedStateHandle;Landroid/app/Application;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/terminalit/viewmodel/ConnectionUiState;", "profileId", "", "sessionState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/terminalit/repository/SessionState;", "getSessionState", "()Lkotlinx/coroutines/flow/StateFlow;", "uiState", "getUiState", "acceptHostKey", "", "clearError", "connect", "onAuthTypeChanged", "useKey", "", "onHostChanged", "host", "onLabelChanged", "label", "onPassphraseChanged", "passphrase", "onPasswordChanged", "password", "onPortChanged", "port", "onPrivateKeyChanged", "keyData", "onUsernameChanged", "username", "rejectHostKey", "saveConnection", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ConnectionViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.repository.SessionRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.terminalit.data.ProfileStore profileStore = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.SavedStateHandle savedStateHandle = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String profileId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.terminalit.viewmodel.ConnectionUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.viewmodel.ConnectionUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.terminalit.repository.SessionState> sessionState = null;
    
    @javax.inject.Inject()
    public ConnectionViewModel(@org.jetbrains.annotations.NotNull()
    com.terminalit.repository.SessionRepository repository, @org.jetbrains.annotations.NotNull()
    com.terminalit.data.ProfileStore profileStore, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.viewmodel.ConnectionUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.terminalit.repository.SessionState> getSessionState() {
        return null;
    }
    
    public final void onLabelChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String label) {
    }
    
    public final void onHostChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String host) {
    }
    
    public final void onPortChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String port) {
    }
    
    public final void onUsernameChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String username) {
    }
    
    public final void onPasswordChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void onAuthTypeChanged(boolean useKey) {
    }
    
    public final void onPrivateKeyChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String keyData) {
    }
    
    public final void onPassphraseChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String passphrase) {
    }
    
    public final void saveConnection() {
    }
    
    public final void connect() {
    }
    
    public final void acceptHostKey() {
    }
    
    public final void rejectHostKey() {
    }
    
    public final void clearError() {
    }
}
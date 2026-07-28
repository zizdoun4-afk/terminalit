package com.terminalit.viewmodel;

import com.terminalit.data.ExtraKeyStore;
import com.terminalit.repository.SessionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class TerminalViewModel_Factory implements Factory<TerminalViewModel> {
  private final Provider<SessionRepository> repositoryProvider;

  private final Provider<ExtraKeyStore> extraKeyStoreProvider;

  public TerminalViewModel_Factory(Provider<SessionRepository> repositoryProvider,
      Provider<ExtraKeyStore> extraKeyStoreProvider) {
    this.repositoryProvider = repositoryProvider;
    this.extraKeyStoreProvider = extraKeyStoreProvider;
  }

  @Override
  public TerminalViewModel get() {
    return newInstance(repositoryProvider.get(), extraKeyStoreProvider.get());
  }

  public static TerminalViewModel_Factory create(Provider<SessionRepository> repositoryProvider,
      Provider<ExtraKeyStore> extraKeyStoreProvider) {
    return new TerminalViewModel_Factory(repositoryProvider, extraKeyStoreProvider);
  }

  public static TerminalViewModel newInstance(SessionRepository repository,
      ExtraKeyStore extraKeyStore) {
    return new TerminalViewModel(repository, extraKeyStore);
  }
}

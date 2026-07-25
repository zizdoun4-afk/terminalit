package com.terminalit.viewmodel;

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

  public TerminalViewModel_Factory(Provider<SessionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TerminalViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static TerminalViewModel_Factory create(Provider<SessionRepository> repositoryProvider) {
    return new TerminalViewModel_Factory(repositoryProvider);
  }

  public static TerminalViewModel newInstance(SessionRepository repository) {
    return new TerminalViewModel(repository);
  }
}

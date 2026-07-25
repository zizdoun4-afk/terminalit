package com.terminalit.service;

import com.terminalit.repository.SessionRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SshSessionService_MembersInjector implements MembersInjector<SshSessionService> {
  private final Provider<SessionRepository> sessionRepositoryProvider;

  public SshSessionService_MembersInjector(Provider<SessionRepository> sessionRepositoryProvider) {
    this.sessionRepositoryProvider = sessionRepositoryProvider;
  }

  public static MembersInjector<SshSessionService> create(
      Provider<SessionRepository> sessionRepositoryProvider) {
    return new SshSessionService_MembersInjector(sessionRepositoryProvider);
  }

  @Override
  public void injectMembers(SshSessionService instance) {
    injectSessionRepository(instance, sessionRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.terminalit.service.SshSessionService.sessionRepository")
  public static void injectSessionRepository(SshSessionService instance,
      SessionRepository sessionRepository) {
    instance.sessionRepository = sessionRepository;
  }
}

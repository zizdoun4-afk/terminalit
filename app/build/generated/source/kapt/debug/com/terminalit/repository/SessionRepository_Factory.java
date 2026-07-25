package com.terminalit.repository;

import com.terminalit.data.HostKeyStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class SessionRepository_Factory implements Factory<SessionRepository> {
  private final Provider<HostKeyStore> hostKeyStoreProvider;

  public SessionRepository_Factory(Provider<HostKeyStore> hostKeyStoreProvider) {
    this.hostKeyStoreProvider = hostKeyStoreProvider;
  }

  @Override
  public SessionRepository get() {
    return newInstance(hostKeyStoreProvider.get());
  }

  public static SessionRepository_Factory create(Provider<HostKeyStore> hostKeyStoreProvider) {
    return new SessionRepository_Factory(hostKeyStoreProvider);
  }

  public static SessionRepository newInstance(HostKeyStore hostKeyStore) {
    return new SessionRepository(hostKeyStore);
  }
}

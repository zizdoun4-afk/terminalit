package com.terminalit.viewmodel;

import android.app.Application;
import androidx.lifecycle.SavedStateHandle;
import com.terminalit.data.ProfileStore;
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
public final class ConnectionViewModel_Factory implements Factory<ConnectionViewModel> {
  private final Provider<SessionRepository> repositoryProvider;

  private final Provider<ProfileStore> profileStoreProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<Application> applicationProvider;

  public ConnectionViewModel_Factory(Provider<SessionRepository> repositoryProvider,
      Provider<ProfileStore> profileStoreProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<Application> applicationProvider) {
    this.repositoryProvider = repositoryProvider;
    this.profileStoreProvider = profileStoreProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public ConnectionViewModel get() {
    return newInstance(repositoryProvider.get(), profileStoreProvider.get(), savedStateHandleProvider.get(), applicationProvider.get());
  }

  public static ConnectionViewModel_Factory create(Provider<SessionRepository> repositoryProvider,
      Provider<ProfileStore> profileStoreProvider,
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<Application> applicationProvider) {
    return new ConnectionViewModel_Factory(repositoryProvider, profileStoreProvider, savedStateHandleProvider, applicationProvider);
  }

  public static ConnectionViewModel newInstance(SessionRepository repository,
      ProfileStore profileStore, SavedStateHandle savedStateHandle, Application application) {
    return new ConnectionViewModel(repository, profileStore, savedStateHandle, application);
  }
}

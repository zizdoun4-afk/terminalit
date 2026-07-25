package com.terminalit.viewmodel;

import com.terminalit.data.ProfileStore;
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
public final class ProfileListViewModel_Factory implements Factory<ProfileListViewModel> {
  private final Provider<ProfileStore> profileStoreProvider;

  public ProfileListViewModel_Factory(Provider<ProfileStore> profileStoreProvider) {
    this.profileStoreProvider = profileStoreProvider;
  }

  @Override
  public ProfileListViewModel get() {
    return newInstance(profileStoreProvider.get());
  }

  public static ProfileListViewModel_Factory create(Provider<ProfileStore> profileStoreProvider) {
    return new ProfileListViewModel_Factory(profileStoreProvider);
  }

  public static ProfileListViewModel newInstance(ProfileStore profileStore) {
    return new ProfileListViewModel(profileStore);
  }
}

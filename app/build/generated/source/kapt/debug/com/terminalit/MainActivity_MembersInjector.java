package com.terminalit;

import com.terminalit.data.ProfileStore;
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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<ProfileStore> profileStoreProvider;

  public MainActivity_MembersInjector(Provider<ProfileStore> profileStoreProvider) {
    this.profileStoreProvider = profileStoreProvider;
  }

  public static MembersInjector<MainActivity> create(Provider<ProfileStore> profileStoreProvider) {
    return new MainActivity_MembersInjector(profileStoreProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectProfileStore(instance, profileStoreProvider.get());
  }

  @InjectedFieldSignature("com.terminalit.MainActivity.profileStore")
  public static void injectProfileStore(MainActivity instance, ProfileStore profileStore) {
    instance.profileStore = profileStore;
  }
}

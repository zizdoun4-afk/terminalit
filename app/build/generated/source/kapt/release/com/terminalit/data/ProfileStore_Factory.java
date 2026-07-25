package com.terminalit.data;

import android.content.Context;
import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ProfileStore_Factory implements Factory<ProfileStore> {
  private final Provider<Context> contextProvider;

  private final Provider<Gson> gsonProvider;

  public ProfileStore_Factory(Provider<Context> contextProvider, Provider<Gson> gsonProvider) {
    this.contextProvider = contextProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public ProfileStore get() {
    return newInstance(contextProvider.get(), gsonProvider.get());
  }

  public static ProfileStore_Factory create(Provider<Context> contextProvider,
      Provider<Gson> gsonProvider) {
    return new ProfileStore_Factory(contextProvider, gsonProvider);
  }

  public static ProfileStore newInstance(Context context, Gson gson) {
    return new ProfileStore(context, gson);
  }
}

package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class LocalMapActivity_MembersInjector implements MembersInjector<LocalMapActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<DatabaseService> databaseServiceProvider;

  public LocalMapActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<DatabaseService> databaseServiceProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert databaseServiceProvider != null;
    this.databaseServiceProvider = databaseServiceProvider;
  }

  @Override
  public void injectMembers(LocalMapActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.databaseService = databaseServiceProvider.get();
  }

  public static MembersInjector<LocalMapActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<DatabaseService> databaseServiceProvider) {  
      return new LocalMapActivity_MembersInjector(supertypeInjector, databaseServiceProvider);
  }
}


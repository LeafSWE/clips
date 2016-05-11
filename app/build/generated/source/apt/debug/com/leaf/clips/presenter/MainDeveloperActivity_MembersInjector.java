package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MainDeveloperActivity_MembersInjector implements MembersInjector<MainDeveloperActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> infoManagerProvider;

  public MainDeveloperActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert infoManagerProvider != null;
    this.infoManagerProvider = infoManagerProvider;
  }

  @Override
  public void injectMembers(MainDeveloperActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.infoManager = infoManagerProvider.get();
  }

  public static MembersInjector<MainDeveloperActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
      return new MainDeveloperActivity_MembersInjector(supertypeInjector, infoManagerProvider);
  }
}


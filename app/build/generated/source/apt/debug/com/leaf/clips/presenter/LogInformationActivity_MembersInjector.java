package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class LogInformationActivity_MembersInjector implements MembersInjector<LogInformationActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> infoManagerProvider;

  public LogInformationActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert infoManagerProvider != null;
    this.infoManagerProvider = infoManagerProvider;
  }

  @Override
  public void injectMembers(LogInformationActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.infoManager = infoManagerProvider.get();
  }

  public static MembersInjector<LogInformationActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
      return new LogInformationActivity_MembersInjector(supertypeInjector, infoManagerProvider);
  }
}


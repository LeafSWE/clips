package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DetailedInformationActivity_MembersInjector implements MembersInjector<DetailedInformationActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;
  private final Provider<NavigationManager> navigationManagerProvider;

  public DetailedInformationActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider, Provider<NavigationManager> navigationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
    assert navigationManagerProvider != null;
    this.navigationManagerProvider = navigationManagerProvider;
  }

  @Override
  public void injectMembers(DetailedInformationActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
    instance.navigationManager = navigationManagerProvider.get();
  }

  public static MembersInjector<DetailedInformationActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider, Provider<NavigationManager> navigationManagerProvider) {  
      return new DetailedInformationActivity_MembersInjector(supertypeInjector, informationManagerProvider, navigationManagerProvider);
  }
}


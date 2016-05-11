package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class HomeActivity_MembersInjector implements MembersInjector<HomeActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;

  public HomeActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public void injectMembers(HomeActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
  }

  public static MembersInjector<HomeActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
      return new HomeActivity_MembersInjector(supertypeInjector, informationManagerProvider);
  }
}


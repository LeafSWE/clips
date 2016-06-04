package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PoiActivity_MembersInjector implements MembersInjector<PoiActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;

  public PoiActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public void injectMembers(PoiActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
  }

  public static MembersInjector<PoiActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
      return new PoiActivity_MembersInjector(supertypeInjector, informationManagerProvider);
  }
}


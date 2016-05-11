package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class NearbyPoiActivity_MembersInjector implements MembersInjector<NearbyPoiActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;

  public NearbyPoiActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public void injectMembers(NearbyPoiActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
  }

  public static MembersInjector<NearbyPoiActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
      return new NearbyPoiActivity_MembersInjector(supertypeInjector, informationManagerProvider);
  }
}


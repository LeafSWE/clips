package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class BeaconPowerAreaActivity_MembersInjector implements MembersInjector<BeaconPowerAreaActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> infoManagerProvider;

  public BeaconPowerAreaActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert infoManagerProvider != null;
    this.infoManagerProvider = infoManagerProvider;
  }

  @Override
  public void injectMembers(BeaconPowerAreaActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.infoManager = infoManagerProvider.get();
  }

  public static MembersInjector<BeaconPowerAreaActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> infoManagerProvider) {  
      return new BeaconPowerAreaActivity_MembersInjector(supertypeInjector, infoManagerProvider);
  }
}


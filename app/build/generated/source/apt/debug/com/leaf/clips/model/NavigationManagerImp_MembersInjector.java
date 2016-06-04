package com.leaf.clips.model;

import com.leaf.clips.model.compass.Compass;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class NavigationManagerImp_MembersInjector implements MembersInjector<NavigationManagerImp> {
  private final MembersInjector<AbsBeaconReceiverManager> supertypeInjector;
  private final Provider<Compass> compassProvider;

  public NavigationManagerImp_MembersInjector(MembersInjector<AbsBeaconReceiverManager> supertypeInjector, Provider<Compass> compassProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert compassProvider != null;
    this.compassProvider = compassProvider;
  }

  @Override
  public void injectMembers(NavigationManagerImp instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.compass = compassProvider.get();
  }

  public static MembersInjector<NavigationManagerImp> create(MembersInjector<AbsBeaconReceiverManager> supertypeInjector, Provider<Compass> compassProvider) {  
      return new NavigationManagerImp_MembersInjector(supertypeInjector, compassProvider);
  }
}


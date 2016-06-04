package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PoiDescriptionActivity_MembersInjector implements MembersInjector<PoiDescriptionActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;

  public PoiDescriptionActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public void injectMembers(PoiDescriptionActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
  }

  public static MembersInjector<PoiDescriptionActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
      return new PoiDescriptionActivity_MembersInjector(supertypeInjector, informationManagerProvider);
  }
}


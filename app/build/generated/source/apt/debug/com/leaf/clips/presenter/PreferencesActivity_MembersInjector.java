package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.usersetting.Setting;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PreferencesActivity_MembersInjector implements MembersInjector<PreferencesActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<Setting> settingProvider;

  public PreferencesActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> settingProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert settingProvider != null;
    this.settingProvider = settingProvider;
  }

  @Override
  public void injectMembers(PreferencesActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.setting = settingProvider.get();
  }

  public static MembersInjector<PreferencesActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> settingProvider) {  
      return new PreferencesActivity_MembersInjector(supertypeInjector, settingProvider);
  }
}


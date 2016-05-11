package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.usersetting.Setting;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DeveloperUnlockerActivity_MembersInjector implements MembersInjector<DeveloperUnlockerActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<Setting> userSettingProvider;

  public DeveloperUnlockerActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> userSettingProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert userSettingProvider != null;
    this.userSettingProvider = userSettingProvider;
  }

  @Override
  public void injectMembers(DeveloperUnlockerActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.userSetting = userSettingProvider.get();
  }

  public static MembersInjector<DeveloperUnlockerActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> userSettingProvider) {  
      return new DeveloperUnlockerActivity_MembersInjector(supertypeInjector, userSettingProvider);
  }
}


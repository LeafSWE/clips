package com.leaf.clips.presenter;

import android.support.v7.app.AppCompatActivity;
import com.leaf.clips.model.usersetting.Setting;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MainDeveloperPresenter_MembersInjector implements MembersInjector<MainDeveloperPresenter> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<Setting> userSettingProvider;

  public MainDeveloperPresenter_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> userSettingProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert userSettingProvider != null;
    this.userSettingProvider = userSettingProvider;
  }

  @Override
  public void injectMembers(MainDeveloperPresenter instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.userSetting = userSettingProvider.get();
  }

  public static MembersInjector<MainDeveloperPresenter> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<Setting> userSettingProvider) {  
      return new MainDeveloperPresenter_MembersInjector(supertypeInjector, userSettingProvider);
  }
}


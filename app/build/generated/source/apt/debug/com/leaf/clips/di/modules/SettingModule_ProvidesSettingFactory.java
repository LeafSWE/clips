package com.leaf.clips.di.modules;

import com.leaf.clips.model.usersetting.Setting;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class SettingModule_ProvidesSettingFactory implements Factory<Setting> {
  private final SettingModule module;

  public SettingModule_ProvidesSettingFactory(SettingModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Setting get() {  
    Setting provided = module.providesSetting();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Setting> create(SettingModule module) {  
    return new SettingModule_ProvidesSettingFactory(module);
  }
}


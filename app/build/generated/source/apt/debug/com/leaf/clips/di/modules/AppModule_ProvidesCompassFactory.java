package com.leaf.clips.di.modules;

import com.leaf.clips.model.compass.Compass;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvidesCompassFactory implements Factory<Compass> {
  private final AppModule module;

  public AppModule_ProvidesCompassFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Compass get() {  
    Compass provided = module.providesCompass();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Compass> create(AppModule module) {  
    return new AppModule_ProvidesCompassFactory(module);
  }
}


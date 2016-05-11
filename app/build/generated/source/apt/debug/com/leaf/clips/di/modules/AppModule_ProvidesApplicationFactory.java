package com.leaf.clips.di.modules;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvidesApplicationFactory implements Factory<Application> {
  private final AppModule module;

  public AppModule_ProvidesApplicationFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Application get() {  
    Application provided = module.providesApplication();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Application> create(AppModule module) {  
    return new AppModule_ProvidesApplicationFactory(module);
  }
}


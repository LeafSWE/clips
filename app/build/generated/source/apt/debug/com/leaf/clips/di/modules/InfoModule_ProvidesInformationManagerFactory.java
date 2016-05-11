package com.leaf.clips.di.modules;

import android.content.Context;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class InfoModule_ProvidesInformationManagerFactory implements Factory<InformationManager> {
  private final InfoModule module;
  private final Provider<DatabaseService> serviceProvider;
  private final Provider<Context> contextProvider;

  public InfoModule_ProvidesInformationManagerFactory(InfoModule module, Provider<DatabaseService> serviceProvider, Provider<Context> contextProvider) {  
    assert module != null;
    this.module = module;
    assert serviceProvider != null;
    this.serviceProvider = serviceProvider;
    assert contextProvider != null;
    this.contextProvider = contextProvider;
  }

  @Override
  public InformationManager get() {  
    InformationManager provided = module.providesInformationManager(serviceProvider.get(), contextProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<InformationManager> create(InfoModule module, Provider<DatabaseService> serviceProvider, Provider<Context> contextProvider) {  
    return new InfoModule_ProvidesInformationManagerFactory(module, serviceProvider, contextProvider);
  }
}


package com.leaf.clips.di.modules;

import android.content.Context;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.graph.MapGraph;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class NavModule_ProvidesNavigationManagerFactory implements Factory<NavigationManager> {
  private final NavModule module;
  private final Provider<MapGraph> mapGraphProvider;
  private final Provider<Context> contextProvider;

  public NavModule_ProvidesNavigationManagerFactory(NavModule module, Provider<MapGraph> mapGraphProvider, Provider<Context> contextProvider) {  
    assert module != null;
    this.module = module;
    assert mapGraphProvider != null;
    this.mapGraphProvider = mapGraphProvider;
    assert contextProvider != null;
    this.contextProvider = contextProvider;
  }

  @Override
  public NavigationManager get() {  
    NavigationManager provided = module.providesNavigationManager(mapGraphProvider.get(), contextProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<NavigationManager> create(NavModule module, Provider<MapGraph> mapGraphProvider, Provider<Context> contextProvider) {  
    return new NavModule_ProvidesNavigationManagerFactory(module, mapGraphProvider, contextProvider);
  }
}


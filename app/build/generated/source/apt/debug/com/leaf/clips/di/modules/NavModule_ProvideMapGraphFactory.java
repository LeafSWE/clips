package com.leaf.clips.di.modules;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.navigator.graph.MapGraph;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class NavModule_ProvideMapGraphFactory implements Factory<MapGraph> {
  private final NavModule module;
  private final Provider<InformationManager> informationManagerProvider;

  public NavModule_ProvideMapGraphFactory(NavModule module, Provider<InformationManager> informationManagerProvider) {  
    assert module != null;
    this.module = module;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public MapGraph get() {  
    MapGraph provided = module.provideMapGraph(informationManagerProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<MapGraph> create(NavModule module, Provider<InformationManager> informationManagerProvider) {  
    return new NavModule_ProvideMapGraphFactory(module, informationManagerProvider);
  }
}


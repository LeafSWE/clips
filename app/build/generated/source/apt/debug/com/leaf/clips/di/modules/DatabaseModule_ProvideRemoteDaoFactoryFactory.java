package com.leaf.clips.di.modules;

import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DatabaseModule_ProvideRemoteDaoFactoryFactory implements Factory<RemoteDaoFactory> {
  private final DatabaseModule module;

  public DatabaseModule_ProvideRemoteDaoFactoryFactory(DatabaseModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public RemoteDaoFactory get() {  
    RemoteDaoFactory provided = module.provideRemoteDaoFactory();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<RemoteDaoFactory> create(DatabaseModule module) {  
    return new DatabaseModule_ProvideRemoteDaoFactoryFactory(module);
  }
}


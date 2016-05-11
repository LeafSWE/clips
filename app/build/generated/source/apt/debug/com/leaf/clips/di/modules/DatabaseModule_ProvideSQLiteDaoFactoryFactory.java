package com.leaf.clips.di.modules;

import android.content.Context;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DatabaseModule_ProvideSQLiteDaoFactoryFactory implements Factory<SQLiteDaoFactory> {
  private final DatabaseModule module;
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideSQLiteDaoFactoryFactory(DatabaseModule module, Provider<Context> contextProvider) {  
    assert module != null;
    this.module = module;
    assert contextProvider != null;
    this.contextProvider = contextProvider;
  }

  @Override
  public SQLiteDaoFactory get() {  
    SQLiteDaoFactory provided = module.provideSQLiteDaoFactory(contextProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<SQLiteDaoFactory> create(DatabaseModule module, Provider<Context> contextProvider) {  
    return new DatabaseModule_ProvideSQLiteDaoFactoryFactory(module, contextProvider);
  }
}


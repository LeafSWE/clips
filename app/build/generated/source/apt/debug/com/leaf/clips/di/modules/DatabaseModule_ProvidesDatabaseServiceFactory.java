package com.leaf.clips.di.modules;

import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DatabaseModule_ProvidesDatabaseServiceFactory implements Factory<DatabaseService> {
  private final DatabaseModule module;
  private final Provider<SQLiteDaoFactory> sqLiteDaoFactoryProvider;
  private final Provider<RemoteDaoFactory> remoteDaoFactoryProvider;

  public DatabaseModule_ProvidesDatabaseServiceFactory(DatabaseModule module, Provider<SQLiteDaoFactory> sqLiteDaoFactoryProvider, Provider<RemoteDaoFactory> remoteDaoFactoryProvider) {  
    assert module != null;
    this.module = module;
    assert sqLiteDaoFactoryProvider != null;
    this.sqLiteDaoFactoryProvider = sqLiteDaoFactoryProvider;
    assert remoteDaoFactoryProvider != null;
    this.remoteDaoFactoryProvider = remoteDaoFactoryProvider;
  }

  @Override
  public DatabaseService get() {  
    DatabaseService provided = module.providesDatabaseService(sqLiteDaoFactoryProvider.get(), remoteDaoFactoryProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DatabaseService> create(DatabaseModule module, Provider<SQLiteDaoFactory> sqLiteDaoFactoryProvider, Provider<RemoteDaoFactory> remoteDaoFactoryProvider) {  
    return new DatabaseModule_ProvidesDatabaseServiceFactory(module, sqLiteDaoFactoryProvider, remoteDaoFactoryProvider);
  }
}


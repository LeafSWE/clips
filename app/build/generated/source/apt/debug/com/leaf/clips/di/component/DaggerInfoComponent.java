package com.leaf.clips.di.component;

import android.content.Context;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.modules.AppModule_ProvidesContextFactory;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.DatabaseModule_ProvideRemoteDaoFactoryFactory;
import com.leaf.clips.di.modules.DatabaseModule_ProvideSQLiteDaoFactoryFactory;
import com.leaf.clips.di.modules.DatabaseModule_ProvidesDatabaseServiceFactory;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.InfoModule_ProvidesInformationManagerFactory;
import com.leaf.clips.di.modules.SettingModule;
import com.leaf.clips.di.modules.SettingModule_ProvidesSettingFactory;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;
import com.leaf.clips.presenter.DeveloperUnlockerActivity_MembersInjector;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.HomeActivity_MembersInjector;
import com.leaf.clips.presenter.LogInformationActivity;
import com.leaf.clips.presenter.LogInformationActivity_MembersInjector;
import com.leaf.clips.presenter.MainDeveloperActivity;
import com.leaf.clips.presenter.MainDeveloperActivity_MembersInjector;
import com.leaf.clips.presenter.MainDeveloperPresenter;
import com.leaf.clips.presenter.MainDeveloperPresenter_MembersInjector;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NavigationActivity_MembersInjector;
import com.leaf.clips.presenter.NearbyPoiActivity;
import com.leaf.clips.presenter.NearbyPoiActivity_MembersInjector;
import com.leaf.clips.presenter.PoiCategoryActivity;
import com.leaf.clips.presenter.PoiCategoryActivity_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerInfoComponent implements InfoComponent {
  private Provider<Context> providesContextProvider;
  private Provider<SQLiteDaoFactory> provideSQLiteDaoFactoryProvider;
  private Provider<RemoteDaoFactory> provideRemoteDaoFactoryProvider;
  private Provider<DatabaseService> providesDatabaseServiceProvider;
  private Provider<InformationManager> providesInformationManagerProvider;
  private MembersInjector<HomeActivity> homeActivityMembersInjector;
  private Provider<Setting> providesSettingProvider;
  private MembersInjector<DeveloperUnlockerActivity> developerUnlockerActivityMembersInjector;
  private MembersInjector<PoiCategoryActivity> poiCategoryActivityMembersInjector;
  private MembersInjector<NearbyPoiActivity> nearbyPoiActivityMembersInjector;
  private MembersInjector<NavigationActivity> navigationActivityMembersInjector;
  private MembersInjector<MainDeveloperPresenter> mainDeveloperPresenterMembersInjector;
  private MembersInjector<MainDeveloperActivity> mainDeveloperActivityMembersInjector;
  private MembersInjector<LogInformationActivity> logInformationActivityMembersInjector;

  private DaggerInfoComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.providesContextProvider = ScopedProvider.create(AppModule_ProvidesContextFactory.create(builder.appModule));
    this.provideSQLiteDaoFactoryProvider = ScopedProvider.create(DatabaseModule_ProvideSQLiteDaoFactoryFactory.create(builder.databaseModule, providesContextProvider));
    this.provideRemoteDaoFactoryProvider = ScopedProvider.create(DatabaseModule_ProvideRemoteDaoFactoryFactory.create(builder.databaseModule));
    this.providesDatabaseServiceProvider = ScopedProvider.create(DatabaseModule_ProvidesDatabaseServiceFactory.create(builder.databaseModule, provideSQLiteDaoFactoryProvider, provideRemoteDaoFactoryProvider));
    this.providesInformationManagerProvider = ScopedProvider.create(InfoModule_ProvidesInformationManagerFactory.create(builder.infoModule, providesDatabaseServiceProvider, providesContextProvider));
    this.homeActivityMembersInjector = HomeActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
    this.providesSettingProvider = ScopedProvider.create(SettingModule_ProvidesSettingFactory.create(builder.settingModule));
    this.developerUnlockerActivityMembersInjector = DeveloperUnlockerActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesSettingProvider);
    this.poiCategoryActivityMembersInjector = PoiCategoryActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
    this.nearbyPoiActivityMembersInjector = NearbyPoiActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
    this.navigationActivityMembersInjector = NavigationActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
    this.mainDeveloperPresenterMembersInjector = MainDeveloperPresenter_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesSettingProvider);
    this.mainDeveloperActivityMembersInjector = MainDeveloperActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
    this.logInformationActivityMembersInjector = LogInformationActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providesInformationManagerProvider);
  }

  @Override
  public void inject(HomeActivity mainActivity) {  
    homeActivityMembersInjector.injectMembers(mainActivity);
  }

  @Override
  public void inject(DeveloperUnlockerActivity developerUnlockerActivity) {  
    developerUnlockerActivityMembersInjector.injectMembers(developerUnlockerActivity);
  }

  @Override
  public void inject(PoiCategoryActivity poiCategoryActivity) {  
    poiCategoryActivityMembersInjector.injectMembers(poiCategoryActivity);
  }

  @Override
  public void inject(NearbyPoiActivity nearbyPoiActivity) {  
    nearbyPoiActivityMembersInjector.injectMembers(nearbyPoiActivity);
  }

  @Override
  public void inject(NavigationActivity navigationActivity) {  
    navigationActivityMembersInjector.injectMembers(navigationActivity);
  }

  @Override
  public void inject(MainDeveloperPresenter mainDeveloperPresenter) {  
    mainDeveloperPresenterMembersInjector.injectMembers(mainDeveloperPresenter);
  }

  @Override
  public void inject(MainDeveloperActivity mainDeveloperActivity) {  
    mainDeveloperActivityMembersInjector.injectMembers(mainDeveloperActivity);
  }

  @Override
  public void inject(LogInformationActivity logInformationActivity) {  
    logInformationActivityMembersInjector.injectMembers(logInformationActivity);
  }

  public static final class Builder {
    private AppModule appModule;
    private InfoModule infoModule;
    private DatabaseModule databaseModule;
    private SettingModule settingModule;
  
    private Builder() {  
    }
  
    public InfoComponent build() {  
      if (appModule == null) {
        throw new IllegalStateException("appModule must be set");
      }
      if (infoModule == null) {
        this.infoModule = new InfoModule();
      }
      if (databaseModule == null) {
        throw new IllegalStateException("databaseModule must be set");
      }
      if (settingModule == null) {
        throw new IllegalStateException("settingModule must be set");
      }
      return new DaggerInfoComponent(this);
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  
    public Builder infoModule(InfoModule infoModule) {  
      if (infoModule == null) {
        throw new NullPointerException("infoModule");
      }
      this.infoModule = infoModule;
      return this;
    }
  
    public Builder databaseModule(DatabaseModule databaseModule) {  
      if (databaseModule == null) {
        throw new NullPointerException("databaseModule");
      }
      this.databaseModule = databaseModule;
      return this;
    }
  
    public Builder settingModule(SettingModule settingModule) {  
      if (settingModule == null) {
        throw new NullPointerException("settingModule");
      }
      this.settingModule = settingModule;
      return this;
    }
  }
}


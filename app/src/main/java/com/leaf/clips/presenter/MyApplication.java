package com.leaf.clips.presenter;

import android.app.Application;

import com.leaf.clips.di.component.DaggerDatabaseComponent;
import com.leaf.clips.di.component.DaggerInfoComponent;
import com.leaf.clips.di.component.DatabaseComponent;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.component.InfoComponent;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.model.dataaccess.dao.RemoteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.DatabaseServiceImp;
import com.leaf.clips.model.dataaccess.service.EdgeService;
import com.leaf.clips.model.dataaccess.service.RegionOfInterestService;

import javax.inject.Inject;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class MyApplication extends Application {

    private InfoComponent infoComponent;
    DatabaseService service;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseModule databaseModule = new DatabaseModule(this);
        service = databaseModule.providesDatabaseService();
        infoComponent = DaggerInfoComponent.builder().appModule(new AppModule(this)).
                infoModule(new InfoModule(service, this)).databaseModule(databaseModule).build();
    }

    public InfoComponent getInfoComponent(){
        return infoComponent;
    }
}

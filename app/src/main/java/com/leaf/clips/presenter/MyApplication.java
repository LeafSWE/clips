package com.leaf.clips.presenter;

import android.app.Application;

import com.leaf.clips.di.component.DaggerInfoComponent;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.component.InfoComponent;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.SettingModule;
import com.leaf.clips.model.dataaccess.service.DatabaseService;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class MyApplication extends Application {

    private InfoComponent infoComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseModule databaseModule = new DatabaseModule(this);
        DatabaseService service = databaseModule.providesDatabaseService();
        infoComponent = DaggerInfoComponent.builder().appModule(new AppModule(this)).
            infoModule(new InfoModule(service, this)).settingModule(new SettingModule(this)).
            databaseModule(databaseModule).build();
    }

    public InfoComponent getInfoComponent(){
        return infoComponent;
    }
}

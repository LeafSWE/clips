package com.leaf.clips.di.component;

import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.modules.SettingModule;
import com.leaf.clips.presenter.HomeActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@Singleton
@Component(modules = {AppModule.class, InfoModule.class, DatabaseModule.class, SettingModule.class})
public interface InfoComponent {
    void inject(HomeActivity mainActivity);
}

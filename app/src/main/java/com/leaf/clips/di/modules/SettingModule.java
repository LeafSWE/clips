package com.leaf.clips.di.modules;

import android.content.Context;

import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.model.usersetting.SettingImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
@Module
public class SettingModule {
    Context context;

    public SettingModule (Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public Setting providesSetting(){
        return new SettingImp(context);
    }
}

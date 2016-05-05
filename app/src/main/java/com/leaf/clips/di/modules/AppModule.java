package com.leaf.clips.di.modules;

import android.app.Application;

import com.leaf.clips.presenter.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

@Module
public class AppModule {
    MyApplication mApplication;

    public AppModule(MyApplication application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application providesApplication() {
        return mApplication;
    }
}

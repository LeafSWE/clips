package com.leaf.clips.di.modules;

import android.app.Application;
import android.content.Context;

import com.leaf.clips.presenter.MySingleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Black on 06/05/2016.
 */
@Module
public class SingleModule {

        MySingleton mApplication;

        public SingleModule(MySingleton application) {
            mApplication = application;
        }

        @Provides
        @Singleton
        public MySingleton providesApplication() {
            return mApplication;
        }
}


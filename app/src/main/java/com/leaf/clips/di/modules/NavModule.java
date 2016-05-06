package com.leaf.clips.di.modules;

import android.content.Context;

import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NavigationManagerImp;
import com.leaf.clips.model.navigator.graph.MapGraph;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Black on 06/05/2016.
 */
@Module
public class NavModule {

    @Provides
    @Singleton
    public MapGraph provideMapGraph(){
        return new MapGraph();
    }

    @Provides
    @Singleton
    public NavigationManager providesNavigationManager(MapGraph mapGraph,Context context){
        return new NavigationManagerImp(mapGraph,context);
    }

}

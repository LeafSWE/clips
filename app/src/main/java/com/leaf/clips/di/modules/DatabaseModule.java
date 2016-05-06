package com.leaf.clips.di.modules;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.clips.model.dataaccess.dao.MapsDbHelper;
import com.leaf.clips.model.dataaccess.dao.RemoteDaoFactory;
import com.leaf.clips.model.dataaccess.dao.SQLiteDaoFactory;
import com.leaf.clips.model.dataaccess.service.DatabaseService;
import com.leaf.clips.model.dataaccess.service.ServiceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.01
 */
@Module
public class DatabaseModule {

    String s;

    public DatabaseModule(String s){
        this.s=s;
    }

    @Provides
    @Singleton
    public SQLiteDaoFactory provideSQLiteDaoFactory(Context context){
        SQLiteOpenHelper sqLiteOpenHelper = new MapsDbHelper(context);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        return new SQLiteDaoFactory(db);
    }

    @Provides
    @Singleton
    public RemoteDaoFactory provideRemoteDaoFactory(){
        return new RemoteDaoFactory();
    }

    @Provides
    @Singleton
    public DatabaseService providesDatabaseService(SQLiteDaoFactory sqLiteDaoFactory,RemoteDaoFactory remoteDaoFactory){
        return ServiceHelper.getService(sqLiteDaoFactory,remoteDaoFactory,s);
    }
}

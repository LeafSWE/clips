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

/**
 * Classe nella quale vengono dichiarate le dipendenze verso oggetti di tipo SQliteDaoFactory,
 * RemoteDaoFactory e DatabaseService
 */
@Module
public class DatabaseModule {

    /**
     * Stringa che rappresenta l'URL del database remoto
     */
    private final String remoteURL;

    /**
     * Costruttore della classe DatabaseModule
     * @param remoteURL Stringa che rappresenta l'URL del database remoto
     */
    public DatabaseModule(String remoteURL) {
        this.remoteURL = remoteURL;
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo SQLiteDaoFactory. L'istanza ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @param context Contesto di esecuzione dell'applicazione
     * @return SQLiteDaoFactory
     */
    @Provides
    @Singleton
    public SQLiteDaoFactory provideSQLiteDaoFactory(Context context){
        SQLiteOpenHelper sqLiteOpenHelper = new MapsDbHelper(context);
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        return new SQLiteDaoFactory(db);
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo RemoteDaoFactory. L'istanza ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @return RemoteDaoFactory
     */
    @Provides
    @Singleton
    public RemoteDaoFactory provideRemoteDaoFactory(){
        return new RemoteDaoFactory();
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo DatabaseService ritornando un oggetto di tipo BuildingServiceImp. L'istanza ritornata
     * sarà sempre la stessa utilizzando lo stesso modulo.
     * @param sqLiteDaoFactory Factory che permette di costruire oggetti con cui accedere ai dati
     *                         salvati nel databse locale
     * @param remoteDaoFactory Factory che permette di costruire oggetti scaricati dal database
     *                         remoto
     * @return DatabaseService
     */
    @Provides
    @Singleton
    public DatabaseService providesDatabaseService(SQLiteDaoFactory sqLiteDaoFactory,
                                                   RemoteDaoFactory remoteDaoFactory) {
        return ServiceHelper.getService(sqLiteDaoFactory, remoteDaoFactory, remoteURL);
    }
}

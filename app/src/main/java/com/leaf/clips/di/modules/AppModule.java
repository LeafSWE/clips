package com.leaf.clips.di.modules;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;

import com.leaf.clips.model.compass.Compass;
import com.leaf.clips.presenter.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

// TODO: 25/05/16 aggiornare tracy/uml
/**
 * Classe nella quale vengono dichiarate le dipendenze verso oggetti di tipo Context e Application
 */
@Module
public class AppModule {

    /**
     * Applicazione in esecuzione nella quale ci sono oggetti in cui fare la dependency injection
     */
    private final MyApplication mApplication;

    /**
     * Riferimento ad un oggetto Compass per la lettura dei dati della bussola
     */
    private final Compass compass;

    /**
     * Costruttore della classe AppModule
     * @param application Applicazione in esecuzione nella quale ci sono oggetti in cui fare la
     *                    dependency injection
     */
    public AppModule(MyApplication application) {
        mApplication = application;
        compass = new Compass((SensorManager) application.getSystemService(Context.SENSOR_SERVICE));
        compass.registerListener();
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo Context. L'istanza ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @return Context
     */
    @Provides
    @Singleton
    public Context providesContext() {
        return mApplication;
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo Application. L'istanza ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @return Application
     */
    @Provides
    @Singleton
    public Application providesApplication() {
        return mApplication;
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo Compass. L'istanza ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @return Compass
     */
    @Provides @Singleton
    public Compass providesCompass(){
        return compass;
    }
}

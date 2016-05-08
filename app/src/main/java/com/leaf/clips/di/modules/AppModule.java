package com.leaf.clips.di.modules;

import android.app.Application;
import android.content.Context;

import com.leaf.clips.presenter.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

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
     * Costruttore della classe AppModule
     * @param application Applicazione in esecuzione nella quale ci sono oggetti in cui fare la
     *                    dependency injection
     */
    public AppModule(MyApplication application) {
        mApplication = application;
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
}

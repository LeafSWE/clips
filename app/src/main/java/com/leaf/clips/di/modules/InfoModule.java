package com.leaf.clips.di.modules;

import android.content.Context;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.InformationManagerImp;
import com.leaf.clips.model.dataaccess.service.DatabaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Classe nella quale vengono dichiarate le dipendenze verso oggetti di tipo InformationManager
 */
@Module
public class InfoModule {

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo InformationManager ritornando un oggetto di tipo InformationManagerImp. L'istanza
     * ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @param service Oggetto che permette di accedere al database sia locale che remoto
     * @param context Contesto di esecuzione dell'applciazione
     * @return InformationManager
     */
    @Provides @Singleton
    public InformationManager providesInformationManager(DatabaseService service,Context context){
        return new InformationManagerImp(service, context);
    }
}

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

/**
 * Classe nella quale vengono dichiarate le dipendenze verso oggetti di tipo Setting
 */
@Module
public class SettingModule {

    /**
     * Contesto di esecuzione dell'applicazione
     */
    private final Context context;


    /**
     * Costruttore della classe SettingModule
     * @param context Contesto di esecuzione dell'applicazione
     */
    public SettingModule (Context context){
        this.context = context;
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo Setting ritornando un oggetto di tipo SettingImp. L'istanza ritornata sarà sempre la
     * stessa utilizzando lo stesso modulo.
     * @return Setting
     */
    @Provides
    @Singleton
    public Setting providesSetting(){
        return new SettingImp(context);
    }
}

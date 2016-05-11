package com.leaf.clips.presenter;

import android.app.Application;

import com.leaf.clips.di.component.DaggerInfoComponent;
import com.leaf.clips.di.component.InfoComponent;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.SettingModule;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Classe derivata da Application. Essa permette di ridefinire il comportamento dell'applicazione
 * al momento della creazione tramite il metodo onCreatej
 */
public class MyApplication extends Application {

    /*
    * Riferimento all'oggetto di tipo InfoComponent che permette di risolvere le dipendenze tra i
    * tipi presenti nell'applicazione
    */
    private InfoComponent infoComponent;

    /**
     * 	Metodo di callback che permette di ridefinire il comportamento dell'applicazione al momento
     * 	della creazione. Il metodo viene ridefinito per poter gestire la creazione dell'oggetto
     * 	di tipo InfoComponent utile alla gestione della dependency injection
     */
    @Override
    public void onCreate() {
        super.onCreate();
        /*ATTENZIONE: se non viene trovato dal compilatore DaggerInfoComponent:
         *  >Menu Build
         *  >Rebuild Project
         */
        infoComponent = DaggerInfoComponent.builder().appModule(new AppModule(this)).
            infoModule(new InfoModule()).
            databaseModule(new DatabaseModule("http://52.49.217.118/")).settingModule(new SettingModule(this)).build();
    }

    /**
     * Metodo getter che permette di recuperare il riferimento a infoComponent. Questo metodo è
     * utile per poter eseguire il metodo inject nella classi richieste
     * @return InfoComponent
     */
    public InfoComponent getInfoComponent(){
        return infoComponent;
    }
}

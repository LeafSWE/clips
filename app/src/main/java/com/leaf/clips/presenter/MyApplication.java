package com.leaf.clips.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.app.Application;
import android.os.Environment;

import com.leaf.clips.di.component.DaggerInfoComponent;
import com.leaf.clips.di.component.InfoComponent;
import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.SettingModule;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Classe derivata da Application. Essa permette di ridefinire il comportamento dell'applicazione
 * al momento della creazione tramite il metodo onCreate
 */
public class MyApplication extends Application {

    /**
    * Riferimento all'oggetto di tipo InfoComponent che permette di risolvere le dipendenze tra i
    * tipi presenti nell'applicazione
    */
    private static InfoComponent infoComponent;

    /**
     * 	Metodo di callback che permette di ridefinire il comportamento dell'applicazione al momento
     * 	della creazione. Il metodo viene ridefinito per poter gestire la creazione dell'oggetto
     * 	di tipo InfoComponent utile alla gestione della dependency injection
     */
    @Override
    public void onCreate() {
        super.onCreate();

        if(configuration == null){
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = null;
            try {
                jsonElement = parser.parse(new InputStreamReader(getAssets().open("configuration_file.json")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            JsonObject jsonObject = null;
            if (jsonElement != null) {
                jsonObject = jsonElement.getAsJsonObject();
            }
            configuration = new Gson().fromJson(jsonObject, Configuration.class);
        }

        /*ATTENZIONE: se non viene trovato dal compilatore DaggerInfoComponent:
         *  >Menu Build
         *  >Rebuild Project
         */
        infoComponent = DaggerInfoComponent.builder().appModule(new AppModule(this)).
            infoModule(new InfoModule()).
            databaseModule(new DatabaseModule(getConfiguration().getRemoteDBPath()))
                .settingModule(new SettingModule(this)).build();

    }

    /**
     * Metodo getter che permette di recuperare il riferimento a infoComponent. Questo metodo è
     * utile per poter eseguire il metodo inject nella classi richieste
     * @return InfoComponent
     */
    public static InfoComponent getInfoComponent(){
        return infoComponent;
    }

    private static Configuration configuration = null;

    public static class Configuration {
        private String remoteDBPath;
        private String remoteDBMapRequest;
        private String beaconLayout;
        private String applicationUUID;
        private String logsDirectory;
        private double elevatorFactor;
        private double stairFactor;

        public String getRemoteDBMapRequest(int major) {
            return remoteDBPath + remoteDBMapRequest + major;
        }

        public String getBeaconLayout() {
            return beaconLayout;
        }

        public String getApplicationUUID() {
            return applicationUUID;
        }

        public String getLogsDirectory() {
            return Environment.getExternalStorageDirectory().toString() + logsDirectory;
        }

        public double getElevatorFactor() {
            return elevatorFactor;
        }

        public double getStairFactor() {
            return stairFactor;
        }

        public String getRemoteDBPath() {
            return remoteDBPath;
        }

        private Configuration() {}

    }



    public static Configuration getConfiguration() {
        return configuration;
    }
}

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

    /**
     * Riferimento alle configurazioni dell'applicazione
     */
    private static Configuration configuration = null;

    /**
     * Classe che contiene le configurazioni dell'applciazione
     */
    public static class Configuration {

        /**
         * URL del database remoto
         */
        private String remoteDBPath;

        /**
         * URL per fare una richiesta di download di una mappa
         */
        private String remoteDBMapRequest;

        /**
         * Stringa che rappresenta il formato dei dati trasportati dal segnale dei beacon
         */
        private String beaconLayout;

        /**
         * UUID dei beacon riconosciuti dall'applicazione
         */
        private String applicationUUID;

        /**
         * Cartella in cui vengono salvati i Log dell'applicazione
         */
        private String logsDirectory;

        /**
         * Costante per il calcolo del peso degli archi che rappresentano ascensori
         */
        private double elevatorFactor;

        /**
         * Costante per il calcolo del peso degli archi che rappresentano scale
         */
        private double stairFactor;

        /**
         * Metodo che restituisce l'URL a cui recuperare la mappa dell'edificio associata ad un certo major
         * @param major Major che identifica l'edificio di cui scaricare la mappa
         * @return String
         */
        public String getRemoteDBMapRequest(int major) {
            return remoteDBPath + remoteDBMapRequest + major;
        }

        /**
         * Metodo che permette di recuperare il formato dei beacon
         * @return String
         */
        public String getBeaconLayout() {
            return beaconLayout;
        }

        /**
         * Metodo che permette di recuperare l'UUID dei beacon riconosciuti dall'applicazione
         * @return String
         */
        public String getApplicationUUID() {
            return applicationUUID;
        }

        /**
         * Metodo che permette di recuperare il percorso della cartella in cui salvare i log
         * @return String
         */
        public String getLogsDirectory() {
            return Environment.getExternalStorageDirectory().toString() + logsDirectory;
        }

        /**
         * Metodo che permette di recuperare la costante per il calcolo del peso degli archi che rappresentano ascensori
         * @return double
         */
        public double getElevatorFactor() {
            return elevatorFactor;
        }

        /**
         * Metodo che permette di recuperare la costante per il calcolo del peso degli archi che rappresentano scale
         * @return double
         */
        public double getStairFactor() {
            return stairFactor;
        }

        /**
         * Metodo che permette di recuperare l'URL del database remoto
         * @return String
         */
        public String getRemoteDBPath() {
            return remoteDBPath;
        }

        /**
         * Costruttore di defaul della classe Configuration
         */
        private Configuration() {}

    }



    public static Configuration getConfiguration() {
        return configuration;
    }
}

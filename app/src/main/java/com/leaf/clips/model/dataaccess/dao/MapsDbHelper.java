package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *Classe che rappresenta un aiutante per ottenere informazioni su come accedere al database locale e remoto
 */
public class MapsDbHelper extends SQLiteOpenHelper {

    /**
     * Nome del database locale. Valore di default: "maps.db"
     */
    public final static String DB_NAME = "maps.db";

    /**
     * Numero di versione del database locale. Valore di default: "1"
     */
    public final static int DB_VERSION = 1;

    /**
     * Istanza di MapsDbHelper salvata per poter essere condivisa
     */
    //private static MapsDbHelper instance;

    /**
     * URL del database remoto
     */
    public final static String REMOTE_DB_URL = "http://52.49.217.118/maps/";

    /**
     * Costruttore della classe MapsDbHelper
     */
    public MapsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Metodo che ritorna una istanza di MapsDbHelper
     * @return  MapsDbHelper
     */
    /*public static MapsDbHelper getInstance(Context context){
        if (instance == null){
            instance = new MapsDbHelper(context);
        }
        return instance;
    }*/

    /**
     * Metodo che ritorna l'URL del database remoto
     * @return  String
     */
    public String getRemoteDatabaseURL(){
        return REMOTE_DB_URL;
    }

    /**
     * Metodo che viene chiamato la prima volta che viene creato il database
     * @param db Riferimento al database
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS Building(id INT PRIMARY KEY,uuid VARCHAR(255) UNIQUE,major INT UNIQUE,name VARCHAR(255),description VARCHAR(2000),openingHours VARCHAR(255),address VARCHAR(255),mapVersion VARCHAR(255),mapSize VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROI(id INT PRIMARY KEY,uuid VARCHAR(255),major INT,minor INT UNIQUE,FOREIGN KEY (uuid) REFERENCES Building(uuid) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (major) REFERENCES Building(major) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Category(id INT PRIMARY KEY,description VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS POI(id INT PRIMARY KEY,name VARCHAR(255),description VARCHAR(2000),categoryId INT,FOREIGN KEY (categoryId) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROIPOI(ROIId INT,POIId INT,PRIMARY KEY (ROIId, POIId),FOREIGN KEY (ROIId) REFERENCES ROI(id) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (POIId) REFERENCES POI(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS EdgeType(id INT PRIMARY KEY,typeName VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Edge(id INT PRIMARY KEY,startROI INT,endROI INT,distance INT,coordinate VARCHAR(255),typeId INT,action VARCHAR(255),longDescription VARCHAR(2000),FOREIGN KEY (startROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (endROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (typeId) REFERENCES EdgeType(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Photo(id INT PRIMARY KEY,URL VARCHAR(2048),edgeId INT,FOREIGN KEY (edgeId) REFERENCES Edge(id))");
    }

    /**
     * Metodo che viene chiamato per effettuare l'upgrade del database
     * @param db Riferimento al database
     * @param oldVersion Numero di versione del vecchio database
     * @param newVersione Numero di versione del nuovo database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersione){ /*TODO*/ }

}

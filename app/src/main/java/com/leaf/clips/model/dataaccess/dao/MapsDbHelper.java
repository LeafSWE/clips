package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leaf.clips.presenter.MyApplication;

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
    public final static String REMOTE_DB_URL = MyApplication.getConfiguration().getRemoteDBPath();

    /**
     * Costruttore della classe MapsDbHelper
     */
    public MapsDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

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
        db.execSQL("CREATE TABLE IF NOT EXISTS Photo(id INT PRIMARY KEY,url VARCHAR(2048),edgeId INT,FOREIGN KEY (edgeId) REFERENCES Edge(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Building(id INT PRIMARY KEY,uuid VARCHAR(255),major INT,name VARCHAR(255),description VARCHAR(2000),openingHours VARCHAR(255),address VARCHAR(255),mapVersion VARCHAR(255),mapSize VARCHAR(255),CONSTRAINT uuid_major_key UNIQUE(uuid,major))");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROI(id INT PRIMARY KEY,uuid VARCHAR(255),major INT,minor INT,FOREIGN KEY (uuid, major) REFERENCES Building(uuid, major) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Category(id INT PRIMARY KEY,description VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS POI(id INT PRIMARY KEY,name VARCHAR(255),description VARCHAR(2000),categoryId INT,FOREIGN KEY (categoryId) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROIPOI(roiId INT,poiId INT,PRIMARY KEY (roiId, poiId),FOREIGN KEY (roiId) REFERENCES ROI(id) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (poiId) REFERENCES POI(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS EdgeType(id INT PRIMARY KEY,typeName VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Edge(id INT PRIMARY KEY,startROI INT,endROI INT,distance INT,coordinate VARCHAR(255),typeId INT,action VARCHAR(255),longDescription VARCHAR(2000),FOREIGN KEY (startROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (endROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (typeId) REFERENCES EdgeType(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS delete_empty_category AFTER DELETE ON POI FOR EACH ROW WHEN (SELECT COUNT(POI.id) FROM POI JOIN Category ON POI.categoryId=Category.id WHERE Category.id=OLD.categoryId) = 0 BEGIN DELETE FROM Category WHERE Category.id=OLD.categoryId; END;");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS delete_empty_edgetype AFTER DELETE ON Edge FOR EACH ROW WHEN (SELECT COUNT(Edge.id) FROM Edge JOIN EdgeType ON Edge.typeId=EdgeType.id WHERE EdgeType.id=OLD.typeId) = 0 BEGIN DELETE FROM EdgeType WHERE EdgeType.id=OLD.typeId; END;");
        db.execSQL("PRAGMA foreign_keys=ON");
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

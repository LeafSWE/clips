package com.leaf.clips.model.dataaccess.dao;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

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
    public final static String REMOTE_DB_URL = "http://52.49.217.118/";

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
        db.execSQL("CREATE TABLE IF NOT EXISTS Photo(id INT PRIMARY KEY,url VARCHAR(2048),edgeId INT,FOREIGN KEY (edgeId) REFERENCES Edge(id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Building(id INT PRIMARY KEY,uuid VARCHAR(255) UNIQUE,major INT UNIQUE,name VARCHAR(255),description VARCHAR(2000),openingHours VARCHAR(255),address VARCHAR(255),mapVersion VARCHAR(255),mapSize VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROI(id INT PRIMARY KEY,uuid VARCHAR(255),major INT,minor INT UNIQUE,FOREIGN KEY (uuid) REFERENCES Building(uuid) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (major) REFERENCES Building(major) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Category(id INT PRIMARY KEY,description VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS POI(id INT PRIMARY KEY,name VARCHAR(255),description VARCHAR(2000),categoryId INT,FOREIGN KEY (categoryId) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS ROIPOI(roiId INT,poiId INT,PRIMARY KEY (roiId, poiId),FOREIGN KEY (roiId) REFERENCES ROI(id) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY (poiId) REFERENCES POI(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE IF NOT EXISTS EdgeType(id INT PRIMARY KEY,typeName VARCHAR(255))");
        db.execSQL("CREATE TABLE IF NOT EXISTS Edge(id INT PRIMARY KEY,startROI INT,endROI INT,distance INT,coordinate VARCHAR(255),typeId INT,action VARCHAR(255),longDescription VARCHAR(2000),FOREIGN KEY (startROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (endROI) REFERENCES ROI(id) ON DELETE CASCADE ON UPDATE CASCADE,FOREIGN KEY (typeId) REFERENCES EdgeType(id) ON UPDATE CASCADE ON DELETE CASCADE)");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS delete_empty_category AFTER DELETE ON POI FOR EACH ROW WHEN (SELECT COUNT(POI.id) FROM POI JOIN Category ON POI.categoryId=Category.id WHERE Category.id=OLD.categoryId) = 0 BEGIN DELETE FROM Category WHERE Category.id=OLD.categoryId; END;");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS delete_empty_edgetype AFTER DELETE ON Edge FOR EACH ROW WHEN (SELECT COUNT(Edge.id) FROM Edge JOIN EdgeType ON Edge.typeId=EdgeType.id WHERE EdgeType.id=OLD.typeId) = 0 BEGIN DELETE FROM EdgeType WHERE EdgeType.id=OLD.typeId; END;");
        /*
        db.execSQL("INSERT INTO Photo VALUES (0, 'http://bucketclips01.s3.amazonaws.com/images/img1.jpg', 0), (1, 'http://bucketclips01.s3.amazonaws.com/images/img2.jpg', 0), (2, 'http://bucketclips01.s3.amazonaws.com/images/img3.jpg', 0)");
        db.execSQL("INSERT INTO Building VALUES (0, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 'Torre Archimede', 'Questa mappa era già presente in locale grazie alla insert in MapsDbHelper.', '07.30 - 19.00. Dal lunedì al venerdì.', 'Via Trieste 63 - 35121 Padova', '1', '200 KB')");
        db.execSQL("INSERT INTO ROI VALUES (0, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 00000), (1, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 00001), (2, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 00002), (3, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 00003), (4, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01000), (5, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01001), (6, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01002), (7, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01003), (8, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01004), (9, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01005), (10, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01006), (11, 'f7826da6-4fa2-4e98-8024-bc5b71e0893e', 666, 01007)");
        db.execSQL("INSERT INTO Category VALUES (0, 'Aule'), (1, 'Toilette'), (2, 'Laboratori'), (3, 'Uffici'), (4, 'Zone relax'), (5, 'Aule studio'), (6, 'Biblioteche'), (7, 'Entrate')");
        db.execSQL("INSERT INTO POI VALUES (0, '1A150', 'Posti disponibili: 150.', 0), (1, '1AD100', 'Posti disponibili: 100.', 0), (2, '1C150', 'Posti disponibili: 150.', 0), (3, '1BC45', 'Posti disponibili: 45.', 0), (4, '1BC50', 'Posti disponibili: 50.', 0), (5, '2AB40', 'Posti disponibili: 40.', 0), (6, '2AB45', 'Posti disponibili: 45.', 0), (7, '2BC30', 'Posti disponibili: 30.', 0), (8, '2BC60', 'Posti disponibili: 60.', 0),(9, 'Toilette donne 1AD', 'Disponibili servizi per disabili.', 1), (10, 'Toilette donne 1BC', 'Disponibili servizi per disabili.', 1), (11, 'Toilette uomini 1AD', 'Disponibili servizi per disabili.', 1), (12, 'Toilette uomini 1BC', 'Disponibili servizi per disabili.', 1), (13, 'Toilette donne 2AD', 'Disponibili servizi per disabili.', 1), (14, 'Toilette donne 2BC', 'Disponibili servizi per disabili.', 1), (15, 'Toilette uomini 2AD', 'Disponibili servizi per disabili.', 1), (16, 'Toilette uomini 2BC', 'Disponibili servizi per disabili.', 1), (17, 'LabTA', 'Orari di apertura: 08.00 - 19.00. Lunedì - venerdì. Posti disponibili: 80.', 2), (18, 'ASTA', 'Orari di apertura: 08.00 - 19.00. Lunedì - venerdì. Posti disponibili: 80.', 5), (19, 'Entrata torre A', '' , 7), (20, 'Entrata torre B', '' , 7), (21, 'Entrata torre C', '' , 7), (22, 'Entrata torre D', '' , 7)");
        db.execSQL("INSERT INTO ROIPOI VALUES (0, 19), (1, 20), (2, 21), (3, 22), (4, 9), (4, 0), (4, 1), (5, 0), (5, 4), (5, 12), (6, 2), (6, 10), (6, 3), (7, 11), (7, 1), (7, 2)");
        db.execSQL("INSERT INTO EdgeType VALUES (0, 'default'), (1, 'stairs'), (2, 'elevator')");
        db.execSQL("INSERT INTO Edge VALUES (0, 0, 8, 1, '1', 1, 'Sali 1 piano di scale', 'longDesc'), (2, 8, 0, 1, '0', 1, 'Scendi 1 piano di scale', 'longDesc'), (3, 1, 9, 1, '0', 1, 'Sali 1 piano di scale', 'longDesc'), (4, 9, 1, 1, '0', 1, 'Scendi 1 piano di scale', 'longDesc'), (5, 2, 10, 1, '0', 1, 'Sali 1 piano di scale', 'longDesc'), (6, 10, 2, 1, '0', 1, 'Scendi 1 piano di scale', 'longDesc'), (7, 3, 11, 1, '0', 1, 'Sali 1 piano di scale', 'longDesc'), (8, 11, 3, 1, '0', 1, 'Scendi 1 piano di scale', 'longDesc'),  (9, 0, 4, 1, '0', 2, 'Sali 1 piano con l''ascensore', 'longDesc'), (10, 4, 0, 1, '0', 2, 'Scendi 1 piano con l''ascensore', 'longDesc'), (11, 1, 5, 1, '0', 2, 'Sali 1 piano con l''ascensore', 'longDesc'), (12, 5, 1, 1, '0', 2, 'Scendi 1 piano con l''ascensore', 'longDesc'), (13, 2, 6, 1, '0', 2, 'Sali 1 piano con l''ascensore', 'longDesc'), (14, 6, 2, 1, '0', 2, 'Scendi 1 piano con l''ascensore', 'longDesc'), (15, 3, 7, 1, '0', 2, 'Sali 1 piano con l''ascensore', 'longDesc'), (16, 7, 3, 1, '0', 2, 'Scendi 1 piano con l''ascensore', 'longDesc'), (17, 8, 4,0, '0', 0, 'Apri la porta di fronte a te e svolta a destra', 'longDesc'), (18, 4, 8,0, '0', 0, 'Oltrepassa la prima porta di fronte a te, svolta a sinistra ed oltrepassa la seconda porta', 'longDesc'), (19, 9, 5, 0, '0', 0, 'Apri la porta di fronte a te e svolta a sinistra', 'longDesc'), (20, 5, 9,0, '0', 0, 'Oltrepassa la prima porta di fronte a te, svolta a destra ed oltrepassa la seconda porta', 'longDesc'), (21, 10, 6,0, '0', 0, 'Apri la porta di fronte a te e svolta a destra', 'longDesc'), (22, 6, 10,0, '0', 0, 'Oltrepassa la prima porta di fronte a te, svolta a sinistra ed oltrepassa la seconda porta', 'longDesc'), (23, 11, 7,0, '0', 0, 'Apri la porta di fronte a te e svolta a sinistra', 'longDesc'), (24, 7, 11,0, '0', 0, 'Oltrepassa la prima porta di fronte a te, svolta a destra ed oltrepassa la seconda porta', 'longDesc'), (25, 4, 7,0, '0', 0, 'Percorri il corridoio', 'longDesc'), (26, 7, 4,0, '0', 0, 'Percorri il corridoio', 'longDesc'), (27, 5, 6,0, '0', 0, 'Percorri il corridoio', 'longDesc'), (28, 6, 5,0, '0', 0, 'Percorri il corridoio', 'longDesc')");

        db.execSQL("PRAGMA foreign_keys=ON");*/
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

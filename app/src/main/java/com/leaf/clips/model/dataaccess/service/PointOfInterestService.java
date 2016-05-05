package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import com.leaf.clips.model.dataaccess.dao.CategoryTable;
import com.leaf.clips.model.dataaccess.dao.PointOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.RemotePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.RoiPoiTable;
import com.leaf.clips.model.dataaccess.dao.SQLiteCategoryDao;
import com.leaf.clips.model.dataaccess.dao.SQLitePointOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 * Classe che rappresenta il layer Service tra gli oggetti PointOfInterest e gli oggetti DAO
 * corrispettivi
 */
public class PointOfInterestService {

    /**
     * Oggetto di utility per la conversione da JSON a CategoryTable
     */
    private RemoteCategoryDao remoteCategoryDao;

    /**
     * Oggetto di utility per la conversione da JSON a PointOfInterestTable
     */
    private RemotePointOfInterestDao remotePointOfInterestDao;

    /**
     * Oggetto di utility per la conversione da JSON a RoiPoiTable
     */
    private RemoteRoiPoiDao remoteRoiPoiDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "Category" del database locale
     */
    private SQLiteCategoryDao sqliteCategoryDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "POI" del database locale
     */
    private SQLitePointOfInterestDao sqlitePointOfInterestDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "ROIPOI" del database locale
     */
    private SQLiteRoiPoiDao sqliteRoiPoiDao;

    /**
     * Costruttore della classe PointOfInterestService
     * @param sqlitePOI Oggetto che rappresenta un DAO per la tabella "POI" del database locale
     * @param remotePOI Oggetto di utility per la conversione da JSON a PointOfInterestTable
     * @param sqliteRoiPoi Oggetto che rappresenta un DAO per la tabella "ROIPOI" del database
     *                     locale
     * @param remoteRoiPoi Oggetto di utility per la conversione da JSON a RoiPoiTable
     * @param sqliteCategory Oggetto che rappresenta un DAO per la tabella "Category" del
     *                       database locale
     * @param remoteCategory Oggetto di utility per la conversione da JSON a CategoryTable
     */
    public PointOfInterestService(SQLitePointOfInterestDao sqlitePOI, RemotePointOfInterestDao
            remotePOI, SQLiteRoiPoiDao sqliteRoiPoi, RemoteRoiPoiDao remoteRoiPoi, SQLiteCategoryDao
            sqliteCategory, RemoteCategoryDao remoteCategory) {

        remoteCategoryDao = remoteCategory;
        remotePointOfInterestDao = remotePOI;
        remoteRoiPoiDao = remoteRoiPoi;
        sqliteCategoryDao = sqliteCategory;
        sqlitePointOfInterestDao = sqlitePOI;
        sqliteRoiPoiDao = sqliteRoiPoi;
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto PointOfInterestTable,
     * che verrà inserito nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di un PointOfInterest
     */
    public void convertAndInsert(JsonObject object) {
        PointOfInterestTable table = remotePointOfInterestDao.fromJSONToTable(object);
        int i = sqlitePointOfInterestDao.insertPointOfInterest(table);
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto CategoryTable,
     * che verrà inserito nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di una categoria
     */
    public void convertAndInsertCategory(JsonObject object) {
        CategoryTable table = remoteCategoryDao.fromJSONToTable(object);
        int i = sqliteCategoryDao.insertCategory(table);
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto RoiPoiTable,
     * che verrà inserito nel database locale
     * @param object Oggetto JsonObject che contiene le stesse informazioni di un oggetto EdgeType
     */
    public void convertAndInsertRoiPoi(JsonObject object) {
        RoiPoiTable table = remoteRoiPoiDao.fromJSONToTable(object);
        int i = sqliteRoiPoiDao.insertRoiPoi(table);
    }

    /**
     * Metodo per rimuovere un PointOfInterest dal database locale
     * @param id Identificativo numerico del PointOfInterest da rimuovere
     */
    public void deletePointOfInterest(int id) {
        // cancello tutte le istanze di ROIPOI nel database dove compare quel POI
        sqliteRoiPoiDao.deleteRoiPoisWherePoi(id);

        // elimino il PointOfInterest dal database
        sqlitePointOfInterestDao.deletePointOfInterest(id);
    }

    /**
     * Metodo per recuperare le informazioni di tutti i PointOfInterest di un edificio,
     * dato il major dell'edificio
     * @param major Major dell'edificio
     * @return  Collection<PointOfInterest>
     */
    public Collection<PointOfInterest> findAllPointsWithMajor(int major) {
        Collection<PointOfInterestTable> tables =
                sqlitePointOfInterestDao.findAllPointsWithMajor(major);
        Iterator<PointOfInterestTable> iter = tables.iterator();
        List<PointOfInterest> pois = new LinkedList<PointOfInterest>();
        while(iter.hasNext()) {
            PointOfInterestTable table = iter.next();
            PointOfInterest poi = fromTableToBo(table);
            pois.add(poi);
        }
        return pois;
    }

    /**
     * Metodo per recuperare un PointOfInterest ricercandolo nel database locale
     * @param id Identificativo numerico del PointOfInterest da recuperare
     * @return  PointOfInterest
     */
    public PointOfInterest findPointOfInterest(int id) {
        PointOfInterestTable table = sqlitePointOfInterestDao.findPointOfInterest(id);
        PointOfInterest poi = fromTableToBo(table);
        return poi;
    }

    /**
     * Metodo per la costruzione di oggetto PointOfInterest a partire da un PointOfInterestTable
     * @param pointOfInterestTable Oggetto contenente le informazioni del PointOfInterest
     * @return  PointOfInterest
     */
    private PointOfInterest fromTableToBo(PointOfInterestTable pointOfInterestTable) {
        // recupero le informazioni dall'oggetto PointOfInterestTable
        int id = pointOfInterestTable.getId();
        String name = pointOfInterestTable.getName();
        String description = pointOfInterestTable.getDescription();
        int categoryId = pointOfInterestTable.getCategoryId();

        // recupero il nome della categoria, necessario per creare le PointOfInterestInformation
        CategoryTable categoryTable = sqliteCategoryDao.findCategory(categoryId);
        String category = categoryTable.getDescription();

        // costruisco l'oggetto PointOfInterestInformation
        PointOfInterestInformation poiInfo =
                new PointOfInterestInformation(name, description, category);

        // costruisco il PointOfInterest da restituire
        PointOfInterest poi = new PointOfInterest(id, poiInfo);
        return poi;
    }

}

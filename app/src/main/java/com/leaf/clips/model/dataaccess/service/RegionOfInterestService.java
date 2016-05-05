package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import com.leaf.clips.model.dataaccess.dao.RegionOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
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
 * Classe che rappresenta il layer Service tra gli oggetti RegionOfInterest e
 * gli oggetti DAO corrispettivi
 */

public class RegionOfInterestService {

    /**
     * Oggetto di utility per la conversione da JSON a RegionOfInterestTable
     */
    private RemoteRegionOfInterestDao remoteRegionOfInterestDao;

    /**
     * Oggetto di utility per la conversione da JSON a RoiPoiTable
     */
    private RemoteRoiPoiDao remoteRoiPoiDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "ROI" del database locale
     */
    private SQLiteRegionOfInterestDao sqliteRegionOfInterestDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "ROIPOI" del database locale
     */
    private SQLiteRoiPoiDao sqliteRoiPoiDao;

    /**
     * Costruttore della classe RegionOfInterestService
     * @param sqliteROI Oggetto che rappresenta un DAO per la tabella "ROI" del database locale
     * @param remoteROI Oggetto di utility per la conversione da JSON a RegionOfInterestTable
     * @param sqliteRoiPoi Oggetto che rappresenta un DAO per la tabella "ROIPOI" del database
     *                     locale
     * @param remoteRoiPoi Oggetto di utility per la conversione da JSON a RoiPoiTable
     */
    public RegionOfInterestService(SQLiteRegionOfInterestDao sqliteROI, RemoteRegionOfInterestDao
            remoteROI, SQLiteRoiPoiDao sqliteRoiPoi, RemoteRoiPoiDao remoteRoiPoi) {
        sqliteRegionOfInterestDao = sqliteROI;
        sqliteRoiPoiDao = sqliteRoiPoi;
        remoteRegionOfInterestDao = remoteROI;
        remoteRoiPoiDao = remoteRoiPoi;
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto RegionOfInterestTable,
     * che verrà inserito nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di una RegionOfInterest
     */
    public void convertAndInsert(JsonObject object) {
        RegionOfInterestTable table = remoteRegionOfInterestDao.fromJSONToTable(object);
        sqliteRegionOfInterestDao.insertRegionOfInterest(table);
    }

    /**
     * Metodo per rimuovere una RegionOfInterest dal database locale
     * @param id Identificativo numerico della RegionOfInterest da rimuovere
     */
    public void deleteRegionOfInterest(int id) {
        // cancello tutte le istanze di ROIPOI nel database dove compare quella ROI
        sqliteRoiPoiDao.deleteRoiPoisWhereRoi(id);

        // elimino la RegionOfInterest dal database
        sqliteRegionOfInterestDao.deleteRegionOfInterest(id);
    }

    /**
     * Metodo per recuperare le informazioni di tutte le RegionOfInterest di un edificio,
     * dato il major dell'edificio
     * @param major Major dell'edificio
     * @return  Collection<RegionOfInterest>
     */
    public Collection<RegionOfInterest> findAllRegionsWithMajor(int major) {
        Collection<RegionOfInterestTable> tables =
                sqliteRegionOfInterestDao.findAllRegionsWithMajor(major);
        Iterator<RegionOfInterestTable> iter = tables.iterator();
        List<RegionOfInterest> rois = new LinkedList<RegionOfInterest>();
        while(iter.hasNext()) {
            RegionOfInterestTable table = iter.next();
            RegionOfInterest roi = fromTableToBo(table);
            rois.add(roi);
        }
        return rois;
    }

    /**
     * Metodo per recuperare una RegionOfInterest ricercandola nel database locale
     * @param id Identificativo numerico della RegionOfInterest da recuperare
     * @return  RegionOfInterest
     */
    public RegionOfInterest findRegionOfInterest(int id) {
        RegionOfInterestTable table = sqliteRegionOfInterestDao.findRegionOfInterest(id);
        RegionOfInterest roi = fromTableToBo(table);
        return roi;
    }

    /**
     * Metodo per la costruzione di oggetto RegionOfInterest a partire da un RegionOfInterestTable
     * @param regionOfInterestTable Oggetto contenente le informazioni della RegionOfInterest
     * @return  RegionOfInterest
     */
    private RegionOfInterest fromTableToBo(RegionOfInterestTable regionOfInterestTable) {
        // recupero tutte le informazioni dall'oggetto RegionOfInterestTable
        int id = regionOfInterestTable.getId();
        String uuid = regionOfInterestTable.getUUID();
        int major = regionOfInterestTable.getMajor();
        int minor = regionOfInterestTable.getMinor();

        // recupero i PointOfInterest vicini alla RegionOfInterest
        Collection<PointOfInterest> pois = sqliteRoiPoiDao.findAllPointsWithRoi(id);

        // creo la RegionOfInterest e poi inserisco i POI vicini
        RegionOfInterest roi = new RegionOfInterestImp(id, uuid, major, minor);
        roi.setNearbyPOIs(pois);

        // ritorno la RegionOfInterest costruita
        return roi;
    }
}


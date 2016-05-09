package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import com.leaf.clips.model.dataaccess.dao.RegionOfInterestTable;
import com.leaf.clips.model.dataaccess.dao.RemoteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.RemoteRoiPoiDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRegionOfInterestDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteRoiPoiDao;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterestImp;

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
     * Lista delle RegionOfInterest della mappa dell'edificio che si sta costruendo
     * che sono già state associate ai PointOfInterest vicini
     */
    private Collection<RegionOfInterest> tracedRois;

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
     * Metodo per impostare le RegionOfInterest che sono già state associate ai POI vicini
     * @param tracedRois Le RegionOfInterest che sono già state associate ai POI vicini
     */
    public void setTracedRois(Collection<RegionOfInterest> tracedRois) {
        this.tracedRois = tracedRois;
    }

    /**
     * Metodo per recuperare le RegionOfInterest che sono già state associate ai POI vicini
     * @return Collection<RegionOfInterest> Le RegionOfInterest che sono già state associate ai
     * POI vicini
     */
    public Collection<RegionOfInterest> getTracedRois() {
        return tracedRois;
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
     * @return  Collection<RegionOfInterest> Le informazioni su tutte le RegionOfInterest di un
     * edificio
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
     * @return  RegionOfInterest La RegionOfInterest richiesta
     */
    public RegionOfInterest findRegionOfInterest(int id) {
        RegionOfInterestTable table = sqliteRegionOfInterestDao.findRegionOfInterest(id);
        return fromTableToBo(table);
    }

    /**
     * Metodo per recuperare gli identificativi di tutti i PointOfInterest associati ad una
     * specifica RegionOfInterest
     * @param roiId Identificativo della RegionOfInterest
     * @return int[] Insieme degli identificativi dei PointOfInterest associati ad una specifica
     * RegionOfInterest
     */
    public int[] findAllPointsWithRoi(int roiId) {
        return sqliteRoiPoiDao.findAllPointsWithRoi(roiId);
    }

    /**
     * Metodo per la costruzione di oggetto RegionOfInterest a partire da un RegionOfInterestTable
     * @param regionOfInterestTable Oggetto contenente le informazioni della RegionOfInterest
     * @return  RegionOfInterest La RegionOfInterest costruita dopo aver recuperato tutte le
     * informazioni necessarie
     */
    private RegionOfInterest fromTableToBo(RegionOfInterestTable regionOfInterestTable) {
        // recupero tutte le informazioni dall'oggetto RegionOfInterestTable
        int id = regionOfInterestTable.getId();
        String uuid = regionOfInterestTable.getUUID();
        int major = regionOfInterestTable.getMajor();
        int minor = regionOfInterestTable.getMinor();

        // creo la RegionOfInterest da ritornare
        return new RegionOfInterestImp(id, uuid, major, minor);
    }
}

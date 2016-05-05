package com.leaf.clips.model.dataaccess.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.dao.RemoteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 * Classe che rappresenta il layer Service tra gli oggetti BuildingMap e gli oggetti DAO
 * corrispettivi
 */
public class BuildingService implements DatabaseService {

    /**
     * URL del database remoto
     */
    private String databaseURL;

    /**
     * Oggetto che si pone come layer Service tra gli oggetti EnrichedEdge
     * e gli oggetti DAO corrispettivi
     */
    private EdgeService edgeService;

    /**
     * Oggetto che si pone come layer Service tra gli oggetti PointOfInterest
     * e gli oggetti DAO corrispettivi
     */
    private PointOfInterestService poiService;

    /**
     * Oggetto di utility per la conversione da JSON a BuildingTable
     */
    private RemoteBuildingDao remoteBuildingDao;

    /**
     * Oggetto che si pone come layer Service tra gli oggetti RegionOfInterest
     * e gli oggetti DAO corrispettivi
     */
    private RegionOfInterestService roiService;

    /**
     * Oggetto che rappresenta un DAO per la tabella "Building" del database locale
     */
    private SQLiteBuildingDao sqliteBuildingDao;

    /**
     * Costruttore della classe BuildingService
     * @param dbURL URL del database remoto
     * @param sqliteBuilding Oggetto che rappresenta un DAO per la tabella "Building"
     *                       del database locale
     * @param remoteBuilding Oggetto di utility per la conversione da JSON a BuildingTable
     * @param roiService Oggetto che si pone come layer Service tra gli oggetti RegionOfInterest
     *                   e gli oggetti DAO corrispettivi
     * @param poiService Oggetto che si pone come layer Service tra gli oggetti PointOfInterest
     *                   e gli oggetti DAO corrispettivi
     * @param edgeService Oggetto che si pone come layer Service tra gli oggetti EnrichedEdge
     *                    e gli oggetti DAO corrispettivi
     */
    public BuildingService(String dbURL, SQLiteBuildingDao sqliteBuilding,
                           RemoteBuildingDao remoteBuilding, RegionOfInterestService roiService,
                           PointOfInterestService poiService, EdgeService edgeService) {

        databaseURL = dbURL;
        sqliteBuildingDao = sqliteBuilding;
        remoteBuildingDao = remoteBuilding;
        this.roiService = roiService;
        this.poiService = poiService;
        this.edgeService = edgeService;
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto BuildingTable, che verrà
     * inserito nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di un edificio
     */
    public void convertAndInsert(JsonObject object) {
        BuildingTable table = remoteBuildingDao.fromJSONToTable(object);
        int i = sqliteBuildingDao.insertBuilding(table);
    }

    /**
     * Metodo per rimuovere la mappa di un edificio dal database locale
     * @param buildingMap Mappa dell'edificio da rimuovere
     */
    @Override
    public void deleteBuilding(BuildingMap buildingMap) {

        // recupero ed elimino tutti gli edge della mappa
        Collection<EnrichedEdge> edges = buildingMap.getAllEdges();
        Iterator<EnrichedEdge> edgeIterator = edges.iterator();
        while(edgeIterator.hasNext())
            edgeService.deleteEdge(edgeIterator.next());

        // recupero ed elimino tutti i PointOfInterest della mappa
        Collection<PointOfInterest> pois = buildingMap.getAllPOIs();
        Iterator<PointOfInterest> poiIterator = pois.iterator();
        while(poiIterator.hasNext()) {
            PointOfInterest poi = poiIterator.next();
            poiService.deletePointOfInterest(poi.getId());
        }

        // recupero ed elimino tutte le RegionOfInterest della mappa
        Collection<RegionOfInterest> rois = buildingMap.getAllROIs();
        Iterator<RegionOfInterest> roiIterator = rois.iterator();
        while(roiIterator.hasNext()) {
            RegionOfInterest roi = roiIterator.next();
            roiService.deleteRegionOfInterest(roi.getId());
        }

        // elimino l'edificio
        int buildingId = buildingMap.getId();
        sqliteBuildingDao.deleteBuilding(buildingId);
    }

    /**
     * Metodo per recuperare le informazioni di tutte le mappe degli edifici
     * presenti nel database locale
     * @return  Collection<BuildingTable>
     */
    @Override
    public Collection<BuildingTable> findAllBuildings() {
        return sqliteBuildingDao.findAllBuildings();
    }

    /**
     * Metodo per recuperare le informazioni di tutte le mappe degli edifici
     * presenti nel database remoto
     * @return  Collection<BuildingTable>
     */
    @Override
    public Collection<BuildingTable> findAllRemoteBuildings() {
        /**
         * Contatto il db remoto su "/allMaps" e ottengo tutte le istanze della tabella Building.
         * Poi procedo al parsing del risultato (recupero un JsonArray chiamato "building")
         * e per ogni JsonObject che rappresenta una BuildingTable invoco
         * remoteBuildingDao.fromJSONToTable() che mi ritorna una BuildingTable.
         * Raccolgo tutti gli oggetti BuildingTable in una lista che ritorno.
         */

        try (
                InputStream input = new URL("http://52.49.217.118/allMaps").openStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(input));
        ) {
            String inputStr;
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JsonParser parser = new JsonParser();
            JsonObject js = parser.parse(responseStrBuilder.toString()).getAsJsonObject();

            // recupero e inserisco nel db tutte le entry della tabella Building
            JsonArray buildingArray = js.get("building").getAsJsonArray();
            List<BuildingTable> tables = new LinkedList<BuildingTable>();
            for (int i = 0; i < buildingArray.size(); i++) {
                // costruisco una BuildingTable e la inserisco nella lista
                JsonObject object = buildingArray.get(i).getAsJsonObject();
                BuildingTable table = remoteBuildingDao.fromJSONToTable(object);
                tables.add(table);
            }
            return tables;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Metodo per recuperare la mappa di un edificio ricercandola nel database locale
     * @param major Major dell'edificio
     * @return  BuildingMap
     */
    @Override
    public BuildingMap findBuildingByMajor(int major) {
        BuildingTable table = sqliteBuildingDao.findBuildingByMajor(major);
        return fromTableToBo(table);
    }

    /**
     * Metodo per recuperare la mappa di un edificio ricercandola nel database remoto
     * @param major Major dell'edificio
     * @return  BuildingMap
     */
    @Override
    public BuildingMap findRemoteBuildingByMajor(int major) {
        retrieveAndInsertMap(major);
        return findBuildingByMajor(major);
    }

    /**
     * Metodo per la costruzione di oggetto BuildingMap a partire da un BuildingTable
     * @param buildingTable Oggetto contenente le informazioni dell'edificio
     * @return  BuildingMap
     */
    private BuildingMap fromTableToBo(BuildingTable buildingTable) {
        // recupero tutte le informazioni dall'oggetto BuildingTable
        int id = buildingTable.getId();
        int version = buildingTable.getVersion();
        String uuid = buildingTable.getUUID();
        int major = buildingTable.getMajor();
        String name = buildingTable.getName();
        String description = buildingTable.getDescription();
        String openingHours = buildingTable.getOpeningHours();
        String address = buildingTable.getAddress();
        String size = buildingTable.getSize();

        // recupero tutti i RegionOfInterest riguardanti l'edificio
        Collection<RegionOfInterest> rois = roiService.findAllRegionsWithMajor(major);

        // recupero tutti i PointOfInterest
        Collection<PointOfInterest> pois = poiService.findAllPointsWithMajor(major);

        // recupero tutti gli EnrichedEdge
        Collection<EnrichedEdge> edges = edgeService.findAllEdgesOfBuilding(major);

        // creo l'oggetto BuildingInformation necessario per la costruzione della BuildingMapImp
        BuildingInformation info =
                new BuildingInformation(name, description, openingHours, address);

        // creo l'oggetto BuildingMap da ritornare
        return new BuildingMapImp(edges, id, version, pois, rois, info, size);
    }

    /**
     * Metodo per verificare la presenza di una mappa di un edificio nel database locale
     * @param major Major dell'edificio
     * @return  boolean
     */
    @Override
    public boolean isBuildingMapPresent(int major) {
        return sqliteBuildingDao.isBuildingMapPresent(major);
    }

    /**
     * Metodo per verificare se la mappa di un edificio è aggiornata all'ultima versione
     * @param major Major dell'edificio
     * @return  boolean
     */
    @Override
    public boolean isBuildingMapUpdated(int major) {
        try (
                InputStream input = new URL("http://52.49.217.118/mapVersion/?major="+major).openStream();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(input));
        ) {
            String inputStr;
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JsonParser parser = new JsonParser();
            JsonObject object = parser.parse(responseStrBuilder.toString()).getAsJsonObject();

            // recupero l'ultima versione disponibile della mappa
            int updatedVersion = object.get("mapVersion").getAsInt();;

            // recupero la versione della mappa sul database locale
            BuildingTable table = sqliteBuildingDao.findBuildingByMajor(major);
            int actualVersion = table.getVersion();

            return actualVersion == updatedVersion;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Metodo per scaricare la mappa di un edificio dal database remoto
     * ed inserirla nel database locale
     * @param major Major dell'edificio
     */
    private void retrieveAndInsertMap(int major) {
        try (
            InputStream input = new URL("http://52.49.217.118/maps/?major="+major).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(input));
        ) {
            String inputStr;
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JsonParser parser = new JsonParser();
            JsonObject js = parser.parse(responseStrBuilder.toString()).getAsJsonObject();

            // recupero e inserisco nel db la singola entry della tabella Building
            JsonObject object_ = js.get("building").getAsJsonObject();
            convertAndInsert(object_);

            // recupero e inserisco nel db tutte le entry della tabella ROI
            JsonArray roisArray = js.get("rois").getAsJsonArray();
            for (int i = 0; i < roisArray.size(); i++) {
                JsonObject object = roisArray.get(i).getAsJsonObject();
                roiService.convertAndInsert(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella Category
            JsonArray categoriesArray = js.get("categories").getAsJsonArray();
            for (int i = 0; i < categoriesArray.size(); i++) {
                JsonObject object = categoriesArray.get(i).getAsJsonObject();
                poiService.convertAndInsertCategory(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella POI
            JsonArray poisArray = js.get("pois").getAsJsonArray();
            for (int i = 0; i < poisArray.size(); i++) {
                JsonObject object = poisArray.get(i).getAsJsonObject();
                poiService.convertAndInsert(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella ROIPOI
            JsonArray roipoisArray = js.get("roipois").getAsJsonArray();
            for (int i = 0; i < roipoisArray.size(); i++) {
                JsonObject object = roipoisArray.get(i).getAsJsonObject();
                poiService.convertAndInsertRoiPoi(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella EdgeType
            JsonArray edgeTypesArray = js.get("edgeTypes").getAsJsonArray();
            for (int i = 0; i < edgeTypesArray.size(); i++) {
                JsonObject object = edgeTypesArray.get(i).getAsJsonObject();
                edgeService.convertAndInsertEdgeType(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella Edge
            JsonArray edgesArray = js.get("edges").getAsJsonArray();
            for (int i = 0; i < edgesArray.size(); i++) {
                JsonObject object = edgesArray.get(i).getAsJsonObject();
                edgeService.convertAndInsert(object);
            }

            // recupero e inserisco nel db tutte le entry della tabella Photo
            JsonArray photosArray = js.get("photos").getAsJsonArray();
            for (int i = 0; i < photosArray.size(); i++) {
                JsonObject object = photosArray.get(i).getAsJsonObject();
                edgeService.convertAndInsertPhoto(object);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per aggiornare la mappa di un edificio all'ultima versione disponibile
     * @param major Major dell'edificio
     */
    @Override
    public void updateBuildingMap(int major) {
        BuildingMap map = findBuildingByMajor(major);
        deleteBuilding(map);
        retrieveAndInsertMap(major);
    }

}

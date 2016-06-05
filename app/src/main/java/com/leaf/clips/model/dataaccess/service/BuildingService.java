package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.dao.RemoteBuildingDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteBuildingDao;
import com.leaf.clips.model.navigator.BuildingInformation;
import com.leaf.clips.model.navigator.BuildingMap;
import com.leaf.clips.model.navigator.BuildingMapImp;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.presenter.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        for (EnrichedEdge edge : edges) edgeService.deleteEdge(edge);

        // recupero ed elimino tutti i PointOfInterest della mappa
        Collection<PointOfInterest> pois = buildingMap.getAllPOIs();
        for (PointOfInterest poi : pois) {
            poiService.deletePointOfInterest(poi.getId());
        }

        // recupero ed elimino tutte le RegionOfInterest della mappa
        Collection<RegionOfInterest> rois = buildingMap.getAllROIs();
        for (RegionOfInterest roi : rois) {
            roiService.deleteRegionOfInterest(roi.getId());
        }

        // elimino l'edificio
        int buildingId = buildingMap.getId();
        sqliteBuildingDao.deleteBuilding(buildingId);
    }

    /**
     * Metodo per recuperare le informazioni di tutte le mappe degli edifici
     * presenti nel database locale
     * @return  Collection<BuildingTable> Le informazioni sulle mappe degli edifici già scaricate
     */
    @Override
    public Collection<BuildingTable> findAllBuildings() {
        return sqliteBuildingDao.findAllBuildings();
    }

    /**
     * Metodo per recuperare le informazioni di tutte le mappe degli edifici
     * presenti nel database remoto
     * @return  Collection<BuildingTable> Le informazioni sulle mappe degli edifici supportati
     * dall'applicazione
     */
    @Override
    public Collection<BuildingTable> findAllRemoteBuildings() throws IOException {
        AsyncFindRemoteBuilding asyncFindRemoteBuilding = new AsyncFindRemoteBuilding();
        asyncFindRemoteBuilding.execute();

        Collection<BuildingTable> buildingTables = null;

        try {
            buildingTables = (Collection<BuildingTable>) asyncFindRemoteBuilding.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  buildingTables;
    }

    // TODO: 04/06/2016 Classe nuova
    private class AsyncFindRemoteBuilding extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            /**
             * Contatto il db remoto su "/allMaps" e ottengo tutte le istanze della tabella Building.
             * Poi procedo al parsing del risultato (recupero un JsonArray chiamato "building")
             * e per ogni JsonObject che rappresenta una BuildingTable invoco
             * remoteBuildingDao.fromJSONToTable() che mi ritorna una BuildingTable.
             * Raccolgo tutti gli oggetti BuildingTable in una lista che ritorno.
             */

            String url = databaseURL+"allMaps";
            try (
                    InputStream input = new URL(url).openStream();
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
                List<BuildingTable> tables = new LinkedList<>();
                for (int i = 0; i < buildingArray.size(); i++) {
                    // costruisco una BuildingTable e la inserisco nella lista
                    JsonObject object = buildingArray.get(i).getAsJsonObject();
                    BuildingTable table = remoteBuildingDao.fromJSONToTable(object);
                    tables.add(table);
                }
                return tables;

            } catch (IOException exc) {
                try {
                    throw exc;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Metodo per recuperare la mappa di un edificio ricercandola nel database locale
     * @param major Major dell'edificio
     * @return  BuildingMap La mappa dell'edificio richiesto
     */
    @Override
    public BuildingMap findBuildingByMajor(int major) {
        BuildingTable table = sqliteBuildingDao.findBuildingByMajor(major);
        return fromTableToBo(table);
    }

    // TODO: 04/06/2016 Classe nuova
    private class AsyncFindRemoteBuildingByMajor extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                retrieveAndInsertMap((int)params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return findBuildingByMajor((int) params[0]);
        }
    }
    /**
     * Metodo per recuperare la mappa di un edificio ricercandola nel database remoto
     * @param major Major dell'edificio
     * @return  BuildingMap La mappa dell'edificio richiesto
     */
    @Override
    public BuildingMap findRemoteBuildingByMajor(int major) throws IOException {
        AsyncFindRemoteBuildingByMajor asyncFindRemoteBuildingByMajor = new AsyncFindRemoteBuildingByMajor();
        asyncFindRemoteBuildingByMajor.execute(major);

        BuildingMap buildingMap = null;

        try {
            buildingMap = (BuildingMap) asyncFindRemoteBuildingByMajor.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return buildingMap;
    }

    /**
     * Metodo per la costruzione di oggetto BuildingMap a partire da un BuildingTable
     * @param buildingTable Oggetto contenente le informazioni dell'edificio
     * @return  BuildingMap La mappa costruita dopo aver recuperato tutte le informazioni necessarie
     */
    private BuildingMap fromTableToBo(BuildingTable buildingTable) {
        // recupero tutte le informazioni dall'oggetto BuildingTable
        int id = buildingTable.getId();
        int version = buildingTable.getVersion();
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

        // associo ad ogni RegionOfInterest di un edificio i POI vicini
        traceRois(rois, pois);

        // associo ad ogni PointOfInterest dell'edificio le ROI vicine
        tracePois(pois, rois);

        roiService.setTracedRois(rois);

        // recupero tutti gli EnrichedEdge
        Collection<EnrichedEdge> edges = edgeService.findAllEdgesOfBuilding(major);

        // creo l'oggetto BuildingInformation necessario per la costruzione della BuildingMapImp
        BuildingInformation info =
                new BuildingInformation(name, description, openingHours, address);

        // creo l'oggetto BuildingMap da ritornare
        return new BuildingMapImp(edges, id, version, pois, rois, info, size);
    }

    /**
     * Metodo che permette di associare ad ogni RegionOfInterest di un edificio i POI vicini
     * @param rois Le RegionOfInterest da tracciare
     * @param pois I PointOfInterest dell'edificio
     */
    private void traceRois(Collection<RegionOfInterest> rois, Collection<PointOfInterest> pois) {
        Iterator<RegionOfInterest> roiIt = rois.iterator();
        RegionOfInterest roi;
        int roiId;
        int[] nearbyPOI;
        Iterator<PointOfInterest> poiIt;
        List<PointOfInterest> poisOfRoi;
        boolean found;
        PointOfInterest actualPOI;

        while(roiIt.hasNext()) {
            roi = roiIt.next();
            roiId = roi.getId();
            nearbyPOI = roiService.findAllPointsWithRoi(roiId);
            poisOfRoi = new LinkedList<>();
            for (int i=0; i<nearbyPOI.length; i++) {
                poiIt = pois.iterator();
                found = false;
                while(!found && poiIt.hasNext()) {
                    actualPOI = poiIt.next();
                    if(nearbyPOI[i] == actualPOI.getId()) {
                        found = true;
                        poisOfRoi.add(actualPOI);
                    }
                }
            }
            roi.setNearbyPOIs(poisOfRoi);
        }
    }

    /**
     * Metodo che permette di associare ad ogni PointOfInterest dell'edificio le ROI vicine
     * @param pois I PointOfInterest da tracciare
     * @param rois Le RegionOfInterest dell'edificio
     */
    private void tracePois(Collection<PointOfInterest> pois, Collection<RegionOfInterest> rois) {
        Iterator<PointOfInterest> poiIt = pois.iterator();
        PointOfInterest poi;
        int poiId;
        int[] nearbyROI;
        List<RegionOfInterest> roisOfPoi;
        RegionOfInterest actualROI;
        Iterator<RegionOfInterest> roiIt;
        boolean found;

        while(poiIt.hasNext()) {
            poi = poiIt.next();
            poiId = poi.getId();
            nearbyROI = poiService.findAllRegionsWithPoi(poiId);
            roisOfPoi = new LinkedList<>();
            for (int i=0; i<nearbyROI.length; i++) {
                roiIt = rois.iterator();
                found = false;
                while(!found && roiIt.hasNext()) {
                    actualROI = roiIt.next();
                    if(nearbyROI[i] == actualROI.getId()) {
                        found = true;
                        roisOfPoi.add(actualROI);
                    }
                }
            }
            poi.setBelongingROIs(roisOfPoi);
        }
    }


    /**
     * Metodo per verificare la presenza di una mappa di un edificio nel database locale
     * @param major Major dell'edificio
     * @return  boolean True se la mappa è presente nel database locale, false in caso contrario
     */
    @Override
    public boolean isBuildingMapPresent(int major) {
        return sqliteBuildingDao.isBuildingMapPresent(major);
    }

    // TODO: 04/06/2016 Classe nuova
    private class AsyncIsRemoteMapPresent extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            Log.i("Async", "doInBackground");
            int mapVersion = 0;
            String url = MyApplication.getConfiguration().getRemoteDBMapRequest((int)params[0]);
            try (
                    InputStream input = new URL(url).openStream();
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(input));
            ) {
                String inputStr;
                StringBuilder responseStrBuilder = new StringBuilder();
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(responseStrBuilder.toString()).getAsJsonObject();

                // recupero l'ultima versione disponibile della mappa
                 mapVersion = object.get("mapVersion").getAsInt();

            } catch (IOException exc) {
                try {
                    throw exc;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return mapVersion != -1;

        }
    }
    /**
     * Metodo per verificare la presenza di una mappa di un edificio nel database remoto
     * @param major Major dell'edificio
     * @return boolean True se la mappa è presente nel database remoto, false in caso contrario
     */
    @Override
    public boolean isRemoteMapPresent(int major) throws IOException {
        AsyncIsRemoteMapPresent asyncIsRemoteMapPresent = new AsyncIsRemoteMapPresent();
        boolean isRemoteMapPresent = false;
        asyncIsRemoteMapPresent.execute(major);
        try {
            isRemoteMapPresent = (boolean) asyncIsRemoteMapPresent.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isRemoteMapPresent;
    }

    // TODO: 04/06/2016 Classe nuova
    private class AsyncIsBuildingMapUpdated extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            int actualVersion = 0;
            int updatedVersion = 0;
            String url = MyApplication.getConfiguration().getRemoteDBMapRequest((int)params[0]);
            try (
                    InputStream input = new URL(url).openStream();
                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(input));
            ) {
                String inputStr;
                StringBuilder responseStrBuilder = new StringBuilder();
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                JsonParser parser = new JsonParser();
                JsonObject object = parser.parse(responseStrBuilder.toString()).getAsJsonObject();

                // recupero l'ultima versione disponibile della mappa
                updatedVersion = object.get("mapVersion").getAsInt();

                // recupero la versione della mappa sul database locale
                BuildingTable table = sqliteBuildingDao.findBuildingByMajor((int)params[0]);
                actualVersion = table.getVersion();

            } catch (IOException exc) {
                try {
                    throw exc;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return actualVersion == updatedVersion;
        }
    }
    /**
     * Metodo per verificare se la mappa di un edificio è aggiornata all'ultima versione
     * @param major Major dell'edificio
     * @return  boolean True se la mappa è aggiornata all'ultima versione disponibile, false in
     * caso contrario
     */
    @Override
    public boolean isBuildingMapUpdated(int major) throws IOException {
        AsyncIsBuildingMapUpdated asyncIsBuildingMapUpdated = new AsyncIsBuildingMapUpdated();
        asyncIsBuildingMapUpdated.execute(major);
        boolean isBuilingMapUpdated = false;
        try {
            isBuilingMapUpdated = (boolean) asyncIsBuildingMapUpdated.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return isBuilingMapUpdated;
    }

    /**
     * Metodo per scaricare la mappa di un edificio dal database remoto
     * ed inserirla nel database locale
     * @param major Major dell'edificio
     */
    private void retrieveAndInsertMap(int major) throws IOException {

        String url = databaseURL+"maps/?major="+major;
        try (
            InputStream input = new URL(url).openStream();
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

        } catch (IOException exc) {
            throw exc;
        }
    }

    // TODO: 6/4/16 Classe nuova 
    private class AsyncUpdateBuildingMap extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            BuildingMap map = findBuildingByMajor((int)params [0]);
            deleteBuilding(map);
            try {
                retrieveAndInsertMap((int)params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    /**
     * Metodo per aggiornare la mappa di un edificio all'ultima versione disponibile
     * @param major Major dell'edificio
     */
    @Override
    public void updateBuildingMap(int major) throws IOException {
        AsyncUpdateBuildingMap asyncUpdateBuildingMap = new AsyncUpdateBuildingMap();
        asyncUpdateBuildingMap.execute(major);
        try {
            asyncUpdateBuildingMap.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

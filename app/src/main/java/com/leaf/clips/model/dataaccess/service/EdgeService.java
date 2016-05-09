package com.leaf.clips.model.dataaccess.service;

import com.google.gson.JsonObject;

import com.leaf.clips.model.dataaccess.dao.EdgeTable;
import com.leaf.clips.model.dataaccess.dao.EdgeTypeTable;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.RemoteEdgeTypeDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeDao;
import com.leaf.clips.model.dataaccess.dao.SQLiteEdgeTypeDao;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.DefaultEdge;
import com.leaf.clips.model.navigator.graph.edge.ElevatorEdge;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.navigator.graph.edge.StairEdge;
import com.leaf.clips.model.navigator.graph.navigationinformation.BasicInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.DetailedInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformationImp;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Davide Castello
 * @version 0.01
 * @since 0.00
 *
 * Classe che rappresenta il layer Service tra gli oggetti EnrichedEdge e gli oggetti DAO
 * corrispettivi
 *
 */

public class EdgeService {


    /**
     * Oggetto che si pone come layer Service tra gli oggetti PhotoRef e gli oggetti DAO
     * corrispettivi
     */
    private PhotoService photoService;

    /**
     * Oggetto di utility per la conversione da JSON a EdgeTable
     */
    private RemoteEdgeDao remoteEdgeDao;

    /**
     * Oggetto di utility per la conversione da JSON a EdgeTypeTable
     */
    private RemoteEdgeTypeDao remoteEdgeTypeDao;

    /**
     * Oggetto che si pone come layer Service tra gli oggetti RegionOfInterest e gli oggetti DAO
     * corrispettivi
     */
    private RegionOfInterestService roiService;

    /**
     * Oggetto che rappresenta un DAO per la tabella "Edge" del database locale
     */
    private SQLiteEdgeDao sqliteEdgeDao;

    /**
     * Oggetto che rappresenta un DAO per la tabella "EdgeType" del database locale
     */
    private SQLiteEdgeTypeDao sqliteEdgeTypeDao;

    /**
     * Costruttore della classe EdgeService
     * @param sqliteEdge Oggetto che rappresenta un DAO per la tabella "Edge" del database locale
     * @param remoteEdge Oggetto di utility per la conversione da JSON a EdgeTable
     * @param sqliteEdgeType Oggetto che rappresenta un DAO per la tabella "EdgeType" del database
     *                       locale
     * @param remoteEdgeType Oggetto di utility per la conversione da JSON a EdgeTypeTable
     * @param photoService Oggetto che si pone come layer Service tra gli oggetti PhotoRef e gli
     *                     oggetti DAO corrispettivi
     * @param roiService Oggetto che si pone come layer Service tra gli oggetti RegionOfInterest
     *                   e gli oggetti DAO corrispettivi
     */
    public EdgeService(SQLiteEdgeDao sqliteEdge, RemoteEdgeDao remoteEdge,
                       SQLiteEdgeTypeDao sqliteEdgeType, RemoteEdgeTypeDao remoteEdgeType,
                       PhotoService photoService, RegionOfInterestService roiService) {

        sqliteEdgeDao = sqliteEdge;
        sqliteEdgeTypeDao = sqliteEdgeType;
        remoteEdgeDao = remoteEdge;
        remoteEdgeTypeDao = remoteEdgeType;
        this.photoService = photoService;
        this.roiService = roiService;
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto EdgeTable, che verrà inserito nel
     * database locale
     * @param object Oggetto JsonObject che contiene le informazioni di un Edge
     */
    public void convertAndInsert(JsonObject object) {
        EdgeTable table = remoteEdgeDao.fromJSONToTable(object);
        int i = sqliteEdgeDao.insertEdge(table);
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto EdgeTypeTable, che verrà inserito
     * nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di un tipo di Edge
     */
    public void convertAndInsertEdgeType(JsonObject object) {
        EdgeTypeTable table = remoteEdgeTypeDao.fromJSONToTable(object);
        int i = sqliteEdgeTypeDao.insertEdgeType(table);
    }

    /**
     * Metodo per la conversione di un JsonObject in un oggetto PhotoTable, che verrà inserito
     * nel database locale
     * @param object Oggetto JsonObject che contiene le informazioni di una foto
     */
    public void convertAndInsertPhoto(JsonObject object) {
        photoService.convertAndInsert(object);
    }

    /**
     * Metodo per rimuovere un Edge dal database locale
     * @param edge L'Edge da rimuovere
     */
    public void deleteEdge(EnrichedEdge edge) {
        // recupero dall'Edge tutte le sue fotografie
        Collection<PhotoRef> refs = edge.getPhotoInformation().getPhotoInformation();
        Iterator<PhotoRef> iter = refs.iterator();

        // scorro la collection e le elimino tutte dal database locale
        while(iter.hasNext()) {
            PhotoRef ref = iter.next();
            photoService.deletePhoto(ref.getId());
        }

        // elimino l'edge dal database locale
        sqliteEdgeDao.deleteEdge(edge.getId());
    }

    /**
     * Metodo per recuperare le informazioni di tutti gli Edge di un edificio, dato il major
     * dell'edificio
     * @param major Major dell'edificio
     * @return  Collection<EnrichedEdge> Le informazioni su tutti gli Edge dell'edificio
     */
    public Collection<EnrichedEdge> findAllEdgesOfBuilding(int major) {
        Collection<EdgeTable> tables = sqliteEdgeDao.findAllEdgesOfBuilding(major);
        Iterator<EdgeTable> iter = tables.iterator();
        List<EnrichedEdge> edges = new LinkedList<>();
        while(iter.hasNext()) {
            EdgeTable table = iter.next();
            EnrichedEdge edge = fromTableToBo(table);
            edges.add(edge);
        }
        return edges;
    }

    /**
     * Metodo per recuperare un Edge ricercandolo nel database locale
     * @param id Identificativo numerico dell'Edge da ricercare
     * @return  EnrichedEdge L'Edge richiesto
     */
    public EnrichedEdge findEdge(int id) {
        EdgeTable table = sqliteEdgeDao.findEdge(id);
        return fromTableToBo(table);
    }

    /**
     * Metodo per la costruzione di oggetto EnrichedEdge a partire da un EdgeTable
     * @param edgeTable Oggetto contenente le informazioni di un Edge
     * @return  EnrichedEdge L'Edge costruito dopo aver recuperato tutte le informazioni necessarie
     */
    private EnrichedEdge fromTableToBo(EdgeTable edgeTable) {
        // informazioni recuperate da EdgeTable
        int id = edgeTable.getId();
        double distance = edgeTable.getDistance();
        String coordinate_s = edgeTable.getCoordinate();
        System.out.println(coordinate_s);
        int coordinate = Integer.parseInt(coordinate_s);
        int startROIid = edgeTable.getStartROI();
        int endROIid = edgeTable.getEndROI();
        String action = edgeTable.getAction();
        String longDescription = edgeTable.getLongDescription();
        int typeId = edgeTable.getTypeId();

        // creo le due RegionOfInterest a partire dai due id
        Collection<RegionOfInterest> tracedRois = roiService.getTracedRois();
        Iterator<RegionOfInterest> iter = tracedRois.iterator();
        RegionOfInterest startROI = null;
        RegionOfInterest endROI = null;
        RegionOfInterest actualRoi;
        while (iter.hasNext()) {
            actualRoi = iter.next();
            if(actualRoi.getId() == startROIid)
                startROI = actualRoi;
            else if(actualRoi.getId() == endROIid)
                endROI = actualRoi;
        }

        // recupero il nome del tipo di Edge
        EdgeTypeTable table = sqliteEdgeTypeDao.findEdgeType(typeId);
        String typeName = table.getTypeName();

        // recupero le informazioni per costruire le NavigationInformation
        BasicInformation basicInfo = new BasicInformation(action);
        DetailedInformation detailedInfo = new DetailedInformation(longDescription);
        PhotoInformation photoInfo = new PhotoInformation(photoService.findAllPhotosOfEdge(id));

        // costruisco le NavigationInformation
        NavigationInformation navInfo = new NavigationInformationImp(basicInfo, detailedInfo,
                photoInfo);

        // costruisco e ritorno l'edge del tipo specificato
        switch (typeName) {
            case "elevator":
                return new ElevatorEdge(startROI, endROI, distance, coordinate, id, navInfo);
            case "stairs":
                return new StairEdge(startROI, endROI, distance, coordinate, id, navInfo);
            default:
                return new DefaultEdge(startROI, endROI, distance, coordinate, id, navInfo);
        }
    }

}
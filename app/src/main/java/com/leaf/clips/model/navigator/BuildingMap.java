package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import java.util.Collection;

/**
 *Interfaccia che espone i metodi per l'accesso alle informazioni di un edificio
 */
public interface BuildingMap {

    /**
     * Metodo che ritorna l'indirizzo dell'edificio a cui l'oggetto è associato.
     * @return  String
     */
    String getAddress();

    /**
     * Metodo che ritorna un oggetto BuildingInformation contenente tutte le informazioni dell'edificio a cui è associato.
     * @return  BuildingInformation
     */
    BuildingInformation getAllBuildingInformation();

    /**
     * Metodo che ritorna la collezione di tutti gli archi previsti nella rappresentazione a grafo di un edificio.
     * @return  Collection<EnrichedEdge>
     */
    Collection<EnrichedEdge> getAllEdges();

    /**
     * Metodo che ritorna la collezione di tutti i POI presenti in un edificio.
     * @return  Collection<PointOfInterest>
     */
    Collection<PointOfInterest> getAllPOIs();

    /**
     * Metodo che ritorna una collezione di stringhe, eventualmente vuota, che rappresentano le categorie di appartenenza dei POI
     * @return  Collection<String>
     */
    Collection<String> getAllPOIsCategories();

    /**
     * Metodo che ritorna la collezione di tutti i ROI presenti in un edificio.
     * @return  Collection<RegionOfInterest>
     */
    Collection<RegionOfInterest> getAllROIs();

    /**
     * Metodo che ritorna una descrizione dell'edificio a cui l'oggetto è associato.
     * @return  String
     */
    String getDescription();

    /**
     * Metodo che l'identificativo numerico della mappa all'interno di un database.
     * @return  int
     */
    int getId();

    /**
     * Metodo che restituisce il nome dell'edificio a cui è associato tale oggetto.
     * @return  String
     */
    String getName();

    /**
     * Metodo che ritorna la collezione di POI associati alla ROI che contiene il beacon passato come argomento.
     * @param beacon Beacon associato alla RegionOfInterest di cui si vogliono conoscere l'insieme di POI che contiene.
     * @return  Collection<PointOfInterest>
     */
    Collection<PointOfInterest> getNearbyPOIs(MyBeacon beacon);

    /**
     * Metodo che ritornagli orari di apertura dell'edificio a cui l'oggetto è associato.
     * @return  String
     */
    String getOpeningHours();

    /**
     * Metodo che ritorna la dimensione della mappa dell'edificio (espressa in MB)
     * @return  String
     */
    String getSize();

    /**
     * Metodo che ritorna l'identificativo numerico della mappa.
     * @return  int
     */
    int getVersion();

    /**
     * Metodo che permette di cercare i POI di un edificio in cui nome contiene la stringa passata come parametro. Ritorna una collezione, eventualmente vuota,  di oggetti PointOfInterest nel cui nome contengono la stringa passata come parametro
     * @param name Stringa da cercare nei POI dell'edificio
     * @return  Collection<PointOfInterest>
     */
    Collection<PointOfInterest> searchPOIByName(String name);

}


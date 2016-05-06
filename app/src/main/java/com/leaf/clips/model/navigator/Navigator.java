package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.PointOfInterest;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;

import java.util.List;
import java.util.PriorityQueue;

/**
 *Interfaccia che espone i metodi per accedere alle funzionalità di navigazione
 */
public interface Navigator {

    /**
     * Metodo che calcola un percorso tra due RegionOfInterest viste come vertici di un grafo MapGraph utilizzando un oggetto PathFinder. Il punto di partenza e il punto di arrivo sono i parametri richiesti in input dal metodo mentre il grafo è un campo dati. Viene lanciata un eccezione di tipo NoGraphSetException nel caso in cui non sia settato alcun grafo.
     * @param startRoi Punto di partenza del percorso.
     * @param endPoi Punto di arrivo del percorso.
     * @return  void
     */
    void calculatePath(RegionOfInterest startRoi, PointOfInterest endPoi) throws NavigationExceptions;

    /**
     * Metodo che ritorna la lista completa delle ProcessedInstruction da seguire per percorrere un percorso calcolato.
     * @return  List<ProcessedInformation>
     */
    List<ProcessedInformation> getAllInstructions() throws NavigationExceptions;

    /**
     * Metodo per settare il grafo sul quale calcolare il percorso.
     * @param graph Grafo sul quale si vogliono calcolare dei percorsi.
     * @return  void
     */
    void setGraph(MapGraph graph);

    /**
     * Metodo che ritorna le informazioni da seguire per raggiungere la prossima RegionOfInterest. Le informazioni fornite dipendono dalla lista di beacon passata come parametro in ingrasso e dal beacon più potente tra quelli in essa contenuti.
     Viene lanciata una eccezione di tipo NoNavigationInformationException nel caso in cui si cerchi di accedere a tale metodo senza prima aver calcolato un percorso di navigazione.
     Viene lanciata una eccezione di tipo PathException nel caso in cui il beacon più potente nella lista di beacon in ingrasso sia associato ad una RegionOfInterest non appartenente ad una di quelle presenti nel percorso calcolato.
     * @param visibleBeacons Insieme di beacon visibili al momento della chiamata al metodo.
     * @return  ProcessedInformation
     */
    ProcessedInformation toNextRegion(PriorityQueue<MyBeacon> visibleBeacons) throws NavigationExceptions;

    /**
     * Metodo che ritorna un booleano false se il percorso è concluso
     * @return boolean
     */
    boolean hasFinishedPath();

}


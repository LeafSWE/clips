package com.leaf.clips.model.navigator.algorithm;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import java.util.List;

/**
 *Interfaccia che espone i metodi per calcolare un percorso di navigazione
 */
public interface PathFinder {

    /**
     * Metodo che calcola un percorso tra due RegionOfInterest viste come vertici di un grafo MapGraph. Il grafo, il punto di partenza e il punto di arrivo sono i parametri richiesti in input dal metodo.
     * @param graph Grafo sul quale calcolare il percorso.
     * @param startRoi Punto di partenza del percorso.
     * @param endRoi Punto di arrivo del percorso.
     * @return  List<EnrichedEdge>
     */
    List<EnrichedEdge> calculatePath(MapGraph graph, RegionOfInterest startRoi, RegionOfInterest endRoi);

}


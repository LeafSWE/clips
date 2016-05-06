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

import org.jgrapht.alg.DijkstraShortestPath;

import java.util.List;

/**
 *Classe che rappresenta un algoritmo per il calcolo del percorso di navigazione
 */
public class DijkstraPathFinder implements PathFinder {

    /**
     * Costruttore di default della classe DijkstraPathFinder
     */
    public DijkstraPathFinder() {
        //TODO: classe con costruttore vuoto ???
    }

    /**
     * Metodo che calcola un percorso tra due RegionOfInterest viste come vertici di un grafo
     * MapGraph. Il grafo, il punto di partenza e il punto di arrivo sono i parametri richiesti in
     * input dal metodo
     * @param graph Grafo sul quale calcolare il percorso
     * @param startROI RegionOfInterest di partenza del percorso. Deve appartenere al grafo passato
     *                 come paramentro.
     * @param endROI RegionOfInterest di arrivo del percorso. Deve appartenere al grafo passato come
     *               paramentro.
     * @return  List<EnrichedEdge>
     */
    @Override
    public List<EnrichedEdge> calculatePath(MapGraph graph, RegionOfInterest startROI,
                                            RegionOfInterest endROI) {
        return DijkstraShortestPath.findPathBetween(graph.getGraph(), startROI, endROI);
    }

}


package com.leaf.clips.model.navigator.graph.edge;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.vertex.Vertex;

/**
 *Interfaccia che serve per descrivere le funzionalità di un arco di un grafo
 */
public interface Edge {

    /**
     * Metodo che restituisce il Vertex di arrivo dell'arco
     * @return  Vertex
     */
    RegionOfInterest getEndPoint();

    /**
     * Metodo che ritorna l'identificativo numerico dell'arco
     * @return  int
     */
    int getId();

    /**
     * Metodo che restituisce il Vertex di partenza dell'arco
     * @return  Vertex
     */
    RegionOfInterest getStarterPoint();

    /**
     * Metodo che ritorna il peso dell'arco
     * @return  double
     */
    double getWeight();

}

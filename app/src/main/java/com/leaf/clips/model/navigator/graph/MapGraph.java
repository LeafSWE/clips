package com.leaf.clips.model.navigator.graph;
/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;
import com.leaf.clips.model.usersetting.Setting;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.Collection;

/**
 *Classe che rappresenta un grafo da utilizzare per il calcolo del percorso di navigazione
 */
public class MapGraph {

    /**
     * Rappresentazione a grafo dell'edificio
     */
    private SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge> graph;

    /**
     * Costruttore della classe
     */
    //TODO Aggiungere costruttore
    public MapGraph(){
        graph = new SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge>(EnrichedEdge.class);
    }


    /**
     * Metodo che permette di aggiungere più archi al grafo che rappresenta l'edificio
     * @param edges Archi da aggiungere al grafo che rappresenta l'edificio
     * @return void
     */
    public void addAllEdges(Collection<EnrichedEdge> edges) {
        for(EnrichedEdge edge: edges){
            graph.addEdge(edge.getStarterPoint(),edge.getEndPoint(),edge);
        }
    }

    /**
     * Metodo che permette di aggiungere più RegionOfInterest al grafo che rappresenta l'edificio
     * @param regions Collezione di RegionOfInterest da aggiungere al grafo che rappresenta l'edificio
     * @return void
     */
    public void addAllRegions(Collection<RegionOfInterest> regions) {
        for(RegionOfInterest regionOfInterest: regions){
            graph.addVertex(regionOfInterest);
        }
    }

    /**
     * Metodo che permette di aggiungere un arco al grafo che rappresenta l'edificio
     * @param edge Arco da aggiungere al grafo che rappresenta l'edificio
     * @return void
     */
    public void addEdge(EnrichedEdge edge) {
        graph.addEdge(edge.getStarterPoint(),edge.getEndPoint(),edge);
    }

    /**
     * Metodo che permette di aggiungere una RegionOfInterest al grafo che rappresenta l'edificio
     * @param roi RegionOfInterest da aggiungere al grafo che rappresenta l'edificio
     * @return void
     */
    public void addRegionOfInterest(RegionOfInterest roi) {
        graph.addVertex(roi);
    }

    /**
     * Metodo che permette di restituire il grafo che rappresenta la distribuzione
     * degli oggetti RegionOfInterest ed EnrichedEdge
     * @return SimpleDirectedWeightedGraph<RegionOfInterest,EnrichedEdge>
     */
    public SimpleDirectedWeightedGraph<RegionOfInterest, EnrichedEdge> getGraph() {
        return graph;
    }

    /**
     * Metodo che permette di impostare le setting passate come parametro a tutti gli edge all'interno del graph
     * @param setting Impostazioni di preferenza dell'applicazione
     */
    public void setSettingAllEdge(Setting setting) {
        // TODO: da testare
        Collection<EnrichedEdge> allEdges = graph.edgeSet();
        for (EnrichedEdge edge : allEdges) {
            edge.setUserPreference(setting);
        }
    }
}


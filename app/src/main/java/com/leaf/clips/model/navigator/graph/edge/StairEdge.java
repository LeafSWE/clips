package com.leaf.clips.model.navigator.graph.edge;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.navigationinformation.NavigationInformation;
import com.leaf.clips.presenter.MyApplication;

/**
 *Classe che implementa AbsEnrichedEdge e rappresenta un arco del grafo corrispondente ad una rampa di scale
 */
public class StairEdge extends AbsEnrichedEdge {

    /**
     * Costante per il calcolo del peso degli archi
     */
    private final static double STAIR_FACTOR = MyApplication.getConfiguration().getStairFactor();

    /**
     * Costruttore della classe StairEdge
     * @param startROI RegionOfInterest di partenza dell'arco
     * @param endROI RegionOfInterest di arrivo dell'arco
     * @param distance Lunghezza dell'arco
     * @param coordinate Angolo rispetto al Nord polare presente tra il punto iniziale e il punto finale dell'arco
     * @param id Identificativo numerico dell'arco
     * @param navInfo Informazioni di navigazione associate all'arco
     */
    public StairEdge(RegionOfInterest startROI, RegionOfInterest endROI, double distance, int coordinate, int id, NavigationInformation navInfo){
        super(startROI, endROI, distance, coordinate, id, navInfo);
    }

    /**
     * Metodo che ritorna le informazioni dettagliate per attraversare l'arco
     * @return  String
     */
    @Override
    public String getBasicInformation(){
        return getNavigationInformation().getBasicInformation();
    }

    /**
     * Metodo che ritorna le informazioni dettagliate per attraversare l'arco
     * @return  String
     */
    @Override
    public String getDetailedInformation(){
        return getNavigationInformation().getDetailedInformation();
    }

    /**
     * Metodo che ritorna il peso dell'arco calcolato in base al tipo e alle preferenze dell'utente
     * @return  double
     */
    @Override
    public double getWeight(){
        return Math.exp( getDistance() - STAIR_FACTOR )  + maxDistance * userStairPreference;
    }

}

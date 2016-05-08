package com.leaf.clips.model.navigator.graph.area;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import java.util.ArrayList;
import java.util.Collection;

/**
 *Classe che rappresenta un POI, ossia un punto all'interno di un edificio ritenuto di possibile interesse
 */

public class PointOfInterestImp implements PointOfInterest {

    /**
     * Identificativo numerico di un PointOfInterestImp
     */
    private final int id;

    /**
     * Informazioni relative ad un PointOfInterestImp
     */
    private final PointOfInterestInformation info;

    /**
     * Collezione degli oggetti RegionOfInterest alle quali appartiene l'oggetto
     */
    private Collection<RegionOfInterest> rois;

    /**
     * Costruttore della classe PointOfInterestImp
     * @param id  Identificativo numerico della classe PointOfInterestImp.
     * @param info Informazioni relative ad un POI
     */
    public PointOfInterestImp(int id , PointOfInterestInformation info){
        this.id = id;
        this.info = info;
    }

    /**
     * Metodo che ritorna la collezione di RegionOfInterest alle quali tale oggetto appartiene
     * @return  Collection<RegionOfInterest>
     */
    @Override
    public Collection<RegionOfInterest> getAllBelongingROIs(){
        return new ArrayList<RegionOfInterest>(this.rois);
    }

    /**
     * Metodo che ritorna il nome della categoria di appartenenza del PointOfInterestImp.
     * @return  String
     */
    @Override
    public String getCategory(){
        return this.info.getCategory();
    }

    /**
     * Metodo che ritorna una descrizione del PointOfInterestImp.
     * @return  String
     */
    @Override
    public String getDescription(){
        return this.info.getDescription();
    }

    /**
     * Metodo che ritorna l'identificativo numerico associato al PointOfInterestImp.
     * @return  int
     */
    @Override
    public int getId(){
        return this.id;
    }

    /**
     * Metodo che ritorna il nome associato al PointOfInterestImp
     * @return  String
     */
    @Override
    public String getName(){
        return this.info.getName();
    }

    /**
     * Metodo che permette di settare l'insieme di RegionOfInterest nelle quali tale PointOfInterestImp è contenuto
     * @param rois Insieme di RegionOfInterest alle quali appartiene il PointOfInterestImp.
     * @return  void
     */
    @Override
    public void setBelongingROIs(Collection<RegionOfInterest> rois){
        this.rois = new ArrayList<RegionOfInterest>(rois);
    }

}



package com.leaf.clips.model.navigator.graph.area;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import java.util.Collection;

/**
 *Interfaccia che rappresenta un'area di interesse all'interno di un edificio. Espone i metodi per accedere alle informazioni di quest'area e per settare a quali aree coperte dal segnale di beacon appartiene
 */
public interface PointOfInterest {

    /**
     * Metodo che ritorna la collezione di RegionOfInterest alle quali tale oggetto appartiene
     * @return  Collection<RegionOfInterest>
     */
    Collection<RegionOfInterest> getAllBelongingROIs();

    /**
     * Metodo che ritorna il nome della categoria di appartenenza del PointOfInterest
     * @return  String
     */
    String getCategory();

    /**
     * Metodo che ritorna una descrizione del PointOfInterest
     * @return  String
     */
    String getDescription();

    /**
     * Metodo che ritorna l'identificativo numerico associato al PointOfInterest
     * @return  int
     */
    int getId();

    /**
     * Metodo che ritorna il nome associato al PointOfInterest
     * @return  String
     */
    String getName();

    /**
     * Metodo che permette di settare l'insieme di RegionOfInterest nelle quali tale PointOfInterest è contenuto
     * @param rois Insieme di RegionOfInterest alle quali appartiene il PointOfInterest
     * @return  void
     */
    void setBelongingROIs(Collection<RegionOfInterest> rois);

}

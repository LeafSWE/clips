package com.leaf.clips.model.navigator.graph.area;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.beacon.MyBeacon;

import java.util.Collection;

/**
 *Interfaccia che serve per descrivere un'area coperta dal segnale di un beacon. Espone i metodi per accedere alle informazioni riguardanti a quale beacon è associata tale area, che POI appartengono a tale area e per impostare tali POI
 */
public interface RegionOfInterest {

    /**
     * Metodo utilizzato per verificare se il beacon passato come paramentro è il beacon associato alla RegionOfInterest
     * @param beacon Beacon di cui si vuole verificare l'associazione con la RegionOfInterest
     * @return  boolean
     */
    boolean contains(MyBeacon beacon);

    /**
     * Metodo che ritorna la collezione di PointOfInterest appartenenti a tale RegionOfInterest
     * @return  Collection<PointOfInterest>
     */
    Collection<PointOfInterest> getAllNearbyPOIs();

    /**
     * Metodo che ritorna il piano dell'edificio nel quale la ROI rappresentata da tale oggetto si trova
     * @return  int
     */
    int getFloor();

    /**
     * Metodo che ritorna l'identificativo numerico associato all'oggetto
     * @return  int
     */
    int getId();

    /**
     * Metodo che ritorna l'identificativo Major del beacon associato al ROI rappresentato da tale RegionOfInterest
     * @return  int
     */
    int getMajor();

    /**
     * Metodo che ritorna l'identificativo Minor del beacon associato al ROI rappresentato da tale RegionOfInterest
     * @return  int
     */
    int getMinor();

    /**
     * Metodo che ritorna l'identificativo UUID del beacon associato al ROI rappresentato da tale RegionOfInterest
     * @return  String
     */
    String getUUID();

    /**
     * Metodo utilizzato per settare l'insieme di PointOfInterest associato a tale RegionOfInterest
     * @param pois Insieme di PointOfInterest appartenenti alla RegionOfInterest
     * @return  void
     */
    void setNearbyPOIs(Collection<PointOfInterest> pois);

}

package com.leaf.clips.model.navigator.graph.edge;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.model.usersetting.Setting;

/**
 *Interfaccia che serve per rappresentare un arco completo delle informazioni che possono aiutare a superarlo. Questo può rappresentare per esempio un corridoio di un edificio. Quest'interfaccia quindi espone i metodi per accedere a tali informazioni
 */
public interface EnrichedEdge extends Edge {

    /**
     * Metodo astratto che ritorna le informazioni di base associate all'arco
     * @return  String
     */
    String getBasicInformation();

    /**
     * Metodo astratto che ritorna le informazioni di base associate all'arco
     * @return  String
     */
    String getDetailedInformation();

    /**
     * È stata restituita la lunghezza dell'arco
     * @return  double
     */
    double getDistance();

    /**
     * Metodo che ritorna la RegionOfInterest di arrivo dell'arco
     * @return  RegionOfInterest
     */
    RegionOfInterest getEndPoint();

    /**
     * Metodo che ritorna la un oggetto PhotoInformation contente un riferimento alle fotografie associate all'arco
     * @return  PhotoInformation
     */
    PhotoInformation getPhotoInformation();

    /**
     * Metodo che ritorna la RegionOfInterest di partenza dell'arco
     * @return  RegionOfInterest
     */
    RegionOfInterest getStarterPoint();

    /**
     * Metodo che ritorna le coordinate di un arco
     * @return  int
     */
    int getCoordinate();

    /**
     * Metodo che permette di impostare le preferenze di un utente per il calcolo del peso dell'arco
     * @param setting Preferenze da impostare
     * @return  void
     */
    void setUserPreference(Setting setting);

}
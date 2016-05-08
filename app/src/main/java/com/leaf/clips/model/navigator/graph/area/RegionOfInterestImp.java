package com.leaf.clips.model.navigator.graph.area;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.model.navigator.graph.vertex.VertexImp;

import java.util.ArrayList;
import java.util.Collection;

/**
 *Classe che rappresenta una ROI, area coperta da un beacon che può contenere uno o più POI. Implementa la classe VertexImp
 */
public class RegionOfInterestImp extends VertexImp implements RegionOfInterest {

    /**
     * Identificativo numerico di un oggetto RegionOfInterestImp
     */
    private final int id;

    /**
     * Identificativo Major del beacon associato alla ROI rappresentata dall'oggetto
     */
    private final int major;

    /**
     * Identificativo Minor del beacon associato alla ROI rappresentata dall'oggetto
     */
    private final int minor;

    /**
     * Collezione degli oggetti PointOfInterest appartenenti all'oggetto
     */
    private Collection<PointOfInterest> pois;

    /**
     * Identificativo UUID del beacon associato alla ROI rappresentata dall'oggetto
     */
    private final String uuid;

    /**
     * Costruttore della classe RegionOfInterestImp
     * @param id  Identificativo numerico di un RegionOfInterestImp.
     * @param uuid Identificativo UUID del beacon associato alla ROI rappresentata dall'oggetto
     * @param major Identificativo Major del beacon associato alla ROI rappresentata dall'oggetto
     * @param minor Identificativo Minor del beacon associato alla ROI rappresentata dall'oggetto
     */
    public RegionOfInterestImp(int id , String uuid, int major, int minor){
        super(id);
        this.id = id;
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
    }

    /**
     * Metodo utilizzato per verificare se il beacon passato come paramentro è il beacon associato alla RegionOfInterest
     * @param beacon Beacon di cui si vuole verificare l'associazione con la RegionOfInterest
     * @return  boolean
     */
    @Override
    public boolean contains(MyBeacon beacon){
        if((beacon.getMajor() == this.major) && (beacon.getMinor() == this.minor) && (beacon.getUUID().equals(this.uuid)))
            return true;
        else
            return false;
    }

    /**
     * Metodo che ritorna la collezione di PointOfInterest appartenenti a tale RegionOfInterestImp
     * @return  Collection<PointOfInterest>
     */
    @Override
    public Collection<PointOfInterest> getAllNearbyPOIs(){
        return new ArrayList<>(this.pois);
    }

    /**
     * Metodo che ritorna il piano dell'edificio nel quale la ROI rappresentata da tale oggetto si trova
     * @return  int
     */
    @Override
    public int getFloor() {
        return this.minor / 1000;
    }

    /**
     * Metodo che ritorna l'identificativo Major del beacon associato al ROI rappresentato da tale RegionOfInterestImp
     * @return  int
     */
    @Override
    public int getMajor() {
        return this.major;
    }

    /**
     * Metodo che ritorna l'identificativo Minor del beacon associato al ROI rappresentato da tale RegionOfInterestImp
     * @return  int
     */
    @Override
    public int getMinor() {
        return this.minor;
    }

    /**
     * Metodo che ritorna l'identificativo UUID del beacon associato al ROI rappresentato da tale RegionOfInterestImp
     * @return  String
     */
    @Override
    public String getUUID() {
        return this.uuid;
    }

    /**
     * Metodo utilizzato per settare l'insieme di PointOfInterest associato a tale RegionOfInterestImp
     * @return  void
     */
    @Override
    public void setNearbyPOIs(Collection<PointOfInterest> pois) {
        this.pois = new ArrayList<>(pois);
    }

}


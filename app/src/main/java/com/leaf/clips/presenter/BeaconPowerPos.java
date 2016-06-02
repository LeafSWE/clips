package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

//TODO: astah - aggiungere classe
/**
 * Classe che permette di rapperentare un Beacon con posizione,minor e valore RSSI associato
 */
public class BeaconPowerPos extends BeaconPos {
    /**
     * Valore RSSI associato. Valore di default 0.
     */
    private int rssi=0;

    /**
     * Costruttore della classe
     * @param pos posizione del Beacon
     * @param minor minor del Beacon
     */
    BeaconPowerPos(float[] pos, int minor){
        super(pos,minor);
    }

    /**
     * Metodo che permette di ottenere il valore RSSI del Beacon
     * @return valore RSSI
     */
    public int getRssi() {
        return rssi;
    }

    /**
     * Metodo che permette di impostare il valore RSSI associato ad un Beacon
     */
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }
}

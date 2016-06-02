package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */


/**
 *Classe che permette di rappresentare la posizione di un Beacon in base al minor
 */
public class BeaconPos{

    /**
     * Rappresenta la posizione di un Beacon
     */
    private float[] position;

    /**
     * Rappresenta il minor di un Beacon
     */
    private int minor;

    /**
     * Costruttore della classe
     * @param pos posizione del Beacon
     * @param minor minor del Beacon
     */
    public BeaconPos(float[] pos, int minor){
        this.position=pos;
        this.minor=minor;
    }

    /**
     * Metodo che ritorna la posizione del Beacon
     */
    public float[] getPosition() {
        return position;
    }

    /**
     * Metodo che permette di settare la posizione del Beacon
     * @param position posizione del Beacon che deve essere impostata
     */
    public void setPosition(float[] position) {
        this.position = position;
    }

    /**
     * Metodo che ritorna il minor del Beacon
     */
    public int getMinor() {
        return minor;
    }

    /**
     * Metodo che permette di impostare il minor desiderato
     * @param minor minor del Beacon che deve essere impostato
     */
    public void setMinor(int minor) {
        this.minor = minor;
    }
}
package com.leaf.clips.model.beacon;

import java.io.Serializable;

/**
 * @author Federico Tavella
 * @version 1.00
 * @since 0.00
 */

/**
 * Interfaccia che espone tutti i metodi che possono essere invocati
 * su di un beacon gestito all’interno della nostra applicazione.
 * Estende l’interfaccia java.io.Serializable
 */
public interface MyBeacon  extends Serializable{
    /**
     * Metodo che ritorna l'identificativo UUID del beacon
     * @return String UUID del beacon rilevato
     */
    String getUUID();

    /**
     * Metodo che ritorna l’identificativo Major del beacon
     * @return int Major del beacon rilevato
     */
    int getMajor();
    /**
     * Metodo che ritorna l’identificativo Minor del beacon
     * @return int Minor del beacon rilevato
     */
    int getMinor();
    /**
     * Metodo permette di fornire una conversione di MyBeaconImp a tipo String
     * @return String Stringa rappresentante il beacon rilevato
     */
    String toString();
    /**
     * Metodo che ritorna l’indirizzo Bluetooth del beacon
     * @return String indirizzo bluetooth del beacon rilevato
     */
    String getBluetoothAddress();
    /**
     * Metodo che ritorna la distanza del beacon dal dispositivo che lo ha rilevato
     * @return double distanza del beacon dal dispositivo che lo ha rilevato
     */
    double getDistance();
    /**
     * Metodo che ritorna la potenza ricevuta del segnale del beacon
     * @return int potenza ricevuta del segnale del beacon
     */
    int getRssi();
    /**
     * Metodo che ritorna la potenza di trasmissione del beacon
     * @return int potenza di trasmissione del beacon
     */
    int getTxPower();
    /**
     * Metodo che ritorna il livello di batteria del beacon rilevato
     * @return long livello di batteria del beacon rilevato
     */
    long getBatteryLevel();
    /**
     * Metodo che ritorna il codice rappresentante il tipo di beacon che è stato rilevato
     * @return int codice rappresentante il tipo di beacon rilevato
     */
    int getBeaconTypeCode();
}

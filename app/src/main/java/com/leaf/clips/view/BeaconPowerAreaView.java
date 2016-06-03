package com.leaf.clips.view;

import com.leaf.clips.presenter.BeaconPowerPos;

import java.util.Collection;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

/**
 * Interfaccia che espone i metodi per poter aggiornare la mappa dei Beacon con il segnale rssi
 * rilevato
 */
public interface BeaconPowerAreaView {
    /**
     * Metodo che permette di impostare la mappa dei Beacon
     * @param map mappa dei Beacon
     */
    void setBeaconMap(Collection<BeaconPowerPos> map);
    /**
     * Metodo che permette di impostare la mappa dei Beacon rilevati con il segnale rssi aggiornato
     * @param updateMap mappa dei Beacon rilevati con valore rssi aggiornato
     */
    void setBeaconPowerMap(Collection<BeaconPowerPos> updateMap);
}

package com.leaf.clips.model.beacon;

import org.altbeacon.beacon.distance.DistanceCalculator;

/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

/**
 * Classe che implementa l’interfaccia org.altbeacon.beacon.distance.DistanceCalculator
 */

public class MyDistanceCalculator implements DistanceCalculator {

    /**
     * metodo calcola la distanza di un beacon dal dispositivo
     * @param i potenza del beacon
     * @param v rssi del beacon
     * @return double distanza del dispotivo dal beacon rilevato
     */
    @Override
    public double calculateDistance(int i, double v) {
        if (v == 0) {
            return -1.0; // if we cannot determine accuracy, return -1.
        }

        double ratio = v*1.0/i;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        }
        else {
            double accuracy = (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }
}


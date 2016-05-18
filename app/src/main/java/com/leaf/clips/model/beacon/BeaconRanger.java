package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 0.02
 * @since 0.00
 */

/**
 * Interfaccia che espone tutti i metodi che possono essere invocati
 * su di una classe che si occupa del rilevamento dei beacon
 */
public interface BeaconRanger {

    /**
     * Metodo per notificare al Service che l'applicazione sta andando in
     * background
     * @param mode Parametro che serve per impostare se l’applicazione sta andando in background o no
     */
    void setBackgroundMode(boolean mode);

    /**
     * Metodo per impostare il periodo che determina l’uscita di un
     * beacon da una Region
     * @param p Questo parametro richiede il periodo in millisecondi
     */
    void setRegionExitPeriod(long p);

    /**
     * Metodo che serve a modificare il periodo di scansione per il rilevamento
     * dei beacon
     * @param p Periodo di scansione
     * @param type Parametro per decidere se cambiare il periodo di scansione in Foreground o in Background,
     *             di scansione o di non scansione
     */
     void modifyScanPeriod(long p, PeriodType type);

    /**
     * Metodo che ritorna un valore booleano che indica se il BeaconManager è in background
     * @return boolean Valore booleano che indica se il BeaconManager è in background
     */

    boolean isBackground();

    /**
     * Metodo per ottenere uno tra i periodi di scan del BeaconManager in base alla richiesta
     * @param type Tipo di periodo di scan desiderato
     * @return long Periodo di scan desiderato
     */

    long getPeriod(PeriodType type);

}

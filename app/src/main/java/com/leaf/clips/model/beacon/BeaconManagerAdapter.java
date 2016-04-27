package com.leaf.clips.model.beacon;
/**
 * @author Federico Tavella
 * @version 0.01
 * @since 0.00
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import javax.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.startup.BootstrapNotifier;

import java.util.Collection;
import java.util.PriorityQueue;

/**
 * Classe che si occupa del rilevamento dei beacon. Estende la
 * classe android.app.Service e implementa le interfacce org.altbeacon.beacon.BeaconConsumer
 * e org.altbeacon.beacon.startup.BootstrapNotifier
 */
public class BeaconManagerAdapter extends Service implements BeaconConsumer, BootstrapNotifier, RangeNotifier, BeaconRanger {

    /**
     * Riferimento alla classe che permette di gestire il rilevamento dei beacon
     */
    private BeaconManager beaconManager;

    /**
     * Riferimento al LocalBinder che detiene il collegamento con il Service
     */
    private IBinder locBinder;

    /**
     * Rappresenta un criterio che serve ad eseguire il match con un beacon
     */
    private Region region;

    /**
     * Rappresenta una stringa che identifica la marca del Beacon o del protocollo
     */
    private static String beaconLayout = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"; //AltBeacon beacons

    /**
     * Metodo che inizializza i parametri della classe alla creazione di un’istanza
     */
    @Override
    public void onCreate(){
        super.onCreate();
        region = new Region("MyApplicationRegion", null, null, null);
        locBinder = new LocalBinder();
        beaconManager = BeaconManager.getInstanceForApplication(getApplicationContext());
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(beaconLayout));
        setMonitorNotifier(this);
        beaconManager.bind(this);
        BeaconManager.setRegionExitPeriod(4000);
        beaconManager.setForegroundBetweenScanPeriod(500);
        beaconManager.setForegroundScanPeriod(1000);
        beaconManager.setRangeNotifier(this);
    }

    /**
     * Metodo che esegue le azioni necessarie alla distruzione del Service
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        setMonitorNotifier(null);
        try {
            beaconManager.stopMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        beaconManager.getBeaconParsers().clear();
        beaconManager.unbind(this);
    }

    private void setMonitorNotifier(MonitorNotifier monitorNotifier) {
        beaconManager.setMonitorNotifier(monitorNotifier);
    }

    /**
     * Metodo per decidere come comportarsi alla connessione del Service
     */

    @Override
    public void onBeaconServiceConnect() {
        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo serve per far partire il Ranging dei Beacon
     */
    private void startRanging(){

        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che definisce delle azioni da eseguire nel momento
     * in cui il dispositivo rileva uno o più beacon nella Region
     * @param region Criterio che serve ad eseguire il match con un beacon
     */
    @Override
    public void didEnterRegion(Region region) {
        startRanging();
    }

    /**
     * Metodo che definisce delle azioni da eseguire nel momento
     * in cui il dispositivo non rileva più beacon nella Region
     * @param region Criterio che serve ad eseguire il match con un beacon
     */
    @Override
    public void didExitRegion(Region region) {
        try {
            beaconManager.stopRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che determina se un dispositivo è presente all'interno di una Region
     * @param i Stato della Region che può essere MonitorNotifier.INSIDE o MonitorNotifier.OUTSIDE
     * @param region Criterio che serve ad eseguire il match con un beacon
     */
    @Override
    public void didDetermineStateForRegion(int i, Region region) {
        //TO-DO
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
        if (collection.size() > 0) {
            PriorityQueue<MyBeacon> p = new PriorityQueue<>();

            p.clear();

            for (Beacon oneBeacon : collection) {
                p.add(new MyBeaconImp(oneBeacon));
            }

            Intent msg = new Intent("beaconsDetected"); //TO-DO
            msg.putExtra("beacons", p);
            LocalBroadcastManager.getInstance(BeaconManagerAdapter.this).sendBroadcast(msg);


        }
    }

    /**
     * Metodo che serve a definire determinate azioni nel momento
     * in cui una classe viene collegata ad un Service
     * @param intent Intent del Service di cui si vuole fare il collegamento
     * @return IBinder Binder per effettuare il bind con il Service
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return locBinder;
    }

    /**
     * Metodo per notificare al Service che l’applicazione sta andando in background
     * @param mode Parametro che serve per impostare se l’applicazione sta andando in background o no
     */
    @Override
    public void setBackgroundMode(boolean mode) {
        beaconManager.setBackgroundMode(mode);
    }

    /**
     * Metodo per impostare il periodo che determina l’uscita di un
     * beacon da una Region
     * @param p Questo parametro richiede il periodo in millisecondi
     */
    @Override
    public void setRegionExitPeriod(long p) {
        BeaconManager.setRegionExitPeriod(p);
    }

    /**
     * Metodo che serve a modificare il periodo di scansione per il rilevamento
     * dei beacon
     * @param p Periodo di scansione
     * @param type Parametro per decidere se cambiare il periodo di scansione in Foreground o in Background,
     *             di scansione o di non scansione
     */
    @Override
    public void modifyScanPeriod(long p, PeriodType type) {
        //TO-DO
        switch (type){
            case BACKGROUND_BETWEEN :
                beaconManager.setBackgroundBetweenScanPeriod(p);
                break;
            case BACKGROUND :
                beaconManager.setBackgroundScanPeriod(p);
                break;
            case FOREGROUND :
                beaconManager.setForegroundScanPeriod(p);
                break;
            case FOREGROUND_BETWEEN :
                beaconManager.setForegroundBetweenScanPeriod(p);
                break;

        }
        try {
            beaconManager.updateScanPeriods();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /**
     * Classe definita per permettere la comunicazione tra processi
     * (IPC), in questo caso permette di comunicare con i metodi pubblici
     * definiti internamente ad una classe Service. Estende la classe
     * android.os.Binder
     */
    public class LocalBinder extends Binder {
        /**
         * Metodo restituisce il riferimento al Service BeaconManagerAdapter
         * @return BeaconManagerAdapter Riferimento al BeaconManagerAdapter per invocare metodi pubblici
         */
        BeaconManagerAdapter getService() {
            return BeaconManagerAdapter.this;
        }
    }
}


package com.leaf.clips.presenter;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.InformationListener;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.view.BeaconPowerAreaView;
import com.leaf.clips.view.BeaconPowerAreaViewImp;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

import javax.inject.Inject;

/**
 * Classe che permette di creare un'Activity che è in grado di rilevare il segnale emesso dai Beacon
 * e visualizzarlo a video. Il segnale preso in considerazione è l'RSSI.
 */
public class BeaconPowerAreaActivity extends AppCompatActivity implements InformationListener{

    /**
     * View associata a tale Activity
     */
    private BeaconPowerAreaView view;

    /**
     * Mappa dei Beacon con relativo segnale e posizione
     */
    private Collection<BeaconPowerPos> map=new ArrayList<>();

    /**
     * Rappresenta lo stato di scansione. Il valore true indica che la scansione è attiva, false
     * non è attiva
     */
    private boolean scan=false;

    /**
     * Oggetto del Model per la gestione delle informazioni
     */
   @Inject
   InformationManager infoManager;

    /**
     * Metodo che viene invocato alla creazione dell'oggetto
     * @param savedInstanceState stato memorizzato dell'oggetto
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new BeaconPowerAreaViewImp(this);

        MyApplication.getInfoComponent().inject(this);

        buildMap();
        //Controllo che l'iniezione sia andata abuon fine
        Assert.assertNotNull(infoManager);
    }

    /**
     * Metodo che permette di avviare la scansione del segnale dei Beacon
     */
    public void startScan(){
        infoManager.startRecordingBeacons();
        infoManager.addListener(this);
        scan=true;
    }

    /**
     * Metodo che permette di terminare la scansione del segnale dei Beacon
     */
    public void stopScan(){
        infoManager.removeListener(this);
        scan=false;
    }

    /**
     * Metodo che permette di controllare lo stato della scansione. Se true indica che la scansione
     * è attiva, false indica che la scansione non è attiva
     */
    public boolean isScanning(){
        return scan;
    }

    /**
     * Metodo che permette di ottenere tutti i beacon rilevati e di creare una mappa che contiene i
     * Beacon rilevati e i relativi segnali
     * @param visibleBeacons lista di beacon rilevati
     */
    @Override
    public void getAllVisibleBeacons(PriorityQueue<MyBeacon> visibleBeacons) {
        Collection<BeaconPowerPos> updateMap = new ArrayList<>();
        for (BeaconPowerPos bp : map) {
            for (MyBeacon beacon : visibleBeacons) {
                if (bp.getMinor() == beacon.getMinor()){
                    bp.setRssi(beacon.getRssi());
                    updateMap.add(bp);
                }
            }
        }
        view.setBeaconPowerMap(updateMap);
    }

    /**
     * Metodo che permette di creare la mappa dei Beacon
     */
    public void buildMap(){
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        //rendere relativi i punti all'immagine
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.85f,(float)size.y*0.52f, 0.0f},0)); //Entrata A
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.15f,(float)size.y*0.52f,0.0f},1)); //Entrata B
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.15f,(float)size.y*0.08f,0.0f},2)); //Entrata C
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.85f,(float)size.y*0.08f,0.0f},3)); //Entrata D
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.75f,(float)size.y*0.475f,0.0f}, 1000)); //Region A
        map.add(new BeaconPowerPos(new float[]{(float) size.x*0.25f ,(float)size.y*0.475f ,0.0f},1001)); //Region B
        map.add(new BeaconPowerPos(new float[]{(float) size.x*0.25f ,(float)size.y*0.125f ,0.0f},1002)); //Region C
        map.add(new BeaconPowerPos(new float[]{(float) size.x*0.75f ,(float)size.y*0.125f , 0.0f}, 1003)); //Region D
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.95f,(float)size.y*0.52f,0.0f},1004)); //Scala A
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.05f,(float)size.y*0.52f,0.0f},1005)); // Scala B
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.05f,(float)size.y*0.08f,0.0f},1006)); // Scala C
        map.add(new BeaconPowerPos(new float[]{(float)size.x*0.95f,(float)size.y*0.08f, 0.0f}, 1007)); //Scala D
        view.setBeaconMap(map);
    }

    /**
     * Metodo che permette di fermare la scansione ogni volta che l'Activity entra nello stato
     * onPause(). Questo metodo viene chiamato ogni qualvolta viene richiamata un'altra Activity
     * o viene premuto il tasto di blocco del dispositivo
     */

    @Override
    public void onPause(){
        super.onPause();
        if(isScanning()) {
            stopScan();
            ((BeaconPowerAreaViewImp) view).changeIcon(1);
        }

    }

    /**
     * Metodo che viene invocato al caricamento del database. Non implementato
     */
    @Override
    public void onDatabaseLoaded() {
        // non implementato
    }

    /**
     * Metodo che viene invocato nel caso in cui la mappa non venga trovata in locale. Non implementato
     * @return false
     */
    @Override
    public boolean onLocalMapNotFound() {
        // non implementato
        return false;
    }

    /**
     * Metodo che viene invocato nel caso in cui la mappa non venga trovata nel database remoto. Non implementato
     */
    @Override
    public void onRemoteMapNotFound() {
        // non implementato
    }

    /**
     * Metodo che viene invocato nel caso in cui non si possano recuperare le informazioni di una mappa in remoto. Non implementato
     */
    @Override
    public void cannotRetrieveRemoteMapDetails() {
        // non implementato
    }

    /**
     * Metodo che viene invocato nel caso in cui la mappa presenta in locale non sia aggiornata. Non implementato
     * @return false
     */
    @Override
    public boolean noLastMapVersion() {
        // non implementato
        return false;
    }


}

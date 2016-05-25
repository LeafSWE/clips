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


public class BeaconPowerAreaActivity extends AppCompatActivity implements InformationListener{

    /**
     * View associata a tale Activity
     */
    private BeaconPowerAreaView view;
    private Collection<BeaconPowerPos> map=new ArrayList<>();
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

        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        buildMap();
        //Controllo che l'iniziezione sia andata abuon fine
        Assert.assertNotNull(infoManager);
    }


    public void startScan(){
        infoManager.startRecordingBeacons();
        infoManager.addListener(this);
        scan=true;
    }

    public void stopScan(){
        infoManager.removeListener(this);
        scan=false;
    }

    public boolean isScanning(){
        return scan;
    }


    /**
     * @inheritDoc
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
     * Metodo utilizzato per modificare il comportamento di default del tasto back
     */

    @Override
    public void onPause(){
        super.onPause();
        if(isScanning()) {
            stopScan();
            ((BeaconPowerAreaViewImp) view).changeIcon(1);
        }

    }

    @Override
    public void onDatabaseLoaded() {}

    @Override
    public boolean onLocalMapNotFound() {
        return false;
    }

    @Override
    public void onRemoteMapNotFound() {}

    @Override
    public void cannotRetrieveRemoteMapDetails() {}

    @Override
    public boolean noLastMapVersion() {
        return false;
    }


}

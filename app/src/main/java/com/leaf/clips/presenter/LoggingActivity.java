package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.leaf.clips.model.InformationListener;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.beacon.MyBeacon;
import com.leaf.clips.view.LoggingView;
import com.leaf.clips.view.LoggingViewImp;

import java.util.PriorityQueue;

import javax.inject.Inject;

public class LoggingActivity extends AppCompatActivity implements InformationListener  {
    /**
     * View associata a tale Activity 
     */
    private LoggingView view;

    @Inject
    InformationManager informationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new LoggingViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        informationManager.startRecordingBeacons();
        view.setBeaconListAdapter(new StringBuffer("Waiting for nearby beacons..."));

        informationManager.addListener(this);
    }


    /**
     * Metodo che viene utilizzato per interrompere l'attività di log
     * @return  void
     */
    public void stopLogging(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        informationManager.saveRecordedBeaconInformation(ts);
        Intent intent = new Intent(this, MainDeveloperActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public void getAllVisibleBeacons(PriorityQueue<MyBeacon> visibleBeacons) {
        StringBuffer beacons = new StringBuffer();

        for(MyBeacon beacon : visibleBeacons){
            beacons.append(beacon.toString());
            beacons.append("\n\n\n");
        }
        Log.e("BeaconSeen:", beacons.toString());
        view.setBeaconListAdapter(beacons);
    }

    @Override
    public void onBackPressed() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        informationManager.saveRecordedBeaconInformation(ts);
        Intent intent = getParentActivityIntent();
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                informationManager.saveRecordedBeaconInformation(ts);
                Intent intent = getParentActivityIntent();
                startActivity(intent);
                finish();
                return true;
        }
        return true;
    }
}

package com.leaf.clips.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.BeaconPowerAreaActivity;
import com.leaf.clips.presenter.BeaconPowerPos;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class BeaconPowerAreaViewImp implements BeaconPowerAreaView {
    /**
     * Bottone per interrompere un log in corso
     */
    private FloatingActionButton fab;
    /**
     * Presenter della View
     */
    private BeaconPowerAreaActivity presenter;

    //Questi due attributi possono essere spostati in BeaconPowerArea


    /**
     * Costruttore della classe LoggingViewImp
     * @param presenter Presenter della View che viene creata
     */
    public BeaconPowerAreaViewImp(final BeaconPowerAreaActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_beacon_power_area);
        fab = (FloatingActionButton) presenter.findViewById(R.id.start_scan_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!presenter.isScanning()) {
                    presenter.startScan();
                    changeIcon(0);
                } else {
                    presenter.stopScan();
                    changeIcon(1);
                }
            }
        });
    }
    public void changeIcon(int c){
        switch(c){
            case 0:
            fab.setImageResource(R.drawable.icon_stop_logging);
            break;
            case 1:
            fab.setImageResource(R.drawable.icon_play);
            break;
        }
    }
    public void setBeaconMap(Collection<BeaconPowerPos> map ){
        BeaconPowerArea.setMap(map);
    }

    public void setBeaconPowerMap(Collection<BeaconPowerPos> updateMap ){
        BeaconPowerArea.setUpdateMap(updateMap);
    }
    //E' possibile spostare questa classe altrove
}

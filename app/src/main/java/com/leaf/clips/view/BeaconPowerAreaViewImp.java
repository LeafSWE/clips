package com.leaf.clips.view;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.BeaconPowerAreaActivity;
import com.leaf.clips.presenter.BeaconPowerPos;

import java.util.Collection;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.00
 */

//TODO: astah - aggiungere classe
/**
 * Classe che implementa BeaconPowerView e fornisce una vista alla classe BeaconPowerActivity. Essa
 * è in grado di rappresentare una mappa dell'edificio in cui si sta navigando e l'area coperta dal
 * segnale dei beacon sottoforma di cerchio.
 */
public class BeaconPowerAreaViewImp implements BeaconPowerAreaView {
    /**
     * Bottone per avviare o interrompere la scansione del segnale dei Beacon
     */
    private FloatingActionButton fab;
    /**
     * Presenter della View
     */
    private BeaconPowerAreaActivity presenter;

    /**
     * Costruttore della classe BeaconPowerAreaViewImp
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

    /**
     * Metodo che permette di cambiare l'icona del pulsante che permette l'avvio o l'interruzione
     * della scansione dei Beacon
     */
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

    /**
     * Metodo che permette di impostare la mappa dei Beacon
     * @param map mappa dei Beacon
     */
    public void setBeaconMap(Collection<BeaconPowerPos> map ){
        BeaconPowerArea.setMap(map);
    }

    /**
     * Metodo che permette di impostare la mappa dei Beacon rilevati con il segnale rssi aggiornato
     * @param updateMap mappa dei Beacon rilevati con valore rssi aggiornato
     */
    public void setBeaconPowerMap(Collection<BeaconPowerPos> updateMap ){
        BeaconPowerArea.setUpdateMap(updateMap);
    }
}

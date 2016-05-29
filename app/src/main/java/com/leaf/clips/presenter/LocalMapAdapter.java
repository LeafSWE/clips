package com.leaf.clips.presenter;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */

/**
 * Si occupa del binding tra le informazioni di navigazione fornite dal Model e la View deputata
 * a mostrarle.
 */
public class LocalMapAdapter extends BaseAdapter{

    /**
     * Riferimentio all'activity che si occupa della sua gestione.
     */
    private LocalMapActivity presenter;

    /**
     * Collezione di BuildingTable contenente tutte le informazioni associate ad una certa mappa.
     */
    private Collection<BuildingTable> collectionBuildingTable;

    /**
     * Array contenente lo stato di ogni mappa installata. Se vero allora la mappa è da aggiornare, se falso non lo è.
     */
    private boolean [] mapsVersionStatus;

    /**
     * Costruttore della classe LocalMapAdapter
     * @param presenter Riferimento al model utile per avere anche il contesto dell'applicazione
     * @param collectionBuildingTable Insieme di mappe installate sul dispositivo
     * @param mapsVersionStatus Array contenente lo stato delle mappe
     */
    public LocalMapAdapter(LocalMapActivity presenter, Collection<BuildingTable> collectionBuildingTable, boolean [] mapsVersionStatus){
        this.presenter = presenter;
        this.collectionBuildingTable = collectionBuildingTable;
        this.mapsVersionStatus = mapsVersionStatus;
    }
    /**
     * Restituisce il numero di mappe installate nel telefono
     * @return int numero di mappe installate
     */
    @Override
    public int getCount() {
        return collectionBuildingTable.size();
    }

    /**
     * Restituisce la mappa della collezione che si trova nella posizione fornita come parametro.
     * @param position Posizione della mappa
     * @return Object mappa nella posizione selezionata
     */
    @Override
    public Object getItem(int position) {
        return collectionBuildingTable.toArray()[position];
    }

    /**
     * Restituisce l'id della mappa della collezione che si trova nella posizione fornita come parametro fornita come parametro.
     * @param position Posizione della mappa
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return collectionBuildingTable.toArray()[position].hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(presenter).inflate(R.layout.local_map_row, null);
        }

        final BuildingTable buildingTable = (BuildingTable) getItem(position);

        final boolean isBuildingMapUpdate = mapsVersionStatus[position];

        TextView txtViewName = (TextView) convertView.findViewById(R.id.textViewLocalMapName);
        txtViewName.setText(buildingTable.getName());

        TextView txtViewAddress = (TextView) convertView.findViewById(R.id.textViewLocalMapAddres);
        txtViewAddress.setText(buildingTable.getAddress());

        TextView txtViewMapVersion =  (TextView) convertView.findViewById(R.id.textViewLocalMapVersion);
        txtViewMapVersion.setText("v. " + String.valueOf(buildingTable.getVersion()));

        TextView txtViewMapSize = (TextView) convertView.findViewById(R.id.textViewLocalMapSize);
        txtViewMapSize.setText(buildingTable.getSize());

        TextView txtViewMapStatus = (TextView) convertView.findViewById(R.id.textViewMapStatus);


        AppCompatImageButton btnUpdateMap = (AppCompatImageButton) convertView.findViewById(R.id.updateLocalMap);
        AppCompatImageButton btnDeleteMap = (AppCompatImageButton) convertView.findViewById(R.id.removeLocalMap);

        if(isBuildingMapUpdate){
            txtViewMapStatus.setText(R.string.updated_map);
        }
        else {
            txtViewMapStatus.setText(R.string.update_map);
            txtViewMapStatus.setTextColor(Color.RED);
            txtViewMapVersion.setTextColor(Color.RED);
        }

        btnUpdateMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBuildingMapUpdate) {
                    showNoUpdateDialog();
                }
                else {
                    showUpdateDialog(buildingTable.getMajor());
                }
            }
        });

        btnDeleteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(presenter);
                builder1.setMessage(R.string.remove_map_question);
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                presenter.deleteMap(buildingTable.getMajor());
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return convertView;
    }

    /**
     * Metodo utilizzato per mostrare il dialog che indica che la mappa è già aggiornata
     */
    private void showNoUpdateDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(presenter);
        builder.setMessage(R.string.already_updated_map)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Metodo utilizzato per mostrare un dialog che chiede la conferma dell'utente per aggiornare la mappa
     * @param major parametro contente il major della mappa selezionata
     */
    private void showUpdateDialog (final int major) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(presenter);

        builder1.setMessage(R.string.update_map_question);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.updateMap(major);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}

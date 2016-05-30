package com.leaf.clips.presenter;

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
 * Si occupa del binding tra le mappe fornite dal Model e la View deputata
 * a mostrarle.
 */
public class RemoteMapManagerAdapter extends BaseAdapter {

    /**
     * Riferimentio all'activity che si occupa della sua gestione.
     */
    private RemoteMapManagerActivity presenter;

    /**
     * Collezione di BuildingTable contenente tutte le informazioni associate ad una certa mappa.
     */
    private Collection<BuildingTable> buildingTables;

    /**
     * Costruttore della classe RemoteMapAdapter
     * @param presenter Riferimento al presenter utile per avere anche il contesto dell'applicazione
     * @param buildingTables Insieme di mappe disponibili
     */
    public RemoteMapManagerAdapter(RemoteMapManagerActivity presenter, Collection<BuildingTable> buildingTables){
        this.presenter = presenter;
        this.buildingTables = buildingTables;
    }

    /**
     * Restituisce il numero di mappe disponibili
     * @return int numero di mappe disponibili
     */
    @Override
    public int getCount() {
        return buildingTables.size();
    }

    /**
     * Restituisce la mappa della collezione che si trova nella posizione fornita come parametro.
     * @param position Posizione della mappa
     * @return Object mappa nella posizione selezionata
     */
    @Override
    public Object getItem(int position) {
        return buildingTables.toArray()[position];
    }

    /**
     * Restituisce l'id della mappa della collezione che si trova nella posizione fornita come parametro fornita come parametro.
     * @param position Posizione della mappa
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return buildingTables.toArray()[position].hashCode();
    }

    /**
     * @inheritDoc
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(presenter).inflate(R.layout.remote_map_row, null);
        }

        final BuildingTable buildingTable = (BuildingTable) getItem(position);

        TextView textViewMapName = (TextView) convertView.findViewById(R.id.textViewRemoteMapName);
        textViewMapName.setText(buildingTable.getName());

        TextView textViewMapAddress = (TextView) convertView.findViewById(R.id.textViewRemoteMapAddress);
        textViewMapAddress.setText(buildingTable.getAddress());

        TextView textViewMapVersion = (TextView) convertView.findViewById(R.id.textViewRemoteMapVersion);
        textViewMapVersion.setText(String.valueOf("v. " + buildingTable.getVersion()));

        TextView textViewMapSize = (TextView) convertView.findViewById(R.id.textViewRemoteMapSize);
        textViewMapSize.setText(buildingTable.getSize());

        TextView textViewMapDescription = (TextView) convertView.findViewById(R.id.textViewRemoteMapDescription);
        textViewMapDescription.setText(buildingTable.getDescription());

        AppCompatImageButton btnUpdateMap = (AppCompatImageButton) convertView.findViewById(R.id.download_remote_map);

        btnUpdateMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.downloadMap(buildingTable.getMajor());
            }
        });

        return convertView;
    }
}

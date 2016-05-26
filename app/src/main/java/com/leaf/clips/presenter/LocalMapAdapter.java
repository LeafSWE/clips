package com.leaf.clips.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;

import java.io.IOException;
import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */


// TODO: 5/24/16 Asta + Tracy, classe nuova

public class LocalMapAdapter extends BaseAdapter{

    private Context context;

    private Collection<BuildingTable> collectionBuildingTable;

    private boolean [] mapsVersionStatus;

    private BuildingTable buildingTable;

    private Button btnUpdateMap;

    private Button btnDeleteMap;

    public LocalMapAdapter(Context context, Collection<BuildingTable> collectionBuildingTable, boolean [] mapsVersionStatus){
        this.context = context;
        this.collectionBuildingTable = collectionBuildingTable;
        this.mapsVersionStatus = mapsVersionStatus;
    }

    @Override
    public int getCount() {
        return collectionBuildingTable.size();
    }

    @Override
    public Object getItem(int position) {
        return collectionBuildingTable.toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return collectionBuildingTable.toArray()[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.local_map_row, null);
        }

        buildingTable = (BuildingTable) getItem(position);

        boolean buildingMapStatus = mapsVersionStatus[position];

        TextView txtViewName = (TextView) convertView.findViewById(R.id.textViewLocalMapName);
        txtViewName.setText(buildingTable.getName());

        TextView txtViewAddress = (TextView) convertView.findViewById(R.id.textViewLocalMapAddres);
        txtViewAddress.setText(buildingTable.getAddress());

        TextView txtViewMapVersion =  (TextView) convertView.findViewById(R.id.textViewLocalMapVersion);
        txtViewMapVersion.setText(buildingTable.getVersion());

        TextView txtViewMapSize = (TextView) convertView.findViewById(R.id.textViewLocalMapSize);
        txtViewMapSize.setText(buildingTable.getSize());

        TextView txtViewMapStatus = (TextView) convertView.findViewById(R.id.textViewMapStatus);

        if(buildingMapStatus){
            txtViewMapStatus.setText("Mappa aggiornata");
        }
        else {
            txtViewMapStatus.setText("Mappa da aggiornare");
        }

        btnUpdateMap = (Button) convertView.findViewById(R.id.updateLocalMap);
        btnDeleteMap = (Button) convertView.findViewById(R.id.removeLocalMap);

        btnUpdateMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 5/26/16 Aspetto il buildingManager
            }
        });

        btnDeleteMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 5/26/16 Aspetto il buildingManager
            }
        });

        return convertView;
    }
}

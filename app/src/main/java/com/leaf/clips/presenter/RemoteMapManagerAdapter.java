package com.leaf.clips.presenter;

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

// TODO: 5/27/16 Asta + Tracy, classe nuova 

public class RemoteMapManagerAdapter extends BaseAdapter {

    private RemoteMapManagerActivity presenter;

    private Collection<BuildingTable> buildingTables;

    private BuildingTable buildingTable;

    public RemoteMapManagerAdapter(RemoteMapManagerActivity presenter, Collection<BuildingTable> buildingTables){
        this.presenter = presenter;
        this.buildingTables = buildingTables;
    }

    @Override
    public int getCount() {
        return buildingTables.size();
    }

    @Override
    public Object getItem(int position) {
        return buildingTables.toArray()[position];
    }

    @Override
    public long getItemId(int position) {
        return buildingTables.toArray()[position].hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(presenter).inflate(R.layout.remote_map_row, null);
        }

        buildingTable = (BuildingTable) getItem(position);

        TextView textViewMapName = (TextView) convertView.findViewById(R.id.textViewRemoteMapName);
        textViewMapName.setText(buildingTable.getName());

        TextView textViewMapAddress = (TextView) convertView.findViewById(R.id.textViewRemoteMapAddress);
        textViewMapAddress.setText(buildingTable.getAddress());

        TextView textViewMapVersion = (TextView) convertView.findViewById(R.id.textViewRemoteMapVersion);
        textViewMapVersion.setText(String.valueOf("v. " + buildingTable.getVersion()));

        TextView textViewMapSize = (TextView) convertView.findViewById(R.id.textViewRemoteMapSize);
        textViewMapSize.setText(buildingTable.getSize());

        return convertView;
    }
}

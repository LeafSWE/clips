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


// TODO: 5/24/16 Asta + Tracy, classe nuova

public class LocalMapAdapter extends BaseAdapter{

    private LocalMapActivity presenter;

    private Collection<BuildingTable> collectionBuildingTable;

    private boolean [] mapsVersionStatus;

    private BuildingTable buildingTable;

    private AppCompatImageButton btnUpdateMap;

    private AppCompatImageButton btnDeleteMap;

    public LocalMapAdapter(LocalMapActivity presenter, Collection<BuildingTable> collectionBuildingTable, boolean [] mapsVersionStatus){
        this.presenter = presenter;
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
            convertView = LayoutInflater.from(presenter).inflate(R.layout.local_map_row, null);
        }

        buildingTable = (BuildingTable) getItem(position);

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


        btnUpdateMap = (AppCompatImageButton) convertView.findViewById(R.id.updateLocalMap);
        btnDeleteMap = (AppCompatImageButton) convertView.findViewById(R.id.removeLocalMap);

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
                    showUpdateDialog();
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
    
    private void showUpdateDialog () {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(presenter);

        builder1.setMessage(R.string.update_map_question);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.updateMap(buildingTable.getMajor());
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

package com.leaf.clips.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leaf.clips.model.dataaccess.dao.BuildingTable;
import com.leaf.clips.model.dataaccess.service.DatabaseService;

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

    private DatabaseService databaseService;

    public LocalMapAdapter(Context context, DatabaseService databaseService){
        this.context = context;
        this.databaseService = databaseService;
        collectionBuildingTable = databaseService.findAllBuildings();
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
        return null;
    }
}

package com.leaf.clips.view;

import android.widget.ListAdapter;

public interface HomeView {
    void setBuildingName(String name);
    void setBuildingDescription(String description);
    void setBuildingOpeningHours(String hours);
    void setBuildingAddress(String address);
    void setPoiCategoryListAdapter(ListAdapter adapter);
}

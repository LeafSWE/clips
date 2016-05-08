package com.leaf.clips.view;

import java.util.List;

public interface HomeView {
    void setBuildingName(String name);
    void setBuildingDescription(String description);
    void setBuildingOpeningHours(String hours);
    void setBuildingAddress(String address);
    void setPoiCategoryListAdapter(List<String> list);
}

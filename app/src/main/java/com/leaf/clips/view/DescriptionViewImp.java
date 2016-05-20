package com.leaf.clips.view;

import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiDescriptionActivity;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class DescriptionViewImp implements DescriptionView {

    private PoiDescriptionActivity presenter;

    public DescriptionViewImp(PoiDescriptionActivity presenter){
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_poi_description);
    }

    @Override
    public void setDescription(String description) {
        TextView detailedDesc = (TextView)presenter.findViewById(R.id.poi_description);
        if (detailedDesc != null) {
            detailedDesc.setText(description);
        }
    }
}

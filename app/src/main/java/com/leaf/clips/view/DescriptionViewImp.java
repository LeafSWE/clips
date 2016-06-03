package com.leaf.clips.view;

import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.PoiDescriptionActivity;

/**
 * @author Andrea Tombolato
 * @version 0.05
 * @since 0.02
 */

public class DescriptionViewImp implements DescriptionView {

    /**
     * Riferimento al presenter.
     */
    private PoiDescriptionActivity presenter;

    /**
     * Costruttore della classe DescriptionViewImp
     * @param presenter Presenter che si occupa del controllo dell'oggetto
     */
    public DescriptionViewImp(PoiDescriptionActivity presenter){
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_poi_description);
    }

    /**
     * Imposta l'identificativo del piano nel widget deputato a mostrarlo.
     *
     * @param ground identificativo del piano
     */
    @Override
    public void setGround(String ground) {
        TextView groundView = (TextView)presenter.findViewById(R.id.poi_ground);
        if (groundView != null) {
            groundView.setText(ground);
        }
    }

    /**
     * Imposta la categoria del POI nel widget deputato a mostrarlo.
     *
     * @param category categoria del POI
     */
    @Override
    public void setCategory(String category) {
        TextView catView = (TextView)presenter.findViewById(R.id.poi_category);
        if (catView != null) {
            catView.setText(category);
        }
    }

    /**
     * Imposta la descrizione del POI nel widget deputato a mostrarla.
     * @param description descrizione del POI
     */
    @Override
    public void setDescription(String description) {
        TextView detailedDesc = (TextView)presenter.findViewById(R.id.poi_description);
        if (detailedDesc != null) {
            detailedDesc.setText(description);
        }
    }
}

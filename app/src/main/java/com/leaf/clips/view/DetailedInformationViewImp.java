package com.leaf.clips.view;

import android.text.Html;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.presenter.DetailedInformationActivity;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */
public class DetailedInformationViewImp implements DetailedInformationView {
    DetailedInformationActivity presenter;

    public DetailedInformationViewImp(DetailedInformationActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_detailed_information);
    }

    @Override
    public void setPhoto(PhotoInformation photo) {

    }

    /**
     * Metodo che imposta una stringa formattata in HTML come testo della TextView dedicata a
     * mostrare l'istruzione dettagliata.
     * @param detailedInstr: Stringa contenente l'istruzione dettagliata formattata come HTML.
     */
    @Override
    public void setDetailedDescription(String detailedInstr) {
        TextView detailedDesc = (TextView)presenter.findViewById(R.id.detailed_description);
        //TODO: Inserire detailedInstr come HTML formatted già in DB
        detailedDesc.setText(Html.fromHtml(detailedInstr));
    }
}

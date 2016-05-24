package com.leaf.clips.view;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.text.Html;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;
import com.leaf.clips.presenter.DetailedInformationActivity;

/**
 * DetailedInformationViewImp si occupa di gestire direttamente i widget della UI deputati a mostrare
 * le informazioni dettagliate rispetto ad una certa istruzione di navigazione. Tali informazioni
 * comprendono le immagini del prossimo ROI da raggiungere ed i passi dettagliati da seguire.
 */
public class DetailedInformationViewImp implements DetailedInformationView {

    /**
     * Riferimento al relativo Presenter.
     */
    DetailedInformationActivity presenter;

    /**
     * Costruttore della classe DetailedInformationViewImp
     * @param presenter Presenter che si occupa del controllo dell'oggetto
     */
    public DetailedInformationViewImp(DetailedInformationActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_detailed_information);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setPhoto(PhotoInformation photo) {
        //TODO:
    }

    /**
     * Metodo che imposta una stringa formattata in HTML come testo della TextView dedicata a
     * mostrare l'istruzione dettagliata.
     * @param detailedInstr: Stringa contenente l'istruzione dettagliata formattata come HTML.
     */
    @Override
    public void setDetailedDescription(String detailedInstr) {
        TextView detailedDesc = (TextView)presenter.findViewById(R.id.detailed_description);
        if (detailedDesc != null) {
            detailedDesc.setText(Html.fromHtml(detailedInstr));
        }
    }
}

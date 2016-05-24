package com.leaf.clips.presenter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leaf.clips.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Una sottoclasse di {@link Fragment} che gestisce e mostra un immagine tra quelle relative ad
 * una certa istruzione di navigazione.
 */
public class ImageDetailFragment extends Fragment {

    /**
     * Numero dell'immagine, tra quelle associate ad una particolare istruzione di navigazione,
     * da mostrare.
     */
    private int mImageNum;

    /**
     * Riferimento al widget usato per mostrare l'immagine.
     */
    private ImageView mImageView;

    /**
     * Riferimento alla lista di URI delle immagini associate all'istruzione scelta.
     */
    private List<String> photoUris;

    /**
     * Costruttore di default
     */
    public ImageDetailFragment() {}

    /**
     * Usato per costruire un Fragment attraverso il passaggio di parametri. Best practice consigliata
     * dalla documentazione Android.
     * @param photoUris URI delle immagini associate all'istruzione scelta.
     * @param imageNum Numero dell'immagine, tra quelle associate ad una particolare istruzione di
     *                 navigazione, da mostrare.
     * @return istanza di ImageListFragment i cui campi dati sono stati inizializzati usando il
     * parametro passato.
     */
    public static ImageDetailFragment newInstance(ArrayList<String> photoUris,int imageNum) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt("image_position", imageNum);
        args.putStringArrayList("image_uris", photoUris);
        f.setArguments(args);
        return f;
    }

    /**
     * @inheritDoc
     * @param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt("image_position") : -1;
        photoUris = getArguments().getStringArrayList("image_uris");
    }

    /**
     * @inheritDoc
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String resUrl = photoUris.get(mImageNum);
        Picasso.with(getContext())
                .load(resUrl)
                .resize(1000,1000)
                .centerCrop()
                .placeholder(R.drawable.image_placeholder)
                .into(mImageView);
    }

}

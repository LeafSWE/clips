package com.leaf.clips.presenter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.leaf.clips.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Una sottoclasse di {@link Fragment} che gestisce e mostra le thumbnail delle immagini relative
 * ad una certa istruzione.
 */
public class ImageListFragment extends Fragment implements AdapterView.OnItemClickListener{
    /**
     * Riferimento all'Adapter utilizzato per mostrare le thumbnail.
     * {@link com.leaf.clips.presenter.ImageListFragment.ImageAdapter} è una classe interna
     * ad ImageListFragment.
     */
    private ImageAdapter imgAdapter;
    /**
     * Riferimento alla lista di URI delle immagini associate all'istruzione scelta.
     */
    private ArrayList<String> photoUris;

    /**
     * Costruttore di default. Richiesto dalla documentazione di {@link Fragment}.
     */
    public ImageListFragment() {}

    /**
     * Usato per costruire un Fragment attraverso il passaggio di parametri. Best practice consigliata
     * dalla documentazione Android.
     * @param urls URI delle immagini associate all'istruzione scelta.
     * @return istanza di ImageListFragment i cui campi dati sono stati inizializzati usando il
     * parametro passato.
     */
    public static ImageListFragment newInstance(ArrayList<String> urls) {
        final ImageListFragment f = new ImageListFragment();
        final Bundle args = new Bundle();
        args.putStringArrayList("urls", urls);
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
        imgAdapter = new ImageAdapter(getActivity());
    }

    /**
     * @inheritDoc
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        photoUris = getArguments().getStringArrayList("urls");
        final View v = inflater.inflate(R.layout.fragment_image_list, container, false);
        final GridView mGridView = (GridView) v.findViewById(R.id.gridView_images);
        mGridView.setAdapter(imgAdapter);
        mGridView.setOnItemClickListener(this);
        return v;
    }


    /**
     * Gestisce i tap dell'utente sulle thumbnail della lista, mostrando (a schermo intero)
     * l'immagine relativa alla thumbnail scelta.
     * @param parent View contenitore della lista di thumbnail.
     * @param view View relativa alla lista di thumnail.
     * @param position posizione nella lista della thumbnail scelta.
     * @param id ID della thumbnail scelta.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
        i.putExtra("image_selected", position);
        i.putStringArrayListExtra("photo_uris", photoUris);
        startActivity(i);
    }

    /**
     * Adapter custom che gestisce il binding tra la lista di URI e le relative thumbnail da
     * mostrare.
     */
    private class ImageAdapter extends BaseAdapter {
        /**
         * Contesto dell'applicazione.
         */
        private final Context mContext;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
        }

        /**
         * @return numero di item contenuti nella lista di thumbnail.
         */
        @Override
        public int getCount() {
            return photoUris.size();
        }

        /**
         * @param position poisizione di una thumbnail.
         * @return URI relativa alla thumbnail che si trova nella posizione passata come
         * parametro.
         */
        @Override
        public Object getItem(int position) {
            return photoUris.get(position);
        }

        /**
         * @param position poisizione di una thumbnail.
         * @return ID dell'URI relativa alla thumbnail che si trova nella posizione passata come
         * parametro.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * @inheritDoc
         */
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            Log.d("PHOTO_URI", photoUris.get(position));

            Picasso.with(getContext())
                    .load(photoUris.get(position))
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageView);
            return imageView;
        }
    }

}

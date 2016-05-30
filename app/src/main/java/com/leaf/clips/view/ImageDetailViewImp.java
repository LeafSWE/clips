package com.leaf.clips.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.leaf.clips.R;
import com.leaf.clips.presenter.ImageDetailActivity;
import com.leaf.clips.presenter.ImageDetailFragment;

import java.util.ArrayList;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

/**
 * ImageDetailViewImp si occupa di gestire direttamente i widget della UI deputati a mostrare
 * lo slideshow delle immagini relative ad una certa istruzione di navigazione.
 */
public class ImageDetailViewImp implements ImageDetailView{

    /**
     * Riferimento al relativo Presenter.
     */
    ImageDetailActivity presenter;

    /**
     * Riferimento al widget responsabile dello slideshow.
     */
    private ViewPager pager;

    /**
     * Costruttore della classe ImageDetailViewImp
     * @param presenter Presenter che ha il compito di controllare tale oggetto
     */
    public ImageDetailViewImp(ImageDetailActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_image_detail);

        pager = (ViewPager)presenter.findViewById(R.id.view_pager_photo);
    }

    /**
     * @inheritDoc
     * @param listLength numero delle immagini da mostrare.
     */
    @Override
    public void setAdapter(int listLength) {
        ImagePagerAdapter adapter =
                new ImagePagerAdapter(presenter, listLength);

        pager.setAdapter(adapter);

        int startItem = presenter.getIntent().getIntExtra("image_selected", -1);
        pager.setCurrentItem(startItem);
    }

    /**
     * ImagePagerAdapter si occupa di gestire lo slideshow.
     */
    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {

        /**
         * Numero di immagini da mostrare.
         */
        private final int mSize;

        /**
         * Riferimento al Presenter.
         */
        private ImageDetailActivity presenter;

        /**
         * Costruttore della classe ImagePagerAdapter
         * @param presenter Presenter che ha il compito di controllare tale oggetto
         * @param size Grandezza immagini
         */
        public ImagePagerAdapter(ImageDetailActivity presenter, int size) {
            super(presenter.getSupportFragmentManager());
            this.presenter = presenter;
            mSize = size;
        }

        /**
         * Restituisce il numero di immagini inserite nello slideshow.
         */
        @Override
        public int getCount() {
            return mSize;
        }

        /**
         * Restituisce l'immagine che, nella lista che compone lo slideshow, si trova nella
         * posizione passata come parametro.
         */
        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance((ArrayList<String>)presenter.getListPhotos(), position);
        }
    }
}

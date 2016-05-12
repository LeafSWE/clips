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
public class ImageDetailViewImp implements ImageDetailView{
    ImageDetailActivity presenter;
    private ViewPager pager;

    public ImageDetailViewImp(ImageDetailActivity presenter) {
        this.presenter = presenter;
        presenter.setContentView(R.layout.activity_image_detail);

        pager = (ViewPager)presenter.findViewById(R.id.view_pager_photo);
    }

    @Override
    public void setAdapter(int listLength) {
        ImagePagerAdapter adapter =
                new ImagePagerAdapter(presenter, listLength);

        pager.setAdapter(adapter);

        int startItem = presenter.getIntent().getIntExtra("image_selected", -1);
        pager.setCurrentItem(startItem);
    }

    public static class ImagePagerAdapter extends FragmentStatePagerAdapter {
        private final int mSize;
        private ImageDetailActivity presenter;

        public ImagePagerAdapter(ImageDetailActivity presenter, int size) {
            super(presenter.getSupportFragmentManager());
            this.presenter = presenter;
            mSize = size;
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public Fragment getItem(int position) {
            return ImageDetailFragment.newInstance((ArrayList)presenter.getListPhotos(), position);
        }
    }
}

package com.leaf.clips.presenter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoRef;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends Fragment implements AdapterView.OnItemClickListener{
    //TODO everything
    private ImageAdapter imgAdapter;
    List<PhotoRef> photoUris;

    public ImageListFragment() {}

    public static ImageListFragment newInstance(int[] urls) {
        final ImageListFragment f = new ImageListFragment();
        final Bundle args = new Bundle();
        args.putIntArray("urls", urls);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgAdapter = new ImageAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*TODO
        photoUris = getArguments().getIntArray("urls");
        final View v = inflater.inflate(R.layout.fragment_image_grid, container, false);
        final GridView mGridView = (GridView) v.findViewById(R.id.gridView);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        return v;
         */
        return null;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*final Intent i = new Intent(getActivity(), ImageDetailActivity.class);
        i.putExtra(ImageDetailActivity.EXTRA_IMAGE, position);
        startActivity(i);*/
    }

    private class ImageAdapter extends BaseAdapter {
        private final Context mContext;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
        }

        @Override
        public int getCount() {
            return photoUris.size();
        }

        @Override
        public Object getItem(int position) {
            return photoUris.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            ImageView imageView;
            if (convertView == null) { // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                /*imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new GridView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));*/
            } else {
                imageView = (ImageView) convertView;
            }
            /*Picasso.with(getContext())
                    .load(imageResUrls[position])
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.image_placeholder)
                    .into(imageView);*/
            return imageView;
        }
    }

}

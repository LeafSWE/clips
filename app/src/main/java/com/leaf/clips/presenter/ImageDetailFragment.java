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
 * A simple {@link Fragment} subclass.
 */
public class ImageDetailFragment extends Fragment {

    private int mImageNum;
    private ImageView mImageView;
    private List<String> photoUris;

    public static ImageDetailFragment newInstance(ArrayList<String> photoUris,int imageNum) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putInt("image_position", imageNum);
        args.putStringArrayList("image_uris", photoUris);
        f.setArguments(args);
        return f;
    }

    // Empty constructor, required as per Fragment docs
    public ImageDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageNum = getArguments() != null ? getArguments().getInt("image_position") : -1;
        photoUris = getArguments().getStringArrayList("image_uris");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // image_detail_fragment.xml contains just an ImageView
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.imageView);
        return v;
    }

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

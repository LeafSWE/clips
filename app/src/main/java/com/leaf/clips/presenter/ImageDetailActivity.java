package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.view.ImageDetailView;
import com.leaf.clips.view.ImageDetailViewImp;

import java.util.List;

public class ImageDetailActivity extends AppCompatActivity {
    private ImageDetailView view;
    private List<String> listPhotos;
    private int startItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new ImageDetailViewImp(this);

        listPhotos = getIntent().getStringArrayListExtra("photo_uris");
        startItem = getIntent().getIntExtra("image_selected",-1);

        view.setAdapter(listPhotos.size());
    }

    public List<String> getListPhotos() {
        return listPhotos;
    }
}

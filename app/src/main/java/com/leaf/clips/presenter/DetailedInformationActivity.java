package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.view.DetailedInformationView;
import com.leaf.clips.view.DetailedInformationViewImp;

public class DetailedInformationActivity extends AppCompatActivity {
    private DetailedInformationView view;
    private ProcessedInformation processedInfo;
    ImageListFragment imgListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new DetailedInformationViewImp(this);

    }

    public void updatePhoto(){
        //TODO
    }

    public void updateDetailedDescription(){
        //TODO
    }
}

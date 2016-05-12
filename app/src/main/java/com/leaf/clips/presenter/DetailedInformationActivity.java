package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.02
 * @since 0.00
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.R;
import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.navigator.ProcessedInformation;
import com.leaf.clips.view.DetailedInformationView;
import com.leaf.clips.view.DetailedInformationViewImp;

import java.util.ArrayList;

import javax.inject.Inject;

public class DetailedInformationActivity extends AppCompatActivity {
    /**
     * Riferimento utilizzato per accedere alle informazioni trattate dal model
     */
    @Inject
    InformationManager informationManager;
    @Inject
    NavigationManager navigationManager;

    private DetailedInformationView view;
    private ProcessedInformation processedInfo;
    private ImageListFragment imgListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new DetailedInformationViewImp(this);
        ((MyApplication)getApplication()).getInfoComponent().inject(this);

        ArrayList<String> uris = getIntent().getStringArrayListExtra("photo_uri");

        imgListFragment = ImageListFragment.newInstance(uris);

        updatePhoto();
        updateDetailedDescription();
    }

    public void updatePhoto(){
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,imgListFragment).commit();
    }

    public void updateDetailedDescription(){
        String detailedInfo = getIntent().getStringExtra("detailed_info");
        view.setDetailedDescription(detailedInfo);
    }
}

package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.usersetting.Setting;

import javax.inject.Inject;

public class MainDeveloperPresenter extends AppCompatActivity {

    @Inject
    Setting userSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 5/4/16 Integrate settings 
        if(true){
            startDeveloperOptions();
        }
        else
            startDeveloperUnlocker();

        ((MyApplication)getApplication()).getInfoComponent().inject(this);
    }

    public boolean isDeveloper(){
        //TODO
        return true;
    }

    public void startDeveloperUnlocker(){
        Intent intent = new Intent(this, DeveloperUnlockerActivity.class);
        startActivity(intent);
    }

    public void startDeveloperOptions(){
        Intent intent = new Intent(this, MainDeveloperActivity.class);
        startActivity(intent);
    }
}

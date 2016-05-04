package com.leaf.clips.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainDeveloperPresenter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: 5/4/16 Integrate settings 
        if(true){
            startDeveloperOptions();
        }
        else
            startDeveloperUnlocker();
    }

    public boolean isDeveloper(){
        //TODO
        return false;
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
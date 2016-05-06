package com.leaf.clips.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;


public class MainDeveloperActivity extends AppCompatActivity {

    private MainDeveloperView mainDeveloperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainDeveloperView = new MainDeveloperViewImp(this);

    }


    public void showDetailedLog(int logPosition){
        // TODO: 5/6/16 Passare la posizione del log nell'intent
        Intent intent = new Intent(this, LogInformationActivity.class);
        startActivity(intent);
    }

    public void startNewLog(){
        Intent intent = new Intent(this, LoggingActivity.class);
        startActivity(intent);
    }

}

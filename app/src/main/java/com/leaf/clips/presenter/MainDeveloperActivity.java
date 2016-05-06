package com.leaf.clips.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.leaf.clips.R;
import com.leaf.clips.view.MainDeveloperView;
import com.leaf.clips.view.MainDeveloperViewImp;

import java.util.ArrayList;

public class MainDeveloperActivity extends AppCompatActivity {

    private MainDeveloperView mainDeveloperView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainDeveloperView = new MainDeveloperViewImp(this);

    }


    public void showDetailedLog(int logPosition){
        Intent intent = new Intent(this, LogInformationActivity.class);
        startActivity(intent);
    }

    public void startNewLog(){
        //TODO
    }

}

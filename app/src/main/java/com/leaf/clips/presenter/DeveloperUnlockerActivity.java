package com.leaf.clips.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.view.DeveloperUnlockerView;
import com.leaf.clips.view.DeveloperUnlockerViewImp;

public class DeveloperUnlockerActivity extends AppCompatActivity {
    DeveloperUnlockerView developerUnlockerView;
    /**
     * TODO
     * Setting userSetting;
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        developerUnlockerView = new DeveloperUnlockerViewImp(this);
    }

    public boolean unlockDeveloper(String code){
        //TODO: implementazione deve essere rivista per interagire con Setting, questa è solo una prova.
        if(code.equals("dev")){
            Intent intent = new Intent(this, MainDeveloperActivity.class);
            startActivity(intent);
            return true;
        }
        else{
            developerUnlockerView.showWrongCode();
            return false;
        }
    }
}

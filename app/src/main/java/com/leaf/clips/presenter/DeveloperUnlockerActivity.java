package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.03
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.model.usersetting.Setting;
import com.leaf.clips.view.DeveloperUnlockerView;
import com.leaf.clips.view.DeveloperUnlockerViewImp;

import javax.inject.Inject;

public class DeveloperUnlockerActivity extends AppCompatActivity {

    DeveloperUnlockerView developerUnlockerView;

    @Inject
    Setting userSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        developerUnlockerView = new DeveloperUnlockerViewImp(this);

        ((MyApplication)getApplication()).getInfoComponent().inject(this);

    }

    public boolean unlockDeveloper(String code){
        if(userSetting.unlockDeveloper(code)){
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

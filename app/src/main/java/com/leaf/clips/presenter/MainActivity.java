package com.leaf.clips.presenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leaf.clips.R;

/**
 *  MainActivity ha l'unico compito di reindirizzare l'utente verso HomeActivity e fornire una
 *  schermata di caricamento in caso di computazioni pesanti all'avvio.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

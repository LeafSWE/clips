package com.leaf.clips.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.view.LogInformationView;
import com.leaf.clips.view.LogInformationViewImp;

public class LogInformationActivity extends AppCompatActivity {

    // TODO: 5/6/16 Aggiungere model
    /**
     * Oggetto del Model per la gestione dei log
     */
    //private InformationManager infoManager;

    /**
     * View associata a tale Activity
     */
    private LogInformationView view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO: Leggere il log passato dall'Intendt e passarlo alla view
        super.onCreate(savedInstanceState);
        this.view = new LogInformationViewImp(this);
    }

    /**
     * Metodo che viene utilizzato per rimuovere un log salvato
     * @param name Nome del log da eliminare
     * @return  void
     */
    public void deleteLog(String name){
        // TODO: 5/6/16 Codifiy
    }

}

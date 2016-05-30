package com.leaf.clips.view;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.LogInformationActivity;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public class LogInformationViewImp implements LogInformationView {
    /**
     * Bottone per rimuovere un log salvato 
     */
    private FloatingActionButton btnDeleteLog;

    /**
     * Presenter della View 
     */
    private LogInformationActivity presenter;

    /**
     * TextView all'interno della quale viene visualizzato il contenuto del log 
     */
    private TextView txtLog;

    /**
     * Costruttore della classe LogInformationViewImp
     * @param presenter Presenter della View che viene creata
     */
    public LogInformationViewImp(final LogInformationActivity presenter){

        //Associo il presenter
        this.presenter = presenter;

        //Setto il layout XML
        presenter.setContentView(R.layout.activity_log_information);
        Toolbar toolbar = (Toolbar) presenter.findViewById(R.id.toolbar);
        presenter.setSupportActionBar(toolbar);
        this.txtLog = (TextView) presenter.findViewById(R.id.logInformationTextView);

        //Setto il btnDelete + Listener
        this.btnDeleteLog = (FloatingActionButton) presenter.findViewById(R.id.fab_delete_log);
        this.btnDeleteLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        ActionBar actionBar = presenter.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Metodo utilizzato per visualizzare la lista delle informazioni di un certo beacon
     * @param logInfo Collegamento tra la lista delle informazione dei log e la view in cui esse devono essere mostrate
     */
    @Override
    public void setBeaconAdapter(String logInfo){
        txtLog.setText(logInfo);
    }

    /**
     * Metodo utilizzato per mostrare un dialog di conferma per poter cancellare un log
     */
    private void showAlertDialog () {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(presenter);
        builder1.setMessage(R.string.delete_log_question);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.deleteLog("Nome");
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();

        if(!presenter.isFinishing())
            alert11.show();
    }
}

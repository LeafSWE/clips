package com.leaf.clips.presenter;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.leaf.clips.R;
import com.leaf.clips.view.HelpView;
import com.leaf.clips.view.HelpViewImp;

/**
 *È una classe che estende AppCompactActivity che gestisce consente di gestire l'interazione tra HelpView ed il model
 */
public class HelpActivity extends AppCompatActivity {

    // TODO: 17/05/16 aggiungere tracy/uml se va bene
    /**
     * Stringa che rappresenta l'URL a cui è possibile recuperare la guida
     */
    private static final String helpUrl = "http://leafswe.github.io/";

    /**
     * View associata a tale Activity
     */
    private HelpView view;

    /**
     * Metodo che inizializza la HelpView
     * @param bundle Componente per salvare lo stato dell'applicazione
     */
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        view = new HelpViewImp(this);
        checkInternetConnection();
    }

    /**
     * Metodo che permette di controllare se la connessione internet è attiva. In caso affermativo
     * interroga la view per mostrare la guida, altrimenti viene mostrato un Alert che informa che
     * la connessione internet non è attiva
     */
    //TODO:astah, tracy fatto
    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            view.setHelp(helpUrl);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.no_connection_title_alert_help));
            builder.setMessage(getString(R.string.no_connection_message_alert_help));
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setPositiveButton(getString(R.string.retry_alert_help),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            checkInternetConnection();
                        }
                    });
            builder.setNegativeButton(getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    });
            builder.create().show();
        }
    }

}

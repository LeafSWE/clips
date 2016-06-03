package com.leaf.clips.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.leaf.clips.R;
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 * Classe per creare alert che avvisano che non è disponibile la connessione internet
 */
public class NoInternetAlert {

    /**
     * Metodo che controlla se presente la connessione internet. Se non presente viene mostrato un Alert
     * @param activity Contesto in cui deve essere mostrato l'Alert
     */
    public AlertDialog showIfNoConnection(Activity activity){
        AlertDialog alert = null;
        ConnectivityManager connectivityManager =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())){
            alert = alertSetUp(activity);
            if(!activity.isFinishing()) {
                alert.show();
            }
        }
        return alert;
    }

    /**
     * Metodo che permette di mostrare l'Alert senza controllare se la connessione internet sia attiva
     * @param activity Contesto in cui deve essere mostrato l'Alert
     */
    public AlertDialog show(Activity activity){
        AlertDialog alert = alertSetUp(activity);
        if(!activity.isFinishing()) {
            alert.show();
        }
        return alert;
    }

    /**
     * Metodo che si occupoa della creazione dell'Alert da mostrare
     * @param activity Contesto in cui deve essere mostrato l'Alert
     * @return AlertDialog
     */
    private AlertDialog alertSetUp(Activity activity) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
        alertBuilder.setTitle(R.string.no_connection_title_alert_help);
        alertBuilder.setMessage(R.string.navigation_internet_alert);
        alertBuilder.setPositiveButton(activity.getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //not implemented -> it is only an advise
                    }
                });
        return alertBuilder.create();
    }
}

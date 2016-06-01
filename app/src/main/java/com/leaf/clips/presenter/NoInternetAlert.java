package com.leaf.clips.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.leaf.clips.R;

// TODO: 22/05/16 aggiungere uml/tracy
/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */
public class NoInternetAlert {
    private static AlertDialog alert = null;

    public void showIfNoConnection(Activity presenter){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)presenter.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected())){
            if (alert == null) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(presenter);
                alertBuilder.setTitle(R.string.no_connection_title_alert_help);
                alertBuilder.setMessage(R.string.navigation_internet_alert);
                alertBuilder.setPositiveButton(presenter.getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //not implemented -> it is only an advise
                            }
                        });
                alert = alertBuilder.create();
            }
            if(!presenter.isFinishing())
                alert.show();
        }
    }
    public void show(Activity context){
        if (alert == null) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setTitle(R.string.no_connection_title_alert_help);
            alertBuilder.setMessage(R.string.navigation_internet_alert);
            alertBuilder.setPositiveButton(context.getResources().getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //not implemented -> it is only an advise
                        }
                    });
            alert = alertBuilder.create();
        }
        if(!context.isFinishing())
            alert.show();
    }
}

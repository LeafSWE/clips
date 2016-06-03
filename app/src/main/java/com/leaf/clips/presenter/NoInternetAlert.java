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
    public AlertDialog show(Activity activity){
        AlertDialog alert = null;
        alert = alertSetUp(activity);
        if(!activity.isFinishing()) {
            alert.show();
        }
        return alert;
    }

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

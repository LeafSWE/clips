package com.leaf.clips.presenter;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 *  Una MainActivity ha l'unico compito di reindirizzare l'utente verso HomeActivity e fornire una
 *  schermata di caricamento in caso di computazioni pesanti all'avvio.
 *  @see <a href="http://tinyurl.com/pjuc3bd">AppCompatActivity</a>
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Chiamato quando si sta avviando l'activity. Questo metodo si occupa di eventuali inizializzazioni
     * e di avviare HomeActivity.
     * @param savedInstanceState se l'Actvity viene re-inizializzata dopo essere stata chiusa, allora
     *                           questo Bundle contiene i dati più recenti forniti al metodo
     *                           <a href="http://tinyurl.com/acaw22p">onSavedInstanceState(Bundle)</a>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}

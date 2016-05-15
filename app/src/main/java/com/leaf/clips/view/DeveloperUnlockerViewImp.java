package com.leaf.clips.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leaf.clips.R;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

/**
 * Classe che si occupa di mostrare i campi utili all'inserimento del codice sviluppatore.
 * Mantiene i riferimenti agli elementi di layout che compongono la UI dell'inserimento. Tramite
 * questi riferimenti è possibile invocare i metodi propri dei vari elementi di layout. Ogni
 * bottone presente deve essere legato ad un oggetto anonimo di tipo View.OnClickListener, in modo
 * da poter reagire alla pressione su di esso
 */
public class DeveloperUnlockerViewImp implements DeveloperUnlockerView {

    /**
     * Presenter della View
     */
    private DeveloperUnlockerActivity developerUnlockerActivity;

    /**
     * EditText in cui è possibile inserire il codice sviluppatore
     */
    private EditText developerCode;

    /**
     * Bottone per confermare l'inserimento del codice sviluppatore
     */
    private Button btnInsertCode;

    /**
     * Costruttore della classe DeveloperUnlockerViewImp
     * @param developerUnlockerActivity Presenter che ha il compito di controllare l'oggetto
     */
    public DeveloperUnlockerViewImp(final DeveloperUnlockerActivity developerUnlockerActivity) {
        developerUnlockerActivity.setContentView(R.layout.activity_developer_unlocker);
        this.developerUnlockerActivity = developerUnlockerActivity;

        developerCode = (EditText)developerUnlockerActivity.findViewById(R.id.developer_code);

        btnInsertCode = (Button)developerUnlockerActivity.findViewById(R.id.developer_login_button);

        btnInsertCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insertedCode = developerCode.getText().toString();
                developerUnlockerActivity.unlockDeveloper(insertedCode);
            }
        });

    }

    /**
     * Metodo utilizzato per visualizzare un errore relativo all'errato inserimento del codice sviluppatore
     */
    @Override
    public void showWrongCode() {
        // Uso AlertDialog.Builder per una costruzione più sempre di AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(developerUnlockerActivity);
        builder.setTitle(R.string.dialog_title_dev_code_error)
                .setMessage(R.string.dialog_dev_code_error)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        // Create the AlertDialog object and return it
        builder.create().show();
    }
}

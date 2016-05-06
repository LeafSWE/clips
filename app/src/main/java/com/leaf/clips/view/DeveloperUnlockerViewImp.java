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
public class DeveloperUnlockerViewImp implements DeveloperUnlockerView {
    private DeveloperUnlockerActivity developerUnlockerActivity;
    private EditText developerCode;
    private Button btnInsertCode;

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

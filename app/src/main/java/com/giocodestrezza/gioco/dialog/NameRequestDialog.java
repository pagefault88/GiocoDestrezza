package com.giocodestrezza.gioco.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.os.Bundle;
import com.giocodestrezza.R;
import com.giocodestrezza.gioco.UserData;
import com.giocodestrezza.gioco.activity.AccellerometerGameActivity;

/**
 * Created by anton_000 on 01/04/2015.
 */
public class NameRequestDialog extends DialogFragment {


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.name_request_dialog_layout,null)).setPositiveButton("Inizia",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username;
                EditText  et  = (EditText)NameRequestDialog.this.getDialog().findViewById(R.id.dialog_name_field);
                username = et.getText().toString();
                UserData.initUserData(username);
                Intent intent = new Intent(getActivity(), AccellerometerGameActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("Indietro",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NameRequestDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}

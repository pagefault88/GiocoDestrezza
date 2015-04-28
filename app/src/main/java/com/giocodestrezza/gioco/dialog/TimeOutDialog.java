package com.giocodestrezza.gioco.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;

import com.giocodestrezza.R;
import com.giocodestrezza.gioco.UserData;
import com.giocodestrezza.gioco.activity.AccellerometerGameActivity;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class TimeOutDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.time_out_dialog_title);
        builder.setMessage(R.string.time_out_dialog_mess);
        builder.setPositiveButton(R.string.posite_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(),AccellerometerGameActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton(R.string.negative_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserData.deleteData();
                getActivity().finish();
                //Intent intent = new Intent(getActivity(), MainActivity.class);
                //startActivity(intent);
            }
        });
        return builder.create();
    }
}

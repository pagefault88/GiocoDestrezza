package com.giocodestrezza.gioco.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.giocodestrezza.R;
import com.giocodestrezza.gioco.UserData;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class BackButtonDialog extends DialogFragment {

    public static Class targetActivity;

    public static BackButtonDialog newInstance(Class c){
        BackButtonDialog backButtonDialog = new BackButtonDialog();
        targetActivity = c;

        return backButtonDialog;

    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.back_dialog_title);
        builder.setMessage(R.string.back_dialog_mess);
        builder.setPositiveButton(R.string.posite_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserData.deleteData();
                getActivity().finish();

            }
        }).setNegativeButton(R.string.negative_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                //getActivity().finish();
                //Intent intent = new Intent(getActivity(),targetActivity);
                //startActivity(intent);
            }
        });
        return builder.create();

    }


}

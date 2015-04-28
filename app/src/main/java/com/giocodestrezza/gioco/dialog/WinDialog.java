package com.giocodestrezza.gioco.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import com.giocodestrezza.R;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class WinDialog extends DialogFragment {

    private static Integer score;
    private static Class targetActivity;

    public static WinDialog newInstance(Class c,Integer s){
        WinDialog dialog = new WinDialog();

        score = s;
        targetActivity = c;

        return dialog;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.win_dialog_mes);
        builder.setMessage(score.toString());
        builder.setPositiveButton(R.string.win_dialog_btn_txt,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(),targetActivity);
                startActivity(intent);
            }
        });
        return builder.create();
    }
}

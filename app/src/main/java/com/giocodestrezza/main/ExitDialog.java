package com.giocodestrezza.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.*;
import android.os.Process;

import com.giocodestrezza.R;

/**
 * Created by anton_000 on 02/04/2015.
 */
public class ExitDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.exit_dialog_title);
        builder.setMessage(R.string.exit_dialog_mess);
        builder.setPositiveButton(R.string.posite_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
                android.os.Process.killProcess(Process.myPid());
                System.exit(0);
            }
        }).setNegativeButton(R.string.negative_button_text,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExitDialog.this.getDialog().cancel();
                //just test
            }
        });
        return builder.create();
    }
}

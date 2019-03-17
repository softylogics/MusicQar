package com.softylogic.musicQar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

/**
 * Created by Rehan on 31-Mar-17.
 */
public class InfoPopUpDialogueFragment extends DialogFragment {
    String message;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        message = bundle.getString("message");
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));
        builder.setTitle("Help");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}

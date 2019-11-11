package com.lovelycoding.automobile.ui.home.brand.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NetworkNotFound extends DialogFragment {

    Context context;
    public NetworkNotFound(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Please check your network connection !!");
        return alertDialog.create();
    }
}

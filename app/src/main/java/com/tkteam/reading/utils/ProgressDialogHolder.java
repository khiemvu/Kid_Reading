package com.tkteam.reading.utils;

import android.content.Context;

import com.tkteam.reading.ApplicationStateHolder;

/**
 * Created by Khiemvx on 8/17/2015.
 */
public class ProgressDialogHolder {
    private static ProgressDialogHolder instance;
    private CustomProgressDialog customProgressDialog;
    private Context context;

    private ProgressDialogHolder(Context context) {
        this.context = context;
    }

    public static ProgressDialogHolder getInstance() {
        Context context = ApplicationStateHolder.getInstance().getMyActivity();
        if (instance == null) {
            instance = new ProgressDialogHolder(context);
        }
        return instance;
    }

    public CustomProgressDialog getCustomProgressDialog() {
        return customProgressDialog;
    }

    public void showDialogWithoutMessage() {
        showDialog("");
    }


    public void showDialogWithMessage(String message) {
        showDialog(message);
    }


    private void showDialog(String message) {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
        customProgressDialog = CustomProgressDialog.show(context, "", true, false, null);
    }

    public void dismissDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }

}
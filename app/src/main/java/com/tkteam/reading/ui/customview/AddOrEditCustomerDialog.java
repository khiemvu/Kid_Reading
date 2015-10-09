package com.tkteam.reading.ui.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Khiemvx on 8/16/2015.
 */
public class AddOrEditCustomerDialog extends AlertDialog {
    public Context context;
    public int layout;
    private View rootView;

    public AddOrEditCustomerDialog(Context context) {
        super(context);
    }

    public AddOrEditCustomerDialog(Context context, int layout, int styleAnimation) {
        super(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(layout, null);
        setView(rootView);
        this.getWindow().getAttributes().windowAnimations = styleAnimation;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }
}


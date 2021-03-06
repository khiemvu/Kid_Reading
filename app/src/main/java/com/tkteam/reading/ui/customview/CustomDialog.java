package com.tkteam.reading.ui.customview;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Trung on 5/26/2015.
 */
public class CustomDialog extends AlertDialog {
    public Context context;
    public int layout;
    private View rootView;

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int layout, int styleAnimation) {
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

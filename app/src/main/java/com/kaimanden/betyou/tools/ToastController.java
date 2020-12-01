package com.kaimanden.betyou.tools;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.kaimanden.betyou.R;

public class ToastController {
    private View v;

    private ToastController(){}
    private static ToastController instance;

    public static ToastController init(View v){
        if (instance == null){
            instance = new ToastController();
        }
        instance.v = v;
        return instance;
    }

    private Snackbar make(String msg){
        return Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
    }

    public void showInfo(String msg){
        Snackbar snack = this.make(msg);
        snack.setBackgroundTint(v.getContext().getColor(R.color.info));
        snack.setTextColor(v.getContext().getColor(R.color.white));
        snack.show();
    }

    public void showError(String msg){
        Snackbar snack = this.make(msg);
        snack.setBackgroundTint(v.getContext().getColor(R.color.error));
        snack.setTextColor(v.getContext().getColor(R.color.white));
        snack.show();
    }
}

package com.kaimanden.betyou.tools;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
        Snackbar snack = Snackbar.make(v, msg, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        ViewGroup.LayoutParams params = view.getLayoutParams();

        if (params instanceof CoordinatorLayout.LayoutParams) {
            ((CoordinatorLayout.LayoutParams) params).gravity = Gravity.TOP;
        } else {
            ((FrameLayout.LayoutParams) params).gravity = Gravity.TOP;
        }
        snack.getView().setLayoutParams(params);
        return snack;
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

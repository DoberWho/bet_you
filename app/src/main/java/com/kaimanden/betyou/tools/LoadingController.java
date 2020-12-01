package com.kaimanden.betyou.tools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.kaimanden.betyou.R;

public class LoadingController {

    private LoadingController(){}
    private static LoadingController instance;
    private Activity act;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    public static LoadingController init( Activity act){
        if (instance == null){
            instance = new LoadingController();
        }
        instance.act = act;
        instance.createDialog();
        return instance;
    }

    private void createDialog() {
        builder = new AlertDialog.Builder(act);
        builder.setCancelable(false);
        View view = this.getView();
        builder.setView(view);
    }

    private View getView() {
        LayoutInflater inflater = act.getLayoutInflater();
        return inflater.inflate(R.layout.dialog_loading, null);
    }

    public void show(){
        dialog = builder.create();
        dialog.show();
    }

    public void hide(){
        if (dialog == null) return;
        if (!dialog.isShowing()) return;
        dialog.cancel();
    }
}

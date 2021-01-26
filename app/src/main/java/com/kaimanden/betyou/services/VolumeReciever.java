package com.kaimanden.betyou.services;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.main.MainActivity;
import com.kaimanden.betyou.tools.controllers.ToastController;

public class VolumeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = context.getString(R.string.mensaje_volumen);
        ToastController.init(null).toast(context, msg);

        Intent intent1 = new Intent(context, MainActivity.class);
        context.startActivity(intent1);
    }
}
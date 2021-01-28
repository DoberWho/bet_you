package com.kaimanden.betyou.services;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.main.MainActivity;
import com.kaimanden.betyou.tools.controllers.AuthController;
import com.kaimanden.betyou.tools.controllers.ToastController;

public class VolumeReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = context.getString(R.string.mensaje_volumen);
        ToastController.init(null).toast(context, msg);
        createNotif(context);
    }

    private void createNotif(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.ic_new_bet)
                .setContentTitle("HAS GANADO")
                .setContentText("Mira el resultado de tu apuesta")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, noif);
    }
}
package com.kaimanden.betyou.services;

import android.app.Notification;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.main.MainActivity;

public class NotificationJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w("NOTIF", "PASO POR AQUI, MIRA TU QUE GUAY");
        //createNotif();

        Context ctx = getApplicationContext();
        Intent intent = new Intent(ctx, AuthActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void createNotif() {

        Context ctx = getApplicationContext();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, "1")
                .setSmallIcon(R.drawable.ic_new_bet)
                .setContentTitle("Titulo")
                .setContentText("Esto es un titulo de una notificación de una aplicación android")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);

        notificationManager.notify(1, noif);
    }

}

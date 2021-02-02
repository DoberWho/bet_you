package com.kaimanden.betyou.main;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.services.NotificationJobService;
import com.kaimanden.betyou.services.VolumeReciever;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class MoneyFragment extends BaseFrg {

    private Button btn1, btn2, btn3,btn4, btn5, btn6, btn7, btn8, btn9;
    private boolean canShowNotif = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_money, container, false);
        createNotificationChannel();
        initViews(v);
        checkAllowNotifs();
        return v;
    }

    private void checkAllowNotifs() {

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String fileName = getString(R.string.sharedpreferences_file);
        Context ctx = getContext();
        SharedPreferences sharedPref = ctx.getSharedPreferences( fileName, Context.MODE_PRIVATE);
        int prefHour = sharedPref.getInt("list_preference_1", 0);

        if (prefHour >= hour){
            canShowNotif = true;
        }

    }

    private void initViews(View v) {
        btn1 = v.findViewById(R.id.frg_money_notif_1);
        btn2 = v.findViewById(R.id.frg_money_notif_2);
        btn3 = v.findViewById(R.id.frg_money_notif_3);
        btn4 = v.findViewById(R.id.frg_money_notif_4);
        btn5 = v.findViewById(R.id.frg_money_notif_5);
        btn6 = v.findViewById(R.id.frg_money_notif_6);
        btn7 = v.findViewById(R.id.frg_money_notif_7);
        btn8 = v.findViewById(R.id.frg_money_notif_8);
        btn9 = v.findViewById(R.id.frg_money_notif_9);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchNotif1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchNotif2();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchNotif3();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchNotif4();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchAlarm1();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchJobService();
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif ) launchNotif1();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif )  launchNotif2();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( canShowNotif )  launchNotif3();
            }
        });


    }

    private void launchJobService() {
        Context ctx = getActivity();
        ComponentName serviceComponent = new ComponentName(ctx, NotificationJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency( 15 * 1000); // wait at least
        builder.setOverrideDeadline(20 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_METERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        //builder.setPeriodic(TimeUnit.SECONDS.toMillis(15), TimeUnit.SECONDS.toMillis(5));
        builder.setPersisted(true);

        JobScheduler jobScheduler = ctx.getSystemService(JobScheduler.class);
        JobInfo job = builder.build();
        jobScheduler.schedule(job);
    }

    private void launchNotif1() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_new_bet)
                .setContentTitle("Titulo")
                .setContentText("Esto es un titulo de una notificación de una aplicación android")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(1, noif);

    }

    private void launchNotif2() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_new_bet)
                .setContentTitle("Titulo")
                .setContentText("Texto Corto")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Esto es un titulo de una notificación de una aplicación android. Esto es un titulo de una notificación de una aplicación android. \n \n Esto es un titulo de una notificación de una aplicación android."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(2, noif);
    }

    private void launchNotif3() {

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("DO_LOGOUT", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle("Titulo")
                .setContentText("Clicka aqui anda...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(3, noif);
    }

    private void launchNotif4() {

        Intent iMain = new Intent(getActivity(), MainActivity.class);
        iMain.putExtra("DO_LOGOUT", true);
        iMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pMain = PendingIntent.getActivity(getActivity(), 0, iMain, 0);

        Intent intent = new Intent(getActivity(), VolumeReciever.class);
        intent.setAction("SEMANTIC_ACTION_CALL");
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent pInt = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "1")
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle("Titulo")
                .setContentText("Clicka aqui anda...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_new_bet, "Compro Oro1", pInt)
                .addAction(R.drawable.ic_new_bet, "Compro Oro2", pInt)
                .addAction(R.drawable.ic_new_bet, "Compro Oro3", pInt)
                .setContentIntent(pMain)
                .setAutoCancel(true);;

        Notification noif = builder.build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

        notificationManager.notify(3, noif);

    }

    private void launchAlarm1(){
        Context ctx = getActivity();

        Intent intent = new Intent(getActivity(), VolumeReciever.class);
        intent.setAction("SEMANTIC_ACTION_CALL");
        intent.putExtra(EXTRA_NOTIFICATION_ID, 0);

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pInt = PendingIntent.getService(ctx, 1, intent, PendingIntent.FLAG_NO_CREATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);
        long time =  calendar.getTime().getTime();// calendar.getTimeInMillis();
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pInt);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,5000 , pInt);
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "cnala";
            String description = "desc_ canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
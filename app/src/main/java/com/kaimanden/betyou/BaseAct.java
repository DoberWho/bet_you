package com.kaimanden.betyou;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.kaimanden.betyou.tools.EmergencyHandler;

import org.greenrobot.eventbus.EventBus;

public class BaseAct extends AppCompatActivity {

    private static EmergencyHandler exHandler;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        if(exHandler == null) exHandler = new EmergencyHandler(this);
        else exHandler.setActivity(this);

        if(Thread.getDefaultUncaughtExceptionHandler() != exHandler)
            Thread.setDefaultUncaughtExceptionHandler(exHandler);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}

package com.kaimanden.betyou.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.tools.events.BaseEvent;
import com.kaimanden.betyou.tools.EmergencyHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {/* Do something */};

}

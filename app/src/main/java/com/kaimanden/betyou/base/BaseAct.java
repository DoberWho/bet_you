package com.kaimanden.betyou.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.tools.LoadingController;
import com.kaimanden.betyou.tools.events.BaseEvent;
import com.kaimanden.betyou.tools.EmergencyHandler;
import com.kaimanden.betyou.tools.events.LoadingEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseAct extends AppCompatActivity {

    private static EmergencyHandler exHandler;

    private boolean active = false;
    private LoadingController loadingCtrl;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        loadingCtrl = LoadingController.init(this);

        getSupportActionBar().hide();

        if(exHandler == null) exHandler = new EmergencyHandler(this);
        else exHandler.setActivity(this);

        if(Thread.getDefaultUncaughtExceptionHandler() != exHandler)
            Thread.setDefaultUncaughtExceptionHandler(exHandler);

    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
        EventBus.getDefault().unregister(this);
    }

    public void showLoading(){
        loadingCtrl.show();
    }

    public void hideLoading(){
        loadingCtrl.hide();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {/* Do something */};

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoadingEvent event) {
        if (!active) return;
        if (event.showLoading){
            showLoading();
        }else{
            hideLoading();
        }
    };

}

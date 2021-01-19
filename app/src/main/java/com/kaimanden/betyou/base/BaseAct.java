package com.kaimanden.betyou.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.tools.LoadingController;
import com.kaimanden.betyou.tools.ToastController;
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

        try {
            getSupportActionBar().hide();
        }catch(Exception e){
            try{
                getActionBar().hide();
            }catch (Exception ex){
                Log.e("ActionBar", ex.getLocalizedMessage());
            }
        }

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

    public void showError(View v, String msg){
        ToastController.init(v).showError(msg);
    }

    public void showInfo(View v, String msg){
        ToastController.init(v).showInfo(msg);
    }

    public void hideKeyb(){

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

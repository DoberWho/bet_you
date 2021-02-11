package com.kaimanden.betyou.tools.controllers;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kaimanden.betyou.base.BaseAct;

import java.net.InterfaceAddress;

public class EmergencyHandler implements Thread.UncaughtExceptionHandler {

    private BaseAct activity;
    private Thread.UncaughtExceptionHandler defaultUEH;

    public EmergencyHandler(BaseAct activity) {
        this.activity = activity;
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void setActivity(BaseAct activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("ERROR", ex.getLocalizedMessage());
        ex.printStackTrace();
        defaultUEH.uncaughtException(thread, ex);
    }



    private void test(){

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.activity);
        
        Bundle bundle = new Bundle();
        bundle.putString("asfasfasf", id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

    }
}
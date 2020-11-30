package com.kaimanden.betyou.tools;

import android.util.Log;

import com.kaimanden.betyou.BaseAct;

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
}
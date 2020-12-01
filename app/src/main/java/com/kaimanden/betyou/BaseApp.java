package com.kaimanden.betyou;

import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.hawk.Hawk;

public class BaseApp extends Application {



	@Override
	public void onCreate() {
	    super.onCreate();
		Hawk.init(this).build();

	}



	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onLowMemory() {
	    super.onLowMemory();
	}
}
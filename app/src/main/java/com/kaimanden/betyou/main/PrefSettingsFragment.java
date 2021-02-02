package com.kaimanden.betyou.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.kaimanden.betyou.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class PrefSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        PreferenceManager manager = getPreferenceManager();
        String fileName = getString(R.string.sharedpreferences_file);
        manager.setSharedPreferencesName(fileName);

        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    private void shared(){

        String fileName = getString(R.string.sharedpreferences_file);
        Context ctx = getContext();
        SharedPreferences sharedPref = ctx.getSharedPreferences( fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        // Editamos aqui
        editor.commit();

        Resources res = getResources();
        boolean bNotif = res.getBoolean(R.bool.preferences_notifications_default);
        sharedPref.getBoolean("notifications",bNotif);



    }
}
package com.kaimanden.betyou.main;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.kaimanden.betyou.R;

public class PrefSettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
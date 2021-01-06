package com.kaimanden.betyou.main;

import android.content.Intent;
import android.os.Bundle;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.R;

public class HomeFragment extends BaseFrg {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_home, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

        FloatingActionButton btn = v.findViewById(R.id.frg_home_settings);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAction();
            }
        });


    }

    private void doAction() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
        startActivityForResult(intent, 0);
    }
}
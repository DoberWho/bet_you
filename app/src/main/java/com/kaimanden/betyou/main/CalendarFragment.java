package com.kaimanden.betyou.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseFrg;

public class CalendarFragment extends BaseFrg {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_calendar, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {

    }
}
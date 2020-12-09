package com.kaimanden.betyou.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseFrg;

import java.util.Date;

public class BetFragment extends BaseFrg {

    private EditText edtName, edtDesc;
    private CalendarView calendar;
    private Button btnAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_bet, container, false);
        initViews(v);
        initButtons();
        return v;
    }

    private void initViews(View v) {
        edtName = v.findViewById(R.id.frg_bet_name_edt);
        edtDesc = v.findViewById(R.id.frg_bet_desc_edt);

        calendar = v.findViewById(R.id.frg_bet_calendar);
        btnAction = v.findViewById(R.id.frg_bet_action_btn);
    }

    private void initButtons() {
        Date minDate = new Date();
        calendar.setMinDate(minDate.getTime());
    }
}
package com.kaimanden.betyou.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.main.betcreate.BetCreateActivity;
import com.kaimanden.betyou.tools.DbController;
import com.kaimanden.betyou.tools.models.BetItem;

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

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBet();
            }
        });
    }

    private void createBet() {
        String title = edtName.getText().toString().trim();
        String desc  = edtDesc.getText().toString().trim();

        if (title.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtName.setError(error);
            return;
        }
        if (desc.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtDesc.setError(error);
            return;
        }

        long betTime = calendar.getDate();

        BetItem bet = new BetItem();
        bet.setBetTime(betTime);
        bet.setTitle(title);
        bet.setDesc(desc);

        DbController.init(getActivity()).saveBetItem(bet);

        /**
        Intent intent = new Intent(getActivity(), BetCreateActivity.class);
        intent.putExtra(BetCreateActivity.BETITEM, bet);
        startActivity(intent);

         //*/
    }
}
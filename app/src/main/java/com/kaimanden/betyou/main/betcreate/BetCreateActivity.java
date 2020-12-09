package com.kaimanden.betyou.main.betcreate;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseAct;

public class BetCreateActivity extends BaseAct {

    public static final String BETITEM = "bet item to create";

    private ImageButton btnMinus, btnPlus;
    private EditText edtMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bet_create);
        initViews();
        initButtons();
    }

    private void initViews() {
        btnMinus = findViewById(R.id.act_bet_create_minus);
        btnPlus  = findViewById(R.id.act_bet_create_plus);
        edtMoney = findViewById(R.id.act_bet_create_money);
    }

    private void initButtons() {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMoney(-1);
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMoney(1);
            }
        });
    }

    private void changeMoney(int increase) {
        String strMoney = edtMoney.getText().toString().trim();
        if (strMoney.isEmpty()){
            strMoney = "0";
        }
        Integer value = Integer.valueOf(strMoney);
        if (value == null) {
            value = 0;
        }
        value +=increase;

        Integer minValue = getResources().getInteger(R.integer.min_value);
        Integer maxValue = getResources().getInteger(R.integer.max_value);

        if (value < minValue){
            value = minValue;
        }
        if (value > maxValue){
            value = maxValue;
        }
        edtMoney.setText(""+value);

    }
}
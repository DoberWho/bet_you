package com.kaimanden.betyou.main.betcreate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kaimanden.betyou.R;

public class BetCreateActivity extends AppCompatActivity {

    public static final String BETITEM = "bet item to create";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bet_create);
    }
}
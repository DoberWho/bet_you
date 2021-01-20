package com.kaimanden.betyou.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.controllers.AuthController;

public class MainActivity extends BaseAct {

    private BaseFrg currentFrg = null;
    private BaseFrg frgHome = new HomeFragment();
    private BaseFrg frgMoney = new MoneyFragment();
    private BaseFrg frgBet = new BetFragment();
    private BaseFrg frgCalendar = new CalendarFragment();
    private BaseFrg frgSettings = new SettingsFragment();
    private ImageButton btnHome, btnCalendar, btnNewBet, btnSettings, btnListBet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkAuth();
        this.checkFrg();
        this.initViews();
        this.initButtons();
    }

    private void initViews() {
        btnHome     = findViewById(R.id.act_main_menu_home);
        btnCalendar = findViewById(R.id.act_main_menu_calendar);
        btnNewBet   = findViewById(R.id.act_main_menu_bet);
        btnSettings = findViewById(R.id.act_main_menu_settings);
        btnListBet  = findViewById(R.id.act_main_menu_money);

    }

    private void initButtons() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(frgHome);
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(frgCalendar);
            }
        });
        btnNewBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(frgBet);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(frgSettings);
            }
        });
        btnListBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(frgMoney);
            }
        });
    }

    private void checkFrg() {
        if (currentFrg == null){
            currentFrg = frgHome;
        }
    }

    private void checkAuth() {
        if (AuthController.init(this).isLoged()){
            return;
        }
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void changeFragment(BaseFrg frg){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.act_main_container, frg, "menu_fragment");
        trans.commit();
        currentFrg = frg;
    }

}
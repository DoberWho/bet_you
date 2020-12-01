package com.kaimanden.betyou.main;

import android.content.Intent;
import android.os.Bundle;

import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.AuthController;

public class MainActivity extends BaseAct {

    private BaseFrg currentFrg;
    private BaseFrg frgHome = new HomeFragment();
    private BaseFrg frgMoney = new HomeFragment();
    private BaseFrg frgBet = new HomeFragment();
    private BaseFrg frgCalendar = new HomeFragment();
    private BaseFrg frgSettings = new HomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkAuth();
        this.checkFrg();
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
}
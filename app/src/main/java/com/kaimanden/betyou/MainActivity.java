package com.kaimanden.betyou;

import android.content.Intent;
import android.os.Bundle;

import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.tools.AuthController;

public class MainActivity extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkAuth();
    }

    private void checkAuth() {

        if (!AuthController.init(this).isLoged()){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
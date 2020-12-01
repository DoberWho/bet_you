package com.kaimanden.betyou;

import android.content.Intent;
import android.os.Bundle;

import com.kaimanden.betyou.auth.AuthActivity;
import com.kaimanden.betyou.tools.models.User;
import com.orhanobut.hawk.Hawk;

public class MainActivity extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.checkAuth();
    }

    private void checkAuth() {
        User user = Hawk.get("user",null);
        if (user == null){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
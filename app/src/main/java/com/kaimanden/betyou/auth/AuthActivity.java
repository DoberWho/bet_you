package com.kaimanden.betyou.auth;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kaimanden.betyou.BaseAct;
import com.kaimanden.betyou.MainActivity;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.events.AuthEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AuthActivity extends BaseAct {

    private Fragment frgLogin    = new LoginFragment();
    private Fragment frgRecovery = new RecoveryFragment();
    private Fragment frgregister = new RegisterFragment();

    private Fragment currentFrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_auth);
        initViews();
        initFrg();
    }

    private void initFrg() {
        if (currentFrg == null){
            currentFrg = frgLogin;
            changeFragment(currentFrg);
        }
    }

    private void initViews() {



    }

    private void changeFragment(Fragment frg){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.replace(R.id.act_auth_container, frg, "auth_fragment");
        trans.commit();
        currentFrg = frg;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AuthEvent event) {
        switch (event.frgType){
            case LOGIN_OK:
            case REGISTER_OK:
                goToMain();
                break;
            case RECOVERY:
                changeFragment(frgRecovery);
                break;
            case REGISTER:
                changeFragment(frgregister);
                break;
            default:
            case LOGIN:
                changeFragment(frgLogin);
                break;
        }
    };
}
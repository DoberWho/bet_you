package com.kaimanden.betyou.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.kaimanden.betyou.R;
import com.kaimanden.betyou.events.AuthEvent;

import org.greenrobot.eventbus.EventBus;


public class RegisterFragment extends Fragment {

    private Button btnLogin, btnRegister, btnRecovery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_register, container, false);
        initViews(v);
        initButtons();
        return v;
    }


    private void initViews(View v) {
        btnLogin    = v.findViewById(R.id.frg_recovery_login);
        btnRegister = v.findViewById(R.id.frg_recovery_register);
        btnRecovery = v.findViewById(R.id.frg_recovery_recovery);
    }

    private void initButtons() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthEvent event = new AuthEvent(AuthEvent.FrgType.LOGIN);
                sendEvent(event);
            }
        });
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthEvent event = new AuthEvent(AuthEvent.FrgType.REGISTER);
                sendEvent(event);
            }
        });
    }

    private void sendEvent(AuthEvent event){
        EventBus.getDefault().post(event);
    }

}
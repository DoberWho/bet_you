package com.kaimanden.betyou.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kaimanden.betyou.BaseFrg;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.events.AuthEvent;


public class RecoveryFragment extends BaseFrg {

    private Button btnLogin, btnRegister, btnRecovery;
    private EditText edtEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_recovery, container, false);
        initViews(v);
        initButtons();
        return v;
    }

    private void initViews(View v) {
        btnLogin    = v.findViewById(R.id.frg_recovery_login);
        btnRegister = v.findViewById(R.id.frg_recovery_register);
        btnRecovery = v.findViewById(R.id.frg_recovery_recovery);

        edtEmail = v.findViewById(R.id.frg_recovery_email);
    }

    private void initButtons() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFormData();
            }
        });
        btnRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent(AuthEvent.FrgType.RECOVERY);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent(AuthEvent.FrgType.REGISTER);
            }
        });
    }

    private void checkFormData() {
        String email = edtEmail.getText().toString();
        if (email.trim().isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtEmail.setError(error);
            return;
        }

    }


}
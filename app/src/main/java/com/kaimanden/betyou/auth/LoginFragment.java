package com.kaimanden.betyou.auth;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.AuthController;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.ToastController;
import com.kaimanden.betyou.tools.events.AuthEvent;
import com.kaimanden.betyou.tools.listeners.AuthListener;

public class LoginFragment extends BaseFrg {

    private Button btnLogin, btnRegister, btnRecovery;
    private EditText edtEmail,edtPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_login, container, false);
        initViews(v);
        initButtons();
        return v;
    }

    private void initViews(View v) {
        btnLogin    = v.findViewById(R.id.frg_login_login);
        btnRegister = v.findViewById(R.id.frg_login_register);
        btnRecovery = v.findViewById(R.id.frg_login_recovery);

        edtEmail = v.findViewById(R.id.frg_login_email);
        edtPass  = v.findViewById(R.id.frg_login_pass);
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

        String pass = edtPass.getText().toString().trim();
        if (pass.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtPass.setError(error);
            return;
        }

        ((BaseAct)getActivity()).showLoading();
        AuthController.init(getActivity()).login(email, pass, new AuthListener() {
            @Override
            public void isOk(FirebaseUser user) {
                String msg = getString(R.string.request_login_ok);
                showInfo(msg);
                showLoading(true);
                sendEvent(AuthEvent.FrgType.LOGIN_OK);
            }

            @Override
            public void isKo(String error) {
                showLoading(false);
                showError(error);
            }
        });
    }

}
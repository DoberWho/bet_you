package com.kaimanden.betyou.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.AuthController;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.ToastController;
import com.kaimanden.betyou.tools.events.AuthEvent;
import com.kaimanden.betyou.tools.listeners.AuthListener;


public class RegisterFragment extends BaseFrg {

    private Button btnLogin, btnRegister, btnRecovery;
    private EditText edtEmail, edtPass, edtConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frg_register, container, false);
        initViews(v);
        initButtons();
        return v;
    }


    private void initViews(View v) {
        btnLogin    = v.findViewById(R.id.frg_register_login);
        btnRegister = v.findViewById(R.id.frg_register_register);
        btnRecovery = v.findViewById(R.id.frg_register_recovery);

        edtEmail = v.findViewById(R.id.frg_register_email);
        edtPass = v.findViewById(R.id.frg_register_pass);
        edtConfirm = v.findViewById(R.id.frg_register_passconfirm);
    }

    private void initButtons() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEvent(AuthEvent.FrgType.LOGIN);
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
                checkFormData();
            }
        });
    }

    private void checkFormData() {

        String email = edtEmail.getText().toString();
        if (email.trim().isEmpty()){
            String error = getString(R.string.error_empty_data);
            ToastController.init(getView()).showError(error);
            return;
        }

        String pass = edtPass.getText().toString().trim();
        if (pass.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtPass.setError(error);
            return;
        }

        String passConfirm = edtConfirm.getText().toString().trim();
        if (passConfirm.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtConfirm.setError(error);
            return;
        }

        if (!passConfirm.equals(pass)){
            String error = getString(R.string.error_pass_equals);
            edtConfirm.setError(error);
            return;
        }

        AuthController.init(getActivity()).register(email, pass, new AuthListener() {
            @Override
            public void isOk(FirebaseUser user) {
                String msg = getString(R.string.request_register_ok);
                showInfo(msg);
                sendEvent(AuthEvent.FrgType.REGISTER_OK);
            }

            @Override
            public void isKo(String error) {
                String msg = getString(R.string.request_register_ko);
                showError(msg);
            }
        });

    }

}
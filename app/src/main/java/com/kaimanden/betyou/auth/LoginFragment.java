package com.kaimanden.betyou.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.AuthController;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.ToastController;
import com.kaimanden.betyou.tools.events.AuthEvent;
import com.kaimanden.betyou.tools.listeners.AuthListener;

import org.greenrobot.eventbus.EventBus;


public class LoginFragment extends Fragment {

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
                AuthEvent event = new AuthEvent(AuthEvent.FrgType.RECOVERY);
                sendEvent(event);
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

        AuthController.init(getActivity()).login(email, pass, new AuthListener() {
            @Override
            public void isOk(FirebaseUser user) {
                String msg = getString(R.string.request_login_ok);
                ToastController.init(getView()).showInfo(msg);
            }

            @Override
            public void isKo(String error) {
                ToastController.init(getView()).showError(error);
            }
        });
    }

}
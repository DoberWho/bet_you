package com.kaimanden.betyou.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.ToastController;
import com.kaimanden.betyou.tools.events.AuthEvent;

import org.greenrobot.eventbus.EventBus;


public class RegisterFragment extends Fragment {

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
        btnLogin    = v.findViewById(R.id.frg_recovery_login);
        btnRegister = v.findViewById(R.id.frg_recovery_register);
        btnRecovery = v.findViewById(R.id.frg_recovery_recovery);

        edtEmail = v.findViewById(R.id.frg_register_email);
        edtPass = v.findViewById(R.id.frg_register_pass);
        edtConfirm = v.findViewById(R.id.frg_register_passconfirm);
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
                AuthEvent event = new AuthEvent(AuthEvent.FrgType.RECOVERY);
                sendEvent(event);
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
        boolean isOk = true;
        String email = edtEmail.getText().toString();
        if (email.trim().isEmpty()){
            String error = getString(R.string.error_empty_data);
            ToastController.init(getView()).showError(error);
            isOk = false;
        }

        String pass = edtPass.getText().toString().trim();
        if (pass.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtPass.setError(error);
            isOk = false;
        }

        String passConfirm = edtConfirm.getText().toString().trim();
        if (passConfirm.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtConfirm.setError(error);
            isOk = false;
        }

        if (!passConfirm.equals(pass)){
            String errorShow = getString(R.string.error_pass_equals);
            ToastController.init(getView()).showError(errorShow);
            return;
        }

        if (!isOk){
            String errorShow = getString(R.string.error_form_data);
            ToastController.init(getView()).showError(errorShow);
            return;
        }


    }

    private void sendEvent(AuthEvent event){
        EventBus.getDefault().post(event);
    }

}
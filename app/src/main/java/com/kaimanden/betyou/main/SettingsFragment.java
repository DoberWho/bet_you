package com.kaimanden.betyou.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.AuthController;
import com.kaimanden.betyou.tools.DbController;
import com.kaimanden.betyou.tools.listeners.AuthListener;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.UserProfile;

public class SettingsFragment extends BaseFrg {

    private EditText edtName, edtPaypal, edtOldPass, edtPass, edtPassConfirm;
    private Switch swNotifs;
    private Button btnProfile, btnPass;
    private DbController dbCtrl;
    private UserProfile currentProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_settings, container, false);
        initViews(v);
        dbCtrl = DbController.init(getActivity());
        initButtons();
        getUserProfile();
        return v;
    }

    private void initViews(View v) {
        edtName = v.findViewById(R.id.frg_settings_name_edt);
        edtPaypal = v.findViewById(R.id.frg_settings_paypal_edt);
        edtOldPass = v.findViewById(R.id.frg_settings_oldpass_edt);
        edtPass = v.findViewById(R.id.frg_settings_pass_edt);
        edtPassConfirm = v.findViewById(R.id.frg_settings_pass_confirm_edt);
        swNotifs = v.findViewById(R.id.frg_settings_notifications_sw);
        btnProfile = v.findViewById(R.id.frg_settings_profile_btn);
        btnPass = v.findViewById(R.id.frg_settings_pass_btn);
    }

    private void initButtons() {
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });

    }

    private void saveProfile() {
        String name   = edtName.getText().toString().trim();
        String paypal = edtPaypal.getText().toString().trim();
        boolean showNotifs = swNotifs.isChecked();

        UserProfile profile = new UserProfile();
        profile.setName(name);
        profile.setPaypal(paypal);
        profile.setShowNotifs(showNotifs);

        showLoading(true);
        hideKeyb();
        dbCtrl.saveProfile(profile, new DbListener() {
            @Override
            public void isOk(UserProfile profile) {
                String msg = getString(R.string.request_save_profile_ok);
                showInfo(msg);
                showLoading(false);
            }

            @Override
            public void isKo(String error) {
                showLoading(false);
                showError(error);
            }
        });
    }

    private void changePassword() {
        String oldPass = edtOldPass.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String passConfirm = edtPassConfirm.getText().toString().trim();
        if (oldPass.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtOldPass.setError(error);
            return;
        }
        if (pass.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtPass.setError(error);
            return;
        }
        if (passConfirm.isEmpty()){
            String error = getString(R.string.error_empty_data);
            edtPassConfirm.setError(error);
            return;
        }
        if (!passConfirm.equals(pass)){
            String error = getString(R.string.error_pass_equals);
            edtPassConfirm.setError(error);
            return;
        }

        showLoading(true);
        hideKeyb();
        AuthController.init(getActivity()).changePass(oldPass, pass, new AuthListener() {
            @Override
            public void isOk(FirebaseUser user) {
                String msg = getString(R.string.request_pass_changed);
                showInfo(msg);
                showLoading(false);
            }

            @Override
            public void isKo(String error) {
                showLoading(false);
                showError(error);
            }
        });
    }

    private void getUserProfile() {
        showLoading(true);
        dbCtrl.getUserProfile(new DbListener() {
            @Override
            public void isOk(UserProfile profile) {
                showLoading(false);
                currentProfile = profile;
                updateProfile();
            }

            @Override
            public void isKo(String error) {
                showLoading(false);
                Log.e("Settings", error);
            }
        });
    }

    private void updateProfile() {
        if (currentProfile == null){
            return;
        }
        edtName.setText(currentProfile.getName());
        edtPaypal.setText(currentProfile.getPaypal());
        swNotifs.setChecked(currentProfile.isShowNotifs());
    }
}
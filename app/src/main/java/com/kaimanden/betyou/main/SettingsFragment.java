package com.kaimanden.betyou.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.base.BaseFrg;
import com.kaimanden.betyou.tools.controllers.AuthController;
import com.kaimanden.betyou.tools.controllers.DbController;
import com.kaimanden.betyou.tools.events.AuthEvent;
import com.kaimanden.betyou.tools.listeners.AuthListener;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.UserProfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

public class SettingsFragment extends BaseFrg {

    public static final int PICK_IMAGE = 12345;

    private EditText edtName, edtPaypal, edtOldPass, edtPass, edtPassConfirm;
    private ImageView imgProfile;
    private Switch swNotifs;
    private Button btnProfile, btnPass, btnLogOut;
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
        try {
            downloadImageProfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        btnLogOut = v.findViewById(R.id.frg_settings_logout);
        imgProfile = v.findViewById(R.id.frg_settings_image);
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

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthController.init(getActivity()).logOut();
                sendEvent(AuthEvent.FrgType.LOGOUT);
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        String label = getString(R.string.select_picture);
        Intent iChooser = Intent.createChooser(intent, label);
        startActivityForResult(iChooser, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != PICK_IMAGE) {
            return;
        }

        //TODO: Recoger una foto
        if (resultCode != BaseAct.RESULT_OK){
            showError("No Pude coger el fichero");
            return;
        }

        Uri uri = data.getData();
        Glide.with(imgProfile).load(uri).centerInside().into(imgProfile);

        subirFichero(uri);

    }

    private void subirFichero(Uri uri){

        try{

            FirebaseUser user = DbController.init(getActivity()).getUser();
            String uid = user.getUid();
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
            StorageReference riversRef = mStorageRef.child("profiles/"+uid);

            riversRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    showInfo("Fue Bien");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showError(e.getLocalizedMessage());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
            Log.e("ERROR", e.getLocalizedMessage());
        }
    }

    private void downloadImageProfile () throws IOException {
        FirebaseUser user = DbController.init(getActivity()).getUser();
        String uid = user.getUid();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child("profiles/"+uid);

        File localFile = File.createTempFile("images", "jpg");

        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file

                        Uri uri = Uri.fromFile(localFile);
                        Glide.with(imgProfile).load(uri).into(imgProfile);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                showError(exception.getLocalizedMessage());
            }
        });

    }

    private File createFile() throws IOException {

        File file = File.createTempFile("temp", "xss");
        file.createNewFile();
        byte[] data1={1,1,0,0};

        if(file.exists())
        {
            OutputStream fo = new FileOutputStream(file);
            fo.write(data1);
            fo.close();
            System.out.println("file created: "+file);
        }
        return file;
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
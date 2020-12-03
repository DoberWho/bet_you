package com.kaimanden.betyou.tools;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaimanden.betyou.base.BaseAct;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.UserProfile;

import java.util.HashMap;

public class DbController {

    private FirebaseFirestore db;
    private Activity act;
    private FirebaseAuth mAuth;

    private DbController(){
        db = FirebaseFirestore.getInstance();
    }
    private static DbController instance;

    public static DbController init(Activity act){
        if (instance == null){
            instance = new DbController();
        }
        instance.act = act;
        instance.getAuth();
        return instance;
    }

    private void getAuth() {
        mAuth = AuthController.init(act).getAuth();
    }

    public void saveProfile(UserProfile profile, DbListener listener){
        FirebaseUser user = mAuth.getCurrentUser();
        HashMap map = profile.toMap();
        map.put("id_user", user.getUid());
        db.collection("users")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.isOk();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception ex) {
                        ex.printStackTrace();
                        String error = ex.getLocalizedMessage();
                        if (listener != null) {
                            listener.isKo(error);
                        }
                    }
                });
    }
}

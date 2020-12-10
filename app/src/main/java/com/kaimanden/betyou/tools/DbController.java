package com.kaimanden.betyou.tools;

import android.app.Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.BetItem;
import com.kaimanden.betyou.tools.models.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class DbController {

    private static final String USERS_COLLECTION = "users";
    private static final String BET_COLLECTION = "bets";

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
        String uid = user.getUid();
        Map map = profile.toMap();
        map.put("uid", uid);
        db.collection(USERS_COLLECTION)
                .document(uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.isOk(profile);
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

    public void getUserProfile(DbListener listener){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        DocumentReference docRef = db.collection(USERS_COLLECTION).document(uid);
        Task<DocumentSnapshot> future = docRef.get();
        future.addOnCompleteListener(act, new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        UserProfile obj = doc.toObject(UserProfile.class);
                        listener.isOk(obj);
                    }else{
                        String error = act.getString(R.string.error_profile_not_exist);
                        listener.isKo(error);
                    }
                }else{
                    String error = act.getString(R.string.error_profile_not_exist);
                    listener.isKo(error);
                }
            }
        });

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    UserProfile obj = value.toObject(UserProfile.class);
                    listener.isOk(obj);
                }else{
                    String err = act.getString(R.string.error_profile_not_exist);
                    listener.isKo(err);
                }
            }
        });
    }

    private void updateProfile(FirebaseUser user, UserProfile profile, DbListener listener){
        //.collection("users").where("uid", "==", payload.uid)
    }

    public void saveBetItem(BetItem bet) {

        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        Map map = bet.toMap();
        map.put("uid", uid);
        db.collection(BET_COLLECTION)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference docRef) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception ex) {
                        ex.printStackTrace();
                        String error = ex.getLocalizedMessage();

                    }
                });
    }

    public void getUserBetItems (){
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        db.collection(BET_COLLECTION).whereArrayContains("uid", uid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {

            }
        });
    }
}

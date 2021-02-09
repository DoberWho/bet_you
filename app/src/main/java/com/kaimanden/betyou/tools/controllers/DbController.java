package com.kaimanden.betyou.tools.controllers;

import android.app.Activity;
import android.util.Log;


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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kaimanden.betyou.R;
import com.kaimanden.betyou.tools.listeners.ChatListener;
import com.kaimanden.betyou.tools.listeners.DbBetitemListener;
import com.kaimanden.betyou.tools.listeners.DbSaveListener;
import com.kaimanden.betyou.tools.listeners.DbListener;
import com.kaimanden.betyou.tools.models.BetItem;
import com.kaimanden.betyou.tools.models.Room;
import com.kaimanden.betyou.tools.models.UserProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbController {

    private static final String USERS_COLLECTION = "users";
    private static final String BET_COLLECTION = "bets";
    private static final String BET_CHATS = "rooms";

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

    public FirebaseUser getUser(){

        return mAuth.getCurrentUser();
    }

    public void saveProfile(UserProfile profile, DbListener listener){
        FirebaseUser user = getUser();
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
        FirebaseUser user = getUser();
        String uid = user.getUid();

        DocumentReference docRef = db.collection(USERS_COLLECTION).document(uid);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
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

    public void saveBetItem(BetItem bet, DbSaveListener listener) {

        FirebaseUser user = getUser();
        String uid = user.getUid();
        Map map = bet.toMap();
        map.put("uid", uid);
        db.collection(BET_COLLECTION)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference docRef) {
                        if (listener != null) listener.saveOk();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception ex) {
                        ex.printStackTrace();
                        String error = ex.getLocalizedMessage();
                        if (listener != null) listener.saveKO(error);
                    }
                });
    }

    private EventListener<QuerySnapshot> getEventListener(DbBetitemListener listener) {
        return new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable  FirebaseFirestoreException error) {
                if (error != null){
                    int code = error.getCode().value();
                    String msg = error.getLocalizedMessage();
                    if (listener != null){
                        listener.isKo(msg);
                    }
                    return;
                }
                List<BetItem> items = new ArrayList<>();
                for (DocumentSnapshot doc: value.getDocuments()) {
                    BetItem item = doc.toObject(BetItem.class);
                    items.add(item);
                }
                if (listener != null){
                    listener.isOk(items);
                }
            }
        };
    }

    private void getOwnerBets(String uid, DbBetitemListener listener){
        Query query = db.collection(BET_COLLECTION).whereEqualTo("uid", uid);
        EventListener<QuerySnapshot> eventListener = this.getEventListener(listener);
        query.addSnapshotListener(eventListener);
    }

    private void getInvolvedBets(String uid, DbBetitemListener listener){
        Query query = db.collection(BET_COLLECTION).whereArrayContains("users", uid);
        EventListener<QuerySnapshot> eventListener = this.getEventListener(listener);
        query.addSnapshotListener(eventListener);
    }



    public void getUserBetItems (boolean owner, DbBetitemListener listener){
        FirebaseUser user = getUser();
        String uid = user.getUid();

        if (owner){
            getOwnerBets(uid, listener);
            return;
        }
        getInvolvedBets(uid, listener);

    }

    public void getChats(ChatListener listener) {
        FirebaseUser user = getUser();
        String uid = user.getUid();

        Query query = db.collection(BET_CHATS).whereArrayContains("users", uid);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable  QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    if (listener != null){
                        listener.ko(error.getLocalizedMessage());
                    }
                    return;
                }

                List<Room> rooms = new ArrayList<>();
                Room room = new Room();
                List<DocumentSnapshot> docs = value.getDocuments();
                for (DocumentSnapshot doc : docs) {
                    room = (Room) room.toObject(doc);
                    rooms.add(room);
                }
                if (listener != null){
                    listener.list(rooms);
                }
            }
        });
    }

    public void saveRoom(Room room) {

        Map map = room.toMap();
        db.collection(BET_CHATS)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference docRef) {
                        Log.d("CHAT", docRef.toString());
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


}

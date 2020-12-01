package com.kaimanden.betyou;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kaimanden.betyou.tools.listeners.AuthListener;
import com.orhanobut.hawk.Hawk;

public class AuthController {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Activity act;

    private AuthController(){}
    private static AuthController instance;

    public static AuthController init(Activity act){
        if (instance == null){
            instance = new AuthController();
        }
        instance.act = act;
        instance.checkUser();
        return instance;
    }

    private void checkUser() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public boolean isLoged(){
        return (currentUser != null);
    }

    private OnCompleteListener<AuthResult> getCompletedTas(AuthListener listener){

        OnCompleteListener<AuthResult> task = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    currentUser = user;
                    if (listener != null) {
                        listener.isOk(user);
                    }
                } else {
                    currentUser = null;
                    // error
                    Exception ex = task.getException();
                    ex.printStackTrace();
                    String error = ex.getLocalizedMessage();
                    if (listener != null) {
                        listener.isKo(error);
                    }
                }
            }

            ;
        };

        return task;
    }

    public void register(String email, String password, AuthListener listener){
        OnCompleteListener<AuthResult> task = this.getCompletedTas(listener);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(act, task);
    }

    public void login(String email, String password, AuthListener listener){
        OnCompleteListener<AuthResult> task = this.getCompletedTas(listener);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(act, task);
    }
}

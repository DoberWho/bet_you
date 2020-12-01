package com.kaimanden.betyou.tools.listeners;

import com.google.firebase.auth.FirebaseUser;

public interface AuthListener {
    void isOk(FirebaseUser user);
    void isKo(String error);
}

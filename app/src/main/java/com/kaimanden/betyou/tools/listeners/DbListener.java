package com.kaimanden.betyou.tools.listeners;

import com.kaimanden.betyou.tools.models.UserProfile;

public interface DbListener {
    void isOk(UserProfile profile);
    void isKo(String error);
}

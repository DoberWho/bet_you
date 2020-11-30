package com.kaimanden.betyou.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    private String userName;
    private String email;
    private String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

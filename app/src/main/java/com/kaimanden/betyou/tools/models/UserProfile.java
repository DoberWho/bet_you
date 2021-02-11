package com.kaimanden.betyou.tools.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.kaimanden.betyou.tools.listeners.DbObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserProfile implements DbObject, Serializable {
    private String name = "";
    private String paypal = "";
    private boolean showNotifs = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaypal() {
        return paypal;
    }

    public void setPaypal(String paypal) {
        this.paypal = paypal;
    }

    public boolean isShowNotifs() {
        return showNotifs;
    }

    public void setShowNotifs(boolean showNotifs) {
        this.showNotifs = showNotifs;
    }

    @Override
    public Map toMap() {
        HashMap map = new HashMap();

        map.put("name", this.name);
        map.put("paypal", this.paypal);
        map.put("showNotifs", this.showNotifs);

        return map;
    }

    @Override
    public Object toObject(DocumentSnapshot doc) {
        return null;
    }
}

package com.kaimanden.betyou.tools.models;

import java.util.HashMap;

public class UserProfile {
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

    public HashMap toMap(){
        HashMap map = new HashMap();

        map.put("name", this.name);
        map.put("paypal", this.paypal);
        map.put("showNotifs", this.showNotifs);

        return map;
    }
}

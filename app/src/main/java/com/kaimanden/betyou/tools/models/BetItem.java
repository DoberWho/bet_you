package com.kaimanden.betyou.tools.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.kaimanden.betyou.tools.listeners.DbObject;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;
import com.tomash.androidcontacts.contactgetter.entity.Email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetItem implements Serializable, DbObject {

    private String owner;
    private String title = "";
    private String desc = "";
    private long betTime = 0;
    private List<ContactData> selected;
    private String price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getBetTime() {
        return betTime;
    }

    public void setBetTime(long betTime) {
        this.betTime = betTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public Map toMap() {
        HashMap map = new HashMap();

        map.put("uid", this.owner);
        map.put("title", this.title);
        map.put("desc", this.desc);
        map.put("betTime", this.betTime);
        map.put("price", this.price);

        List<String> contacts = new ArrayList<>();
        for (ContactData contact: this.selected) {
            Email email = contact.getEmailList().get(0);
            contacts.add(email.getMainData());
        }
        map.put("contacts", contacts);

        return map;
    }

    @Override
    public Object toObject(DocumentSnapshot doc) {
        return null;
    }

    public void setSelected(List<ContactData> selected) {
        this.selected = selected;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}

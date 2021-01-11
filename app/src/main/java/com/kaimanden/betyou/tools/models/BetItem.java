package com.kaimanden.betyou.tools.models;

import com.kaimanden.betyou.tools.interfaces.DbObject;
import com.tomash.androidcontacts.contactgetter.entity.ContactData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BetItem implements Serializable, DbObject {

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


    @Override
    public Map toMap() {
        HashMap map = new HashMap();

        map.put("title", this.title);
        map.put("desc", this.desc);
        map.put("betTime", this.betTime);
        map.put("contacts", this.selected);
        map.put("price", this.price);

        return map;
    }

    public void setSelected(List<ContactData> selected) {
        this.selected = selected;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

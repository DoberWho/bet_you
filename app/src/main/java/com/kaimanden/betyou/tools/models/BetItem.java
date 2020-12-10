package com.kaimanden.betyou.tools.models;

import com.kaimanden.betyou.tools.interfaces.DbObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BetItem implements Serializable, DbObject {

    private String title = "";
    private String desc = "";
    private long betTime = 0;

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

        return map;
    }
}

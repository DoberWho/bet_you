package com.kaimanden.betyou.models;

import java.io.Serializable;

public class BetItem implements Serializable {

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
}

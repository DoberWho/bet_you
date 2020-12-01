package com.kaimanden.betyou.tools.events;

public class MenuEvent {

    public static enum FrgType {
        HOME,
        CALENDAR,
        NEW_BET,
        LIST_BET,
        SETTINGS
    }

    public FrgType frgType = FrgType.HOME;

    public MenuEvent(FrgType frgType){
        this.frgType = frgType;
    }
}

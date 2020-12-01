package com.kaimanden.betyou.tools.events;

public class AuthEvent {

    public static enum FrgType {
        LOGIN,
        REGISTER,
        RECOVERY;
    }

    public FrgType frgType = FrgType.LOGIN;

    public AuthEvent(FrgType frgType){
        this.frgType = frgType;
    }
}

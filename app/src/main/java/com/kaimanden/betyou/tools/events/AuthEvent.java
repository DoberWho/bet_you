package com.kaimanden.betyou.tools.events;

public class AuthEvent {

    public static enum FrgType {
        LOGIN,
        REGISTER,
        RECOVERY,
        LOGIN_OK,
        REGISTER_OK
    }

    public FrgType frgType = FrgType.LOGIN;

    public AuthEvent(FrgType frgType){
        this.frgType = frgType;
    }
}

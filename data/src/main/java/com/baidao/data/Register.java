package com.baidao.data;

/**
 * Created by chengxin on 1/7/15.
 */
public class Register {
    public String phoneNumber;
    public String verificationCode;
    public String password;
    public String marketId;
    public String ext_allowContact;

    public Register(String phoneNumber, String verificationCode, String password, String marketId, String allowContact) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
        this.password = password;
        this.marketId = marketId;
        this.ext_allowContact = allowContact;
    }
}
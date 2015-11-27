package com.baidao.data;

/**
 * Created by chengxin on 1/7/15.
 */
public class Retrieve {
    public String phoneNumber;
    public String verificationCode;
    public String password;
    public int serverId;

    public Retrieve(String phoneNumber, String verificationCode, String password, int serverId) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
        this.password = password;
        this.serverId = serverId;
    }
}
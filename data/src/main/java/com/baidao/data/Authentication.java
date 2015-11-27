package com.baidao.data;

/**
 * Created by chengxin on 1/9/15.
 */
public class Authentication {
    public String openId;
    public String accessToken;
    public long expiresIn;
    public String platform;

    public Authentication(String openId, String accessToken, long expiresIn, String platform) {
        this.openId = openId;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.platform = platform;
    }
}

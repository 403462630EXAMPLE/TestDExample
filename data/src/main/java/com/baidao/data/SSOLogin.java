package com.baidao.data;

/**
 * Created by chengxin on 1/9/15.
 */
public class SSOLogin {
    public Authentication authentication;
    public UserInfo userInfo;

    public SSOLogin(String openId, String accessToken, long expiresIn, String platform, String marketId, int serverId, String nickname) {
        authentication = new Authentication(openId, accessToken, expiresIn, platform);
        userInfo = new UserInfo(marketId, serverId, nickname);
    }
}
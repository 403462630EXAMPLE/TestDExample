package com.baidao.data;

/**
 * Created by chengxin on 1/9/15.
 */
public class UserInfo {
    public String marketId;
    public int serverId;
    public String nickname;

    public UserInfo(String marketId, int serverId, String nickname) {
        this.marketId = marketId;
        this.serverId = serverId;
        this.nickname = nickname;
    }
}

package com.baidao.data;

import java.util.UUID;

/**
 * Created by chengxin on 11/17/15.
 */
public class NoticeHistoryResult {
    public String createTime;
    public String updateTime;
    public int state;
    public int type;
    public String createName;
    public String userType;
    public int id;
    public String content;
    public int dataType;
    public String createAt;
    public String updateName;
    public String serverId;
    public String osName;

    public String getClientId() {
        return UUID.nameUUIDFromBytes((id + createAt).getBytes()).toString();
    }
}

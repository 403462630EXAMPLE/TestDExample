package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 15-2-2.
 */
public enum TopMessageType {
    @SerializedName("0") NONE(0, "没有"),
    @SerializedName("1") CHAT(1, "聊天"),
    @SerializedName("2") ACTIVITY(2, "活动"),
    @SerializedName("3") NEWS(3, "资讯"),
    @SerializedName("4") LOTTO(4, "lotto"),
    @SerializedName("5") OPEN_ACCOUNT(5, "开户");

    private int id;
    private String text;
    TopMessageType(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}

package com.baidao.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by burizado on 14-10-23.
 */
public class LoginMessage {
    public String un;
    @SerializedName("hq_ip")
    public String ip;
    @SerializedName("hq_port")
    public int port;
    public String key;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

package com.baidao.data.e;

/**
 * Created by hexi on 15/3/11.
 */
public enum NetworkType {
    TYPE_NONE("NONE"),
    TYPE_UNKNOWN("UNKNOWN"),
    TYPE_WIFI("WIFI"),
    TYPE_2G("2G"),
    TYPE_3G("3G"),
    TYPE_4G("4G");

    private NetworkType(String value) {
        this.value = value;
    }

    public String value;
}

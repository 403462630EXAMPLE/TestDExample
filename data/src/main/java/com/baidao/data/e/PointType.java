package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 14-12-1.
 */
public enum PointType {
    @SerializedName("0") GENERAL(0),
    @SerializedName("1") BID(1),
    @SerializedName("2") NOTICE(2),
    @SerializedName("3") NOTE(3),
    @SerializedName("4") AUDIO(4),
    @SerializedName("5") APPRENTICE(5),
    @SerializedName("6") EXPERIENCE(6),
    @SerializedName("7") TACTICS(7),
    @SerializedName("8") LEAVE(8),
    @SerializedName("9") REPLY(9),
    @SerializedName("10") EXPERIENCE_MODIFY(10),
    @SerializedName("11") TACTICS_MODIFY(11),
    @SerializedName("12") SHOUT_MODIFY(12),
    @SerializedName("13") GIFTS(13);

    private int value;
    PointType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PointType fromValue(int value) {
        switch (value) {
            case 0:
                return GENERAL;
            case 1:
                return BID;
            case 2:
                return NOTICE;
            case 3:
                return NOTE;
            case 4:
                return AUDIO;
            case 5:
                return APPRENTICE;
            case 6:
                return EXPERIENCE;
            case 7:
                return TACTICS;
            case 8:
                return LEAVE;
            case 9:
                return REPLY;
            case 10:
                return EXPERIENCE_MODIFY;
            case 11:
                return TACTICS_MODIFY;
            case 12:
                return SHOUT_MODIFY;
            case 13:
                return GIFTS;

        }
        return null;
    }

    @Override
    public String toString() {
        switch (value) {
            case 0:
                return "普通观点";
            case 1:
                return "喊单";
            case 2:
                return "公告";
            case 3:
                return "纸条";
            case 4:
                return "音频";
            case 5:
                return "拜师";
            case 6:
                return "心得";
            case 7:
                return "战法";
            case 8:
                return "离开";
            case 9:
                return "回复";
            case 10:
                return "心得修改";
            case 11:
                return "战法修改";
            case 12:
                return "喊单修改";
            case 13:
                return "送礼";
        }
        return null;
    }
}

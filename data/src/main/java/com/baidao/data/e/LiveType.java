package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

public enum LiveType{
    @SerializedName("note") NOTE(0),
    @SerializedName("point") POINT(1),
    @SerializedName("strategy") STRAGETY(2),
    @SerializedName("experience") EXPERIENCE(3),
    @SerializedName("bid") BID(4);

    private int value;

    LiveType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LiveType fromValue(int value) {
        switch (value) {
            case 0:
                return NOTE;
            case 1:
                return POINT;
            case 2:
                return STRAGETY;
            case 3:
                return EXPERIENCE;
            case 4:
                return BID;
        }
        return null;
    }

    @Override
    public String toString() {
        switch (value) {
            case 0:
                return "观点";
            case 1:
                return "纸条";
            case 2:
                return "战法";
            case 3:
                return "心得";
            case 4:
                return "策略";
        }
        return "";
    }
}
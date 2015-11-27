package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 14-12-1.
 */
public enum CodeType {
    @SerializedName("0") EXPERIENCE(0),
    @SerializedName("1") TACTICS(1),
    @SerializedName("2") STRATEGY(2);

    private int value;
    CodeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CodeType fromInt(int value) {
        switch (value) {
            case 0:
                return EXPERIENCE;
            case 1:
                return TACTICS;
            case 2:
                return STRATEGY;
        }
        return null;
    }

    @Override
    public String toString() {
        switch (value) {
            case 0:
                return "心得";
            case 1:
                return "战法";
            case 2:
                return "策略";
        }
        return null;
    }
}

package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 14-12-1.
 */
public enum BidType {
    @SerializedName("1") OPEN_POSITION(1),
    @SerializedName("3") CLOSE_POSITION(3);

    private int value;
    private BidType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static BidType fromInt(int value) {
        switch (value) {
            case 1:
                return OPEN_POSITION;
            case 3:
                return CLOSE_POSITION;
        }
        return null;
    }
}

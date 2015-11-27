package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 14-12-1.
 */
public enum RoomerLevelType {
    @SerializedName("0") LEAD_RESEARCHER,
    @SerializedName("1") DIRECTOR_RESEARCHER,
    @SerializedName("2") ADVANCED_RESEARCHER,
    @SerializedName("3") RESEARCHER
}

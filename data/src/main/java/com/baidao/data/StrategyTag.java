package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by burizado on 15-2-5.
 */
@ParcelablePlease
public class StrategyTag implements Parcelable{
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        StrategyTagParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<StrategyTag> CREATOR = new Creator<StrategyTag>() {
        public StrategyTag createFromParcel(Parcel source) {
            StrategyTag target = new StrategyTag();
            StrategyTagParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public StrategyTag[] newArray(int size) {
            return new StrategyTag[size];
        }
    };

}

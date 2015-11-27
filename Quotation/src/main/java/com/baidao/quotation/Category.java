package com.baidao.quotation;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by burizado on 14-11-3.
 */
@ParcelablePlease
public class Category implements Parcelable {
    public String id;
    public String market;
    public String marketName;
    public String nickName;
    public String name;
    public int decimalDigits;
    public int offSet;
    public String offerQuoteSide;
    public String bidQuoteSide;
    public String bondCategory;
    public String reserveString_1;
    public double prevClosedPx;
    public double preSettlementPx;
    public int f10;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        CategoryParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            Category target = new Category();
            CategoryParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static Category fromJson(String json) {
        return new Gson().fromJson(json, Category.class);
    }
}

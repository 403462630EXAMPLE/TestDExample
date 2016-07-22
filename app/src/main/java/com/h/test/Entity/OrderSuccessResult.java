package cn.hdmoney.hdy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class OrderSuccessResult implements Parcelable {
    public long id;
    public String bidName;
    public double amount;
    public String date;
    public List<Coupon> couponList;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.bidName);
        dest.writeDouble(this.amount);
        dest.writeString(this.date);
        dest.writeTypedList(this.couponList);
    }

    public OrderSuccessResult() {
    }

    protected OrderSuccessResult(Parcel in) {
        this.id = in.readLong();
        this.bidName = in.readString();
        this.amount = in.readDouble();
        this.date = in.readString();
        this.couponList = in.createTypedArrayList(Coupon.CREATOR);
    }

    public static final Parcelable.Creator<OrderSuccessResult> CREATOR = new Parcelable.Creator<OrderSuccessResult>() {
        @Override
        public OrderSuccessResult createFromParcel(Parcel source) {
            return new OrderSuccessResult(source);
        }

        @Override
        public OrderSuccessResult[] newArray(int size) {
            return new OrderSuccessResult[size];
        }
    };
}
package cn.hdmoney.hdy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class Coupon implements Parcelable {
    public int id; //优惠劵记录ID
    public int cid; //优惠劵ID
    public String couponName; //优惠劵名字
    public double amount; //优惠卷金额，加息劵是加息的点数
    public String description; //规则描述
    public String expireTime; //过期日期
    public int status;
    public String enableProduct;

    public int type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.cid);
        dest.writeString(this.couponName);
        dest.writeDouble(this.amount);
        dest.writeString(this.description);
        dest.writeString(this.expireTime);
        dest.writeInt(this.status);
        dest.writeString(this.enableProduct);
        dest.writeInt(this.type);
    }

    public Coupon() {
    }

    protected Coupon(Parcel in) {
        this.id = in.readInt();
        this.cid = in.readInt();
        this.couponName = in.readString();
        this.amount = in.readDouble();
        this.description = in.readString();
        this.expireTime = in.readString();
        this.status = in.readInt();
        this.enableProduct = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<Coupon> CREATOR = new Parcelable.Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}

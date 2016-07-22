package cn.hdmoney.hdy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class InvestRecord implements Parcelable {
    public String date; //投资时间（YYYYMMDD）
    public String purchaser; //购买人（手机号码中间4位*号代替，如：135****5678）
    public double money; //投资金额(保留两位小数)

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.purchaser);
        dest.writeDouble(this.money);
    }

    public InvestRecord() {
    }

    protected InvestRecord(Parcel in) {
        this.date = in.readString();
        this.purchaser = in.readString();
        this.money = in.readDouble();
    }

    public static final Parcelable.Creator<InvestRecord> CREATOR = new Parcelable.Creator<InvestRecord>() {
        @Override
        public InvestRecord createFromParcel(Parcel source) {
            return new InvestRecord(source);
        }

        @Override
        public InvestRecord[] newArray(int size) {
            return new InvestRecord[size];
        }
    };
}

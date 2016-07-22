package cn.hdmoney.hdy.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class Bid implements Parcelable {
    public int id; //标的ID
    public String name; //标的名称
    public int type; //标的类型int值
    public String typeName; //标的类型名字
    public double apr; //利率
//    @SerializedName("add_interest")
    public double addInterest; //加息点数（如0.5）
    public String period; //投资周期（如30天，3个月，1年）
    public String residuePeriod; //剩余的投资周期（如30天，3个月，1年）
    public double schedule; //融资进度
    public double residueShare; //剩余份数

    public double baseAmount; //起投金额
    public double avgAmount; //每笔金额
    public double finaceAmount; //融资金额
    public double hasAmount; //已融金额
    public String acceptBank; //承兑行
    public String interestDate; //起息日期
    public String repayDate; //结息时间
    public String latestRepaymentDate; //最迟还款日前（如：2016-08-08）
    public String guarantyUrl; //担保物URL
    public String descriptionUrl; //产品说明URL

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.type);
        dest.writeString(this.typeName);
        dest.writeDouble(this.apr);
        dest.writeDouble(this.addInterest);
        dest.writeString(this.period);
        dest.writeString(this.residuePeriod);
        dest.writeDouble(this.schedule);
        dest.writeDouble(this.residueShare);
        dest.writeDouble(this.baseAmount);
        dest.writeDouble(this.avgAmount);
        dest.writeDouble(this.finaceAmount);
        dest.writeDouble(this.hasAmount);
        dest.writeString(this.acceptBank);
        dest.writeString(this.interestDate);
        dest.writeString(this.repayDate);
        dest.writeString(this.latestRepaymentDate);
        dest.writeString(this.guarantyUrl);
        dest.writeString(this.descriptionUrl);
    }

    public Bid() {
    }

    protected Bid(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.type = in.readInt();
        this.typeName = in.readString();
        this.apr = in.readDouble();
        this.addInterest = in.readDouble();
        this.period = in.readString();
        this.residuePeriod = in.readString();
        this.schedule = in.readDouble();
        this.residueShare = in.readDouble();
        this.baseAmount = in.readDouble();
        this.avgAmount = in.readDouble();
        this.finaceAmount = in.readDouble();
        this.hasAmount = in.readDouble();
        this.acceptBank = in.readString();
        this.interestDate = in.readString();
        this.repayDate = in.readString();
        this.latestRepaymentDate = in.readString();
        this.guarantyUrl = in.readString();
        this.descriptionUrl = in.readString();
    }

    public static final Creator<Bid> CREATOR = new Creator<Bid>() {
        @Override
        public Bid createFromParcel(Parcel source) {
            return new Bid(source);
        }

        @Override
        public Bid[] newArray(int size) {
            return new Bid[size];
        }
    };
}

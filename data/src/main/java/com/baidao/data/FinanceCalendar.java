package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.Date;

/**
 * Created by burizado on 15-1-22.
 */
@ParcelablePlease
public class FinanceCalendar implements Parcelable {
    public static enum Type {
        EVENT("财经大事"),
        DATUM("经济数据");
        public String value;

        private Type(String value) {
            this.value = value;
        }
    }

    public String ecTitle;
    public long publishTimeMs;
    public String ecFormerValue;
    public String ecPredictedValue;
    public String ecPublishedValue;
    public int bullbear;
    public boolean top;
    public String ecName;
    public String category;
    /**
     * 标题
     */
    public String comment;
    public String ecOrg;
    public String ecNextTime; //format 2015-02-28 07:30
    public String ecEffect;
    public String ecFrequency;
    public String ecMeaning;
    public String ecReason;
    public String ecMethod;
    public String ecCountry;
    public String ecImportance;
    public String fx768CalendarId;
    public String ecFid;

    public FinanceCalendar() {
    }

    public FinanceCalendar(String title, long publishTimeMs) {
        this.ecTitle = title;
        this.publishTimeMs = publishTimeMs;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        FinanceCalendarParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<FinanceCalendar> CREATOR = new Creator<FinanceCalendar>() {
        public FinanceCalendar createFromParcel(Parcel source) {
            FinanceCalendar target = new FinanceCalendar();
            FinanceCalendarParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public FinanceCalendar[] newArray(int size) {
            return new FinanceCalendar[size];
        }
    };
}

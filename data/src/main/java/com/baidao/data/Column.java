package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Bruce on 1/29/15.
 */
@ParcelablePlease
public class Column implements Parcelable {
    public long id;
    public long createTime;
    public long updateTime;
    public String name;
    public String introduction;
    public String remarks;
    public String attrslogan;
    public String attrlogo;
    public String attrtitle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ColumnParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Column> CREATOR = new Creator<Column>() {
        public Column createFromParcel(Parcel source) {
            Column target = new Column();
            ColumnParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Column[] newArray(int size) {
            return new Column[size];
        }
    };
}

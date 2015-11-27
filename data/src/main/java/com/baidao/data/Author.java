package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by Bruce on 1/29/15.
 */
@ParcelablePlease
public class Author implements Parcelable, Jsonable {
    public long id;
    public long createTime;
    public long updateTime;
    public String name;
    public String attrlogo;
    public String introduction;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        AuthorParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        public Author createFromParcel(Parcel source) {
            Author target = new Author();
            AuthorParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }
}

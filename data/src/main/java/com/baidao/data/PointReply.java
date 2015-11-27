package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by rjhy on 15-3-10.
 */
@ParcelablePlease
public class PointReply implements Parcelable {
    public String content;
    public String nickname;
    public String updateTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        PointReplyParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<PointReply> CREATOR = new Creator<PointReply>() {
        public PointReply createFromParcel(Parcel source) {
            PointReply target = new PointReply();
            PointReplyParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public PointReply[] newArray(int size) {
            return new PointReply[size];
        }
    };
}

package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.e.TopMessageType;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by rjhy on 15-1-13.
 */
@ParcelablePlease
public class TopMessage implements ExpertLiveMessage, Parcelable {
    public String content;
    public int userType;
    public String datetime;
    public TopMessageType type;
    public long detailId;
    public long id;
    public TopMessageDetail detail;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public TopMessageType getType() {
        return type;
    }

    public void setType(TopMessageType type) {
        this.type = type;
    }

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TopMessageDetail getDetail() {
        return detail;
    }

    public void setDetail(TopMessageDetail detail) {
        this.detail = detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TopMessageParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TopMessage> CREATOR = new Creator<TopMessage>() {
        public TopMessage createFromParcel(Parcel source) {
            TopMessage target = new TopMessage();
            TopMessageParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TopMessage[] newArray(int size) {
            return new TopMessage[size];
        }
    };
}

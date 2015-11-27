package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hannesdorfmann.parcelableplease.ParcelBagger;
import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.List;

/**
 * Created by burizado on 15-1-8.
 */
@ParcelablePlease
public class ImportantEvent implements Jsonable, Parcelable {
    public long id;
    public long createTime;
    public long updateTime;
    public long publishTimeMs;
    public long authorId;
    public String authorName;
    public String title;
    public String subTitle;
    public String introduction;
    public String content;
    public String state;
    public int pv;
    public int uv;
    @Bagger(MessageTypeBagger.class)
    public MessageType messageType;
    public String category;
    public boolean important;
    public boolean top;
    public String referenceType;
    public String referenceDesc;

    public String url;
    public String img;

    public String shareUrl;//视频分享url
    public String shareImg;

    public String comment;//火线速递

    public String desc;//活动的字段
    public int hitCount;//文章阅读量
    public String referenceId;

    public List<Column> columns;
    public List<StrategyTag> tags;
    public Author author;

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ImportantEventParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<ImportantEvent> CREATOR = new Creator<ImportantEvent>() {
        public ImportantEvent createFromParcel(Parcel source) {
            ImportantEvent target = new ImportantEvent();
            ImportantEventParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public ImportantEvent[] newArray(int size) {
            return new ImportantEvent[size];
        }
    };

    public static enum MessageType {
        @SerializedName("0") 今日看盘(0),
        @SerializedName("1") 短讯(1),
        @SerializedName("2") 文章(2),
        @SerializedName("3") 活动专题(3),
        @SerializedName("4") 服务短讯(4),
        @SerializedName("5") fx168火线速递(5);

        private int value;
        private MessageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MessageType fromValue(int value) {
            switch (value) {
                case 0:
                    return MessageType.今日看盘;
                case 1:
                    return MessageType.短讯;
                case 2:
                    return MessageType.文章;
                case 3:
                    return MessageType.活动专题;
                case 4:
                    return MessageType.服务短讯;
                case 5:
                    return MessageType.fx168火线速递;
                default:
                    return MessageType.短讯;
            }
        }


        @Override
        public String toString() {
            switch (value) {
                case 0:
                    return "今日看盘";
                case 1:
                    return "短讯";
                case 2:
                    return "文章";
                case 3:
                    return "活动专题";
                case 4:
                    return "服务短讯";
                case 5:
                    return "fx168火线速递";
                default:
                    return "短讯";
            }
        }
    }

    public static class MessageTypeBagger implements ParcelBagger<MessageType> {

        @Override
        public void write(MessageType value, Parcel out, int flags) {
            if (value == null) {
                out.writeInt(MessageType.短讯.getValue());
            } else {
                out.writeInt(value.getValue());
            }
        }

        @Override
        public MessageType read(Parcel in) {
            int value = in.readInt();
            return MessageType.fromValue(value);
        }
    }
}

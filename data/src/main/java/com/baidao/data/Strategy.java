package com.baidao.data;


import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.e.BidType;
import com.baidao.data.e.CodeType;
import com.baidao.data.e.PointType;

/**
 * Created by rjhy on 14-12-1.
 */
public class Strategy implements Parcelable {

    public long id;
    public String type;
    public String content;
    public String title;
    public String description;
    public String username;
    public int status;
    public String target;
    public CodeType code;
    public String nickname;
    public long updateTime;
    public long createTime;
    public BidType bidType;
    public long bidNumber;
    public String bidWay;
    public String direction;
    public String trader;
    public String nowPrice;
    public String positions;
    public String stop;
    public boolean isPublic;
    public String style;
    public String motto;

    public PointType messageType;
    public boolean canAsk;
    public String pureContent;
    public String userImage;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public CodeType getCode() {
        return code;
    }

    public void setCode(CodeType code) {
        this.code = code;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public BidType getBidType() {
        return bidType;
    }

    public void setBidType(BidType bidType) {
        this.bidType = bidType;
    }

    public long getBidNumber() {
        return bidNumber;
    }

    public void setBidNumber(long bidNumber) {
        this.bidNumber = bidNumber;
    }

    public String getBidWay() {
        return bidWay;
    }

    public void setBidWay(String bidWay) {
        this.bidWay = bidWay;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public PointType getMessageType() {
        return messageType;
    }

    public void setMessageType(PointType messageType) {
        this.messageType = messageType;
    }

    public boolean isCanAsk() {
        return canAsk;
    }

    public void setCanAsk(boolean canAsk) {
        this.canAsk = canAsk;
    }

    public String getPureContent() {
        return pureContent;
    }

    public void setPureContent(String pureContent) {
        this.pureContent = pureContent;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.type);
        dest.writeString(this.content);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.username);
        dest.writeInt(this.status);
        dest.writeString(this.target);
        dest.writeInt(this.code == null ? -1 : this.code.ordinal());
        dest.writeString(this.nickname);
        dest.writeLong(this.updateTime);
        dest.writeLong(this.createTime);
        dest.writeInt(this.bidType == null ? -1 : this.bidType.ordinal());
        dest.writeLong(this.bidNumber);
        dest.writeString(this.bidWay);
        dest.writeString(this.direction);
        dest.writeString(this.trader);
        dest.writeString(this.nowPrice);
        dest.writeString(this.positions);
        dest.writeString(this.stop);
        dest.writeByte(isPublic ? (byte) 1 : (byte) 0);
        dest.writeString(this.style);
        dest.writeString(this.motto);
        dest.writeInt(this.messageType == null ? -1 : this.messageType.ordinal());
        dest.writeByte(canAsk ? (byte) 1 : (byte) 0);
        dest.writeString(this.pureContent);
        dest.writeString(this.userImage);
    }

    public Strategy() {
    }

    private Strategy(Parcel in) {
        this.id = in.readLong();
        this.type = in.readString();
        this.content = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.username = in.readString();
        this.status = in.readInt();
        this.target = in.readString();
        int tmpCode = in.readInt();
        this.code = tmpCode == -1 ? null : CodeType.values()[tmpCode];
        this.nickname = in.readString();
        this.updateTime = in.readLong();
        this.createTime = in.readLong();
        int tmpBidType = in.readInt();
        this.bidType = tmpBidType == -1 ? null : BidType.values()[tmpBidType];
        this.bidNumber = in.readLong();
        this.bidWay = in.readString();
        this.direction = in.readString();
        this.trader = in.readString();
        this.nowPrice = in.readString();
        this.positions = in.readString();
        this.stop = in.readString();
        this.isPublic = in.readByte() != 0;
        this.style = in.readString();
        this.motto = in.readString();
        int tmpMessageType = in.readInt();
        this.messageType = tmpMessageType == -1 ? null : PointType.values()[tmpMessageType];
        this.canAsk = in.readByte() != 0;
        this.pureContent = in.readString();
        this.userImage = in.readString();
    }

    public static final Creator<Strategy> CREATOR = new Creator<Strategy>() {
        public Strategy createFromParcel(Parcel source) {
            return new Strategy(source);
        }

        public Strategy[] newArray(int size) {
            return new Strategy[size];
        }
    };
}

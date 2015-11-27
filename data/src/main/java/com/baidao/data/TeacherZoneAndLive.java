package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.e.BidType;
import com.baidao.data.e.CodeType;
import com.baidao.data.e.LiveType;
import com.baidao.data.e.PointType;
import com.baidao.data.e.RoomerLevelType;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by rjhy on 14-11-28.
 */
@ParcelablePlease
public class TeacherZoneAndLive implements ExpertLiveMessage, Parcelable {

    public String method;
    public long id;
    public LiveType type;
    public String content;
    public String pureContent;
    public String username;
    public String nickname;
    public String style;
    public String motto;
    public long roomId;
    public long updateTime;
    public int userType;
    public boolean isRoomer;
    public long recordId;
    public String userImage;
    public RoomerLevelType roomerLevel;
    public String userDesc;
    public String department;
    public String idea;
    public boolean canAsk;
    public Question question;

    public String title;
    public String imgUrl;
    public PointType messageType;
    public String[] contentImages;
    public PointReply reply;

    public String description;
    public int status;
    public String target;
    public CodeType code;
    public long createTime;
    public BidType bidType;
    public long bidNumber;
    public String bidWay;
    public String direction;
    public String trader;
    public String nowPrice;
    public String positions;
    public String stop;

    public PointReply getReply() {
        return reply;
    }

    public void setReply(PointReply reply) {
        this.reply = reply;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LiveType getType() {
        return type;
    }

    public void setType(LiveType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPureContent() {
        return pureContent;
    }

    public void setPureContent(String pureContent) {
        this.pureContent = pureContent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isRoomer() {
        return isRoomer;
    }

    public void setRoomer(boolean isRoomer) {
        this.isRoomer = isRoomer;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public RoomerLevelType getRoomerLevel() {
        return roomerLevel;
    }

    public void setRoomerLevel(RoomerLevelType roomerLevel) {
        this.roomerLevel = roomerLevel;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public boolean isCanAsk() {
        return canAsk;
    }

    public void setCanAsk(boolean canAsk) {
        this.canAsk = canAsk;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PointType getMessageType() {
        return messageType;
    }

    public void setMessageType(PointType messageType) {
        this.messageType = messageType;
    }

    public String[] getContentImages() {
        return contentImages;
    }

    public void setContentImages(String[] contentImages) {
        this.contentImages = contentImages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TeacherZoneAndLiveParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TeacherZoneAndLive> CREATOR = new Creator<TeacherZoneAndLive>() {
        public TeacherZoneAndLive createFromParcel(Parcel source) {
            TeacherZoneAndLive target = new TeacherZoneAndLive();
            TeacherZoneAndLiveParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TeacherZoneAndLive[] newArray(int size) {
            return new TeacherZoneAndLive[size];
        }
    };
}

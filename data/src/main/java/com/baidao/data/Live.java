package com.baidao.data;


import com.baidao.data.e.LiveType;
import com.baidao.data.e.RoomerLevelType;
import com.baidao.data.e.PointType;

/**
 * Created by rjhy on 14-11-28.
 */
public class Live {

    public String method;
    public long id;
    public LiveType type = LiveType.STRAGETY;
    public String content;
    public String username;
    public String nickname;
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
}

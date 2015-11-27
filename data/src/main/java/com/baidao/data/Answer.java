package com.baidao.data;

import com.baidao.data.e.RoomerLevelType;

/**
 * Created by rjhy on 14-12-1.
 */
public class Answer{
    public String method;
    public String content;
    public String username;
    public String nickname;
    public long updateTime;
    public int userType;
    public boolean isRoomer;
    public String userImage;
    public RoomerLevelType roomerLevel;
    public String userDesc;
    public String department;
    public String idea;
    public boolean canAsk;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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
}

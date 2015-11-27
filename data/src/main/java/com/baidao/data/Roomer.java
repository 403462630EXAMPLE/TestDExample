package com.baidao.data;

import com.baidao.data.e.RoomerLevelType;

/**
 * Created by rjhy on 14-12-2.
 */
public class Roomer {
    public long id;
    public long endTime;
    public String username;
    public String desc;
    public String nickname;
    public long roomId;
    public String imgUrl;
    public boolean isRoomer;
    public int winPoint;
    public int winCnt;
    public RoomerLevelType roomerLevel;
    public String department;
    public String achievement;
    public String style;
    public String motto;
    public long joinTime;
    public int roomerType;
    public String roomerUrl;
    public int ballot;
    public int grade;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isRoomer() {
        return isRoomer;
    }

    public void setRoomer(boolean isRoomer) {
        this.isRoomer = isRoomer;
    }

    public int getWinPoint() {
        return winPoint;
    }

    public void setWinPoint(int winPoint) {
        this.winPoint = winPoint;
    }

    public int getWinCnt() {
        return winCnt;
    }

    public void setWinCnt(int winCnt) {
        this.winCnt = winCnt;
    }

    public RoomerLevelType getRoomerLevel() {
        return roomerLevel;
    }

    public void setRoomerLevel(RoomerLevelType roomerLevel) {
        this.roomerLevel = roomerLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
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

    public long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(long joinTime) {
        this.joinTime = joinTime;
    }

    public int getRoomerType() {
        return roomerType;
    }

    public void setRoomerType(int roomerType) {
        this.roomerType = roomerType;
    }

    public String getRoomerUrl() {
        return roomerUrl;
    }

    public void setRoomerUrl(String roomerUrl) {
        this.roomerUrl = roomerUrl;
    }

    public int getBallot() {
        return ballot;
    }

    public void setBallot(int ballot) {
        this.ballot = ballot;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}

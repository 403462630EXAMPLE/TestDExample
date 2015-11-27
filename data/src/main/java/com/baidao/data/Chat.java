package com.baidao.data;

/**
 * Created by rjhy on 14-11-28.
 */
public class Chat{
    public String method;
    public long id;
    public String content;
    public String username;
    public String nickname;
    public long roomId;
    public long updateTime;
    public int userType;
    public long createTime;
    public long recordId;
    public String userImage;
    public int approveSeconds;
    public String userDesc;
    public int isActive;
    public int isIpad;
    public String ip;
    public String department;
    public String idea;
    public boolean fromTeacher;
    public String pureContent;
    public String approveUsername;

    public QuoteChat quoteChat;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public int getApproveSeconds() {
        return approveSeconds;
    }

    public void setApproveSeconds(int approveSeconds) {
        this.approveSeconds = approveSeconds;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getIsIpad() {
        return isIpad;
    }

    public void setIsIpad(int isIpad) {
        this.isIpad = isIpad;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public boolean isFromTeacher() {
        return fromTeacher;
    }

    public void setFromTeacher(boolean fromTeacher) {
        this.fromTeacher = fromTeacher;
    }

    public String getPureContent() {
        return pureContent;
    }

    public void setPureContent(String pureContent) {
        this.pureContent = pureContent;
    }

    public String getApproveUsername() {
        return approveUsername;
    }

    public void setApproveUsername(String approveUsername) {
        this.approveUsername = approveUsername;
    }

    public QuoteChat getQuoteChat() {
        return quoteChat;
    }

    public void setQuoteChat(QuoteChat quoteChat) {
        this.quoteChat = quoteChat;
    }
}

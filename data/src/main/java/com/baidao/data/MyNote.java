package com.baidao.data;

import com.baidao.data.e.LiveType;
import com.baidao.data.e.RoomerLevelType;

import java.util.ArrayList;

/**
 * Created by rjhy on 14-12-1.
 */
public class MyNote {
    public LiveType type = LiveType.NOTE;
    public String content;
    public String username;
    public String nickname;
    public long updateTime;
    public int userType;
    public String userImage;
    public long roomId;
    public long recordId;
    public ArrayList<String> questionImages;

    public Answer answer;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Teacher teacher;

    public class Teacher{
        public String method;
        public String username;
        public String nickname;
        public int userType;
        public String userImage;
        public long updateTime;
        public RoomerLevelType roomerLevel;
        public String userDesc;
        public boolean isRoomer;
        public String department;
        public String idea;
        public boolean canAsk;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
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

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
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

        public boolean isRoomer() {
            return isRoomer;
        }

        public void setRoomer(boolean isRoomer) {
            this.isRoomer = isRoomer;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public ArrayList<String> getQuestionImages() {
        return questionImages;
    }

    public void setQuestionImages(ArrayList<String> questionImages) {
        this.questionImages = questionImages;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}

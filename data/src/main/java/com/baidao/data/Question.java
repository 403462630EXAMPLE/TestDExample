package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;

/**
 * Created by rjhy on 14-11-28.
 */
@ParcelablePlease
public class Question implements Parcelable {
    public String content;
    public String username;
    public String nickname;
    public long updateTime;
    public int userType;
    public String userImage;
    public ArrayList<String> questionImages;

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

    public ArrayList<String> getQuestionImages() {
        return questionImages;
    }

    public void setQuestionImages(ArrayList<String> questionImages) {
        this.questionImages = questionImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuestionParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        public Question createFromParcel(Parcel source) {
            Question target = new Question();
            QuestionParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}

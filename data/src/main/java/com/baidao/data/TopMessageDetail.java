package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

@ParcelablePlease
public class TopMessageDetail implements Parcelable {
    public String title;
    public String desc;
    public String img;
    public String shareImg;
    public String galleryImg;
    public String coinPageImg;
    public String coinPageColor;
    public String url;
    public int type;
    public int order;
    public long inReward;
    public long shareReward;
    public long share;
    public boolean published;
    public int status;
    public int position;
    public String updateTime;
    public long id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public String getGalleryImg() {
        return galleryImg;
    }

    public void setGalleryImg(String galleryImg) {
        this.galleryImg = galleryImg;
    }

    public String getCoinPageImg() {
        return coinPageImg;
    }

    public void setCoinPageImg(String coinPageImg) {
        this.coinPageImg = coinPageImg;
    }

    public String getCoinPageColor() {
        return coinPageColor;
    }

    public void setCoinPageColor(String coinPageColor) {
        this.coinPageColor = coinPageColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getInReward() {
        return inReward;
    }

    public void setInReward(long inReward) {
        this.inReward = inReward;
    }

    public long getShareReward() {
        return shareReward;
    }

    public void setShareReward(long shareReward) {
        this.shareReward = shareReward;
    }

    public long getShare() {
        return share;
    }

    public void setShare(long share) {
        this.share = share;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TopMessageDetailParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TopMessageDetail> CREATOR = new Creator<TopMessageDetail>() {
        public TopMessageDetail createFromParcel(Parcel source) {
            TopMessageDetail target = new TopMessageDetail();
            TopMessageDetailParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TopMessageDetail[] newArray(int size) {
            return new TopMessageDetail[size];
        }
    };
}
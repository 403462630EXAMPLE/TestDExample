package com.baidao.data;


import com.baidao.data.e.NewsCenterType;

/**
 * Created by rjhy on 14-12-5.
 */
public class NewsCenter {
    public long id;
    public NewsCenterType type;
    public String title;
    public long pushedAt;
    public long createdAt;
    public String news;

    public String username;
    public String sid;
    public double priceTarget;
    public String direction;
    public boolean hasRead;
    public double priceNow;
    public String quoteName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public double getPriceTarget() {
        return priceTarget;
    }

    public void setPriceTarget(double priceTarget) {
        this.priceTarget = priceTarget;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public double getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(double priceNow) {
        this.priceNow = priceNow;
    }

    public String getQuoteName() {
        return quoteName;
    }

    public void setQuoteName(String quoteName) {
        this.quoteName = quoteName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NewsCenterType getType() {
        return type;
    }

    public void setType(NewsCenterType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(long pushedAt) {
        this.pushedAt = pushedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}

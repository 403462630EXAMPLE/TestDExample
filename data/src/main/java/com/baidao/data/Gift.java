package com.baidao.data;

/**
 * Created by rjhy on 14-12-8.
 */
public class Gift {
    public String name;
    public int type;
    public long giftId;
    public int price;
    public String promotionImage;
    public String unit;
    public int marketPrice;
    public int exchangedNumber;
    public String titleImage;

    public int recommendOrder;
    public String recommendedImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getGiftId() {
        return giftId;
    }

    public void setGiftId(long giftId) {
        this.giftId = giftId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPromotionImage() {
        return promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public int getExchangedNumber() {
        return exchangedNumber;
    }

    public void setExchangedNumber(int exchangedNumber) {
        this.exchangedNumber = exchangedNumber;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public int getRecommendOrder() {
        return recommendOrder;
    }

    public void setRecommendOrder(int recommendOrder) {
        this.recommendOrder = recommendOrder;
    }

    public String getRecommendedImage() {
        return recommendedImage;
    }

    public void setRecommendedImage(String recommendedImage) {
        this.recommendedImage = recommendedImage;
    }
}

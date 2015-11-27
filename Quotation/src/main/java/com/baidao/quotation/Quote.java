package com.baidao.quotation;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.Jsonable;
import com.baidao.tools.BigDecimalUtil;
import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by hexi on 14/12/8.
 */
@ParcelablePlease
public class Quote implements Parcelable, Jsonable
{
    public String id;
    public String quoteName;
    public String market;
    public double now;
    public double preClose;
    public double open;
    public double high;
    public double low;
    public double buy;
    public double sell;
    public int decimalDigits;
    public double average;
    public double totalTradeVolume;//上海金only
    public double tradeVolume;
    public String reservedString;
    public long dateTime;

    public Category category;
    /**
     * 委差
     */
    public double committeeDiffer;
    /**
     * 委比
     */
    public String committeeRatio;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuoteParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        public Quote createFromParcel(Parcel source) {
            Quote target = new Quote();
            QuoteParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    public static Quote build(Category category) {
        Quote quote = new Quote();
        quote.quoteName = category.name;
        quote.id = category.nickName;
        quote.market = category.market;
        quote.updatePreClose(category);
        quote.decimalDigits = category.decimalDigits;
        quote.reservedString = category.reserveString_1;
        quote.category = category;
        return quote;
    }


    public void update(Category category) {
        updatePreClose(category);
        this.category = category;
    }

    private static final String MARKET_JG = "TPME";
    private static final String MARKET_SY = "SZPEX";
    private void updatePreClose(Category category){
        if (category.market.equals(MARKET_JG) || category.market.equals(MARKET_SY)) {
            this.preClose = category.prevClosedPx;
        } else {
            this.preClose = (category.preSettlementPx > 0) ? category.preSettlementPx : category.prevClosedPx;
        }
    }

    public void update(Snapshot snapshot) {
        if (snapshot.latestPx > 0) {
            now = snapshot.latestPx;
        }
        if (snapshot.openPx > 0) {
            open = snapshot.openPx;
        }
        buy = snapshot.bidPx1;
        if (snapshot.highPx > 0) {
            high = snapshot.highPx;
        }
        if (snapshot.lowPx > 0) {
            low = snapshot.lowPx;
        }
        sell = snapshot.askPx1;
        this.average = snapshot.avgPx;
        this.dateTime = snapshot.dateTime * 1000;
        setSGEProperties(snapshot);
    }

    private void setSGEProperties(Snapshot snapshot) {
        if (isSGE()) {
            final SHGSnapshot shgSnapshot = (SHGSnapshot) snapshot;
            this.totalTradeVolume = shgSnapshot.totalTradeVolume;
            this.tradeVolume = shgSnapshot.tradeVolume;
            double sumBuy = BigDecimalUtil.add(shgSnapshot.bidVolume1, shgSnapshot.bidVolume2, shgSnapshot.bidVolume3, shgSnapshot.bidVolume4, shgSnapshot.bidVolume5);
            double sumSell = BigDecimalUtil.add(shgSnapshot.askVolume1, shgSnapshot.askVolume2, shgSnapshot.askVolume3, shgSnapshot.askVolume4, shgSnapshot.askVolume5);
            if (sumBuy == 0 || sumSell == 0) {
                return;
            }
            this.committeeDiffer = BigDecimalUtil.sub(sumBuy, sumSell);
            double committeeRatio = BigDecimalUtil.div(BigDecimalUtil.mul(this.committeeDiffer, 100D), BigDecimalUtil.add(sumBuy, sumSell), 2);
            if (committeeRatio > 0) {
                this.committeeRatio = "+" + committeeRatio + "%";
            } else {
                this.committeeRatio = committeeRatio + "%";
            }
        }
    }

    private static final String MARKET_SGE = "SGE";
    private boolean isSGE() {
        return this.market.equals(MARKET_SGE);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getSid() {
        return this.market + "." + this.id;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    public boolean isUp() {
        return (now - preClose) >= 0;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}

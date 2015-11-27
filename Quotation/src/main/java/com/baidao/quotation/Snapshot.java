package com.baidao.quotation;

import com.google.gson.Gson;

/**
 * Created by burizado on 14-11-7.
 */
class Snapshot {
    public String market;
    public String securityId;
    public long dateTime;
    public double latestPx;
    public double openPx;
    public double highPx;
    public double lowPx;
    public double preOpenPosition;
    public double bidPx1;
    public double askPx1;
    public double avgPx;

    public String getSid() {
        return this.market + "." + this.securityId;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

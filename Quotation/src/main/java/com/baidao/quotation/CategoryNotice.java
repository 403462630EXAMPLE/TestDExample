package com.baidao.quotation;

/**
 * Created by hexi on 15/8/19.
 */
public class CategoryNotice {
    public String market;
    public String securityId;
    public double preSettlementPx;
    public double prevClosedPx;

    public String getSid() {
        return this.market + "." + this.securityId;
    }
}

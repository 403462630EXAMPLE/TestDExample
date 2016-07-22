package cn.hdmoney.hdy.mvp.ui;

import cn.hdmoney.hdy.Entity.Financial;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface FinancialBuyUiCallBack {
    public void confirmBuy(int bidId, double amount, String couponIds, String tradePassword);
}

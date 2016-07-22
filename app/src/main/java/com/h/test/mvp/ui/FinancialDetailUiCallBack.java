package cn.hdmoney.hdy.mvp.ui;

import cn.hdmoney.hdy.Entity.Bid;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface FinancialDetailUiCallBack {
    public void immediateBuy(Bid bid);
    public void getBidDetailInfo(int bidId);
}

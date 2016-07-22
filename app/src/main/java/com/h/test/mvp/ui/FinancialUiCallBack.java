package cn.hdmoney.hdy.mvp.ui;

import cn.hdmoney.hdy.Entity.Bid;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public interface FinancialUiCallBack {
    public void onItemFinancialClick(Bid bid);
    public void onRefresh(int bidType);
    public void onLoadMore(int bidType, int page);
    public void onDestroy();
}

package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Bid;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public interface FinancialUi extends BaseUi<FinancialUiCallBack> {
    public void showProgress();
    public void showFinancials(List<Bid> bids);
    public void showMoreFinancials(List<Bid> bids);
    public void showError(boolean isLoadMore);
}

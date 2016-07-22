package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.InvestRecord;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface FinancialDetailUi extends BaseUi<FinancialDetailUiCallBack> {
    public void showFinancialDetail(Bid bid, List<InvestRecord> investRecords);
    public void showError();
    public void gotoFinancialBuyUI();
}

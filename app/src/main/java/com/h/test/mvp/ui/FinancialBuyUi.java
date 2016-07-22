package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Account;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.Entity.Financial;
import cn.hdmoney.hdy.Entity.OrderSuccessResult;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public interface FinancialBuyUi extends BaseUi<FinancialBuyUiCallBack>{
    public void showFinancial(Bid bid, List<Coupon> coupons, Account account);
    public void showError(String message);
    public void gotoFinancialBuySuccessUI(OrderSuccessResult result);
}

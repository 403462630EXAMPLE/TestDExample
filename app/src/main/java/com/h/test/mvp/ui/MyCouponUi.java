package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Coupon;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public interface MyCouponUi extends BaseUi<MyCouponUiCallBack> {
    public void showMyRedPackets(List<Coupon> coupons);
    public void showInterestRateCoupons(List<Coupon> coupons);
}

package cn.hdmoney.hdy.mvp.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Coupon;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class CouponModel {

    @Inject
    public CouponModel() {
    }

    public List<Coupon> getMyRedPackets() {
        List<Coupon> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }

    public List<Coupon> getInterestRateCoupons() {
        List<Coupon> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }
}

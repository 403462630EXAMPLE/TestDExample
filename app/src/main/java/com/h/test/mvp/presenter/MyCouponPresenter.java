package cn.hdmoney.hdy.mvp.presenter;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.CouponModel;
import cn.hdmoney.hdy.mvp.ui.MyCouponUi;
import cn.hdmoney.hdy.mvp.ui.MyCouponUiCallBack;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyCouponPresenter extends Presenter<MyCouponUi, MyCouponUiCallBack> {
    public final static int FLAG_RED_PACKET = 0;
    public final static int FLAG_INTEREST_RATE_COUPON = 1;
    @Inject
    CouponModel model;

    private int flag;

    public MyCouponPresenter(MyCouponUi ui) {
        this(ui, FLAG_RED_PACKET);
    }

    public MyCouponPresenter(MyCouponUi ui, int flag) {
        super(ui);
        this.flag = flag;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(MyCouponUi ui) {
        if (flag == FLAG_RED_PACKET) {
            ui.showMyRedPackets(model.getMyRedPackets());
        } else {
            ui.showInterestRateCoupons(model.getMyRedPackets());
        }
    }

    @Override
    protected MyCouponUiCallBack createUiCallback(MyCouponUi ui) {
        return null;
    }
}

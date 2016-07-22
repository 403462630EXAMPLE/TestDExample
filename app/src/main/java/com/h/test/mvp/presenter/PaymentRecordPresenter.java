package cn.hdmoney.hdy.mvp.presenter;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.PaymentRecordModel;
import cn.hdmoney.hdy.mvp.ui.PaymentRecordUi;
import cn.hdmoney.hdy.mvp.ui.PaymentRecordUiCallBack;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class PaymentRecordPresenter extends Presenter<PaymentRecordUi, PaymentRecordUiCallBack> implements PaymentRecordUiCallBack {

    @Inject
    PaymentRecordModel model;

    public PaymentRecordPresenter(PaymentRecordUi ui) {
        super(ui);
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(PaymentRecordUi ui) {

    }

    @Override
    protected PaymentRecordUiCallBack createUiCallback(PaymentRecordUi ui) {
        return this;
    }

    @Override
    public void loadData(int type, int page) {

    }
}

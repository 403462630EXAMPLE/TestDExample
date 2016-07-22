package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.PaymentRecord;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public interface PaymentRecordUi extends BaseUi<PaymentRecordUiCallBack> {
    public void showPaymentRecords(List<PaymentRecord> list);
    public void showMorePaymentRecords(List<PaymentRecord> list);
    public void showError();
}

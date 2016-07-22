package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.ImmutableList;

import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.fragment.paymentRecord.PaymentRecordFragment;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class PaymentRecordPageAdapter extends FragmentPagerAdapter {

    private ImmutableList<String> titleLists = ImmutableList.of("收支", "充值", "提现");

    public PaymentRecordPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return PaymentRecordFragment.build(position == 0 ? Constants.PAYMENT_RECORD_TYPE_ALL : position == 1 ? Constants.PAYMENT_RECORD_TYPE_IN : Constants.PAYMENT_RECORD_TYPE_OUT);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleLists.get(position);
    }

    @Override
    public int getCount() {
        return titleLists.size();
    }
}

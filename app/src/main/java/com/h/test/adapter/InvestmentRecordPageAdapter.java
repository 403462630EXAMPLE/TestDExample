package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.ImmutableList;

import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.fragment.investmentRecord.InvestmentRecordListFragment;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentRecordPageAdapter extends FragmentPagerAdapter {

    private ImmutableList<String> titleLists = ImmutableList.of("在投项目", "已完成项目");

    public InvestmentRecordPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return InvestmentRecordListFragment.build(Constants.INVESTMENT_RECORD_TYPE_ONGOING);
        } else {
            return InvestmentRecordListFragment.build(Constants.INVESTMENT_RECORD_TYPE_COMPLETE);
        }
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

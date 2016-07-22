package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.InvestRecord;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.fragment.financialDetail.CollateralFragment;
import cn.hdmoney.hdy.fragment.financialDetail.InvestmentRecordFragment;
import cn.hdmoney.hdy.fragment.financialDetail.ProductDescriptionFragment;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class FinancialDetailPageAdapter extends FragmentPagerAdapter {

    private ImmutableList<String> titleLists = ImmutableList.of("担保物", "投资记录", "产品说明");
    private Bid bid;
    private List<InvestRecord> records;
    private String url;

    public void setBid(Bid bid, List<InvestRecord> records, String url) {
        this.bid = bid;
        this.records = records;
        this.url = url;
        notifyDataSetChanged();
    }

    public FinancialDetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CollateralFragment();
        } else if (position == 1) {
            return InvestmentRecordFragment.build((ArrayList<InvestRecord>) records);
        } else if (position == 2) {
            return ProductDescriptionFragment.build(url);
        } else {
            return new BaseFragment();
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

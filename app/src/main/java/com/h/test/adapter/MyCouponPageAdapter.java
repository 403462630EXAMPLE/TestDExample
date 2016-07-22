package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.ImmutableList;

import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.fragment.myCoupon.MyCouponListFragment;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyCouponPageAdapter extends FragmentPagerAdapter {

    private ImmutableList<String> titleLists = ImmutableList.of("红包", "加息券");

    public MyCouponPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MyCouponListFragment.build(Constants.MY_COUPON_TYPE_RED_PACKET);
        } else {
            return MyCouponListFragment.build(Constants.MY_COUPON_TYPE_ADD_RATE);
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

package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.common.collect.ImmutableList;

import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.fragment.myRecommend.MyRecommendListFragment;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyRecommendPageAdapter extends FragmentPagerAdapter {

    private ImmutableList<String> titleLists = ImmutableList.of("好友推荐", "奖励明细");

    public MyRecommendPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MyRecommendListFragment.build(Constants.MY_RECOMMEND_TYPE_RECOMMEND);
        } else {
            return MyRecommendListFragment.build(Constants.MY_RECOMMEND_TYPE_AWARD);
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

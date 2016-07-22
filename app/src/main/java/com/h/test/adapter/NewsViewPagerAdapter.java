package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.hdmoney.hdy.fragment.news.GonggaoFragment;

/**
 * Created by Administrator on 2016/6/17.
 */
public class NewsViewPagerAdapter extends FragmentPagerAdapter {
    private GonggaoFragment gonggaoFragment;
    private GonggaoFragment gonggaoFragment2;

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (gonggaoFragment == null) {
                    gonggaoFragment = new GonggaoFragment();
                }
                return gonggaoFragment;
            case 1:
                if (gonggaoFragment2 == null) {
                    gonggaoFragment2 = new GonggaoFragment();
                }
                return gonggaoFragment2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}

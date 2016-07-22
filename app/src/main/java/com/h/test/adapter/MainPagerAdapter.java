package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.hdmoney.hdy.fragment.ActivityFragment;
import cn.hdmoney.hdy.fragment.FinancialFragment;
import cn.hdmoney.hdy.fragment.HomeFragment;
import cn.hdmoney.hdy.fragment.MeFragment;
import cn.hdmoney.hdy.fragment.MoreFragment;


/**
 * 主页面适配器。
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM = 5;


    private HomeFragment homeFragment;
    private FinancialFragment financialFragment;
    private MoreFragment moreFragment;
    private MeFragment meFragment;
    private ActivityFragment activityFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                return homeFragment;
            case 1:
                if (financialFragment == null) {
                    financialFragment = new FinancialFragment();
                }
                return financialFragment;
            case 2:
                if (activityFragment == null) {
                    activityFragment = new ActivityFragment();
                }
                return activityFragment;

            case 3:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                }
                return meFragment;

            case 4:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                }
                return moreFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM;
    }

}

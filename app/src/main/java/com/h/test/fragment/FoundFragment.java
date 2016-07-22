package cn.hdmoney.hdy.fragment;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.FoundPagerAdapter;
import cn.hdmoney.hdy.adapter.MainPagerAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.mvp.ui.SlowViewPager;
import cn.hdmoney.hdy.utils.Logs;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FoundFragment extends BaseFragment {
    @BindView(R.id.activity)
    TextView activity;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.view_pager)
    SlowViewPager viewPager;
    private FoundPagerAdapter mPagerAdapter;

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected int getContentView() {
        return R.layout.fra_found_layout;
    }

    @Override
    protected void initViews() {
        super.initViews();
        activity.setTextSize(24);
        message.setTextSize(20);
        mPagerAdapter = new FoundPagerAdapter(getChildFragmentManager());
        viewPager.setOnPageChangeListener(onPageChangeListener);
        viewPager.setOffscreenPageLimit(MainPagerAdapter.NUM - 1);
        viewPager.setAdapter(mPagerAdapter);
    }


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectedMenu(position);
            Logs.i(position);
            if (position == 0) {
                activity.setTextSize(24);
                message.setTextSize(20);
            } else if (position == 1) {
                activity.setTextSize(20);
                message.setTextSize(24);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void selectedMenu(int position) {
        if (2 > position) {
            mPagerAdapter.notifyDataSetChanged();
        }
    }


}

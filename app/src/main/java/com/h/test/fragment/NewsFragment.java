package cn.hdmoney.hdy.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.NewsViewPagerAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/16.
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    private NewsViewPagerAdapter newsViewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.frg_news_layout;
    }

//    @Override
//    public Presenter setPresenter() {
//        return super.setPresenter();
//    }

    @Override
    protected void initViews() {
        super.initViews();

        newsViewPagerAdapter = new NewsViewPagerAdapter(getChildFragmentManager());
        viewPage.setAdapter(newsViewPagerAdapter);
        viewPage.setOffscreenPageLimit(1);
        newsViewPagerAdapter.getItem(0);
        selectTab(viewPage.getCurrentItem());
        viewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPage.setCurrentItem(position);
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void selectTab(int position) {
        tvTab1.setSelected(position == 0);
        tvTab2.setSelected(position == 1);
    }


    @OnClick({R.id.tv_tab_1, R.id.tv_tab_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tab_1:
                viewPage.setCurrentItem(0);

                break;
            case R.id.tv_tab_2:
                viewPage.setCurrentItem(1);

                break;
        }
    }
}

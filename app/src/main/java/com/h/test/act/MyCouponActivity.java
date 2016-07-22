package cn.hdmoney.hdy.act;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.MyCouponPageAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyCouponActivity extends BaseActivity  implements ViewPager.OnPageChangeListener{

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;
    @BindView(R.id.view_page)
    ViewPager viewPage;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setRightTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getApplicationContext(), "使用说明");
            }
        });
        MyCouponPageAdapter adapter = new MyCouponPageAdapter(getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        viewPage.addOnPageChangeListener(this);
        setTabSelected(viewPage.getCurrentItem());
    }

    private void setTabSelected(int position) {
        tvTab1.setSelected(position == 0);
        tvTab2.setSelected(position == 1);
    }

    @Override
    public void initData() {}

    @Override
    public int getContentView() {
        return R.layout.activity_my_coupon;
    }

    @Override
    public void onAttachedUi() {}

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTabSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

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

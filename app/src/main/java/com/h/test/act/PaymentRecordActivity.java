package cn.hdmoney.hdy.act;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.PaymentRecordPageAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class PaymentRecordActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.tv_tab_1)
    TextView tvTab1;
    @BindView(R.id.tv_tab_2)
    TextView tvTab2;
    @BindView(R.id.tv_tab_3)
    TextView tvTab3;

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
        PaymentRecordPageAdapter adapter = new PaymentRecordPageAdapter(getSupportFragmentManager());
        viewPage.setAdapter(adapter);
        viewPage.addOnPageChangeListener(this);
        viewPage.setOffscreenPageLimit(2);
        setTabSelected(viewPage.getCurrentItem());
    }

    @Override
    public void initData() {
    }

    @Override
    public int getContentView() {
        return R.layout.activity_payment_record;
    }

    private void setTabSelected(int position) {
        tvTab1.setAllCaps(true);
        tvTab1.setSelected(position == 0);
        tvTab2.setSelected(position == 1);
        tvTab3.setSelected(position == 2);
        }
    
    

    @Override
    public void onAttachedUi() {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        setTabSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    @OnClick({R.id.tv_tab_1, R.id.tv_tab_2, R.id.tv_tab_3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tab_1:
                viewPage.setCurrentItem(0);
                break;
            case R.id.tv_tab_2:
                viewPage.setCurrentItem(1);
                break;
            case R.id.tv_tab_3:
                viewPage.setCurrentItem(2);
                break;
        }
    }
}

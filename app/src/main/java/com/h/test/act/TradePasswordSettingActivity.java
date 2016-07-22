package cn.hdmoney.hdy.act;

import android.view.View;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class TradePasswordSettingActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;

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
    }

    @Override
    public void initData() {
    }

    @Override
    public int getContentView() {
        return R.layout.activity_trade_password_setting;
    }

    @Override
    public void onAttachedUi() {}

    @OnClick({R.id.nt_setting, R.id.nt_modify, R.id.nt_reset})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nt_setting:
                startActivity(TradePasswordOperationActivity.buildIntent(this, TradePasswordOperationActivity.FLAG_OPERATION_SET));
                break;
            case R.id.nt_modify:
                startActivity(TradePasswordOperationActivity.buildIntent(this, TradePasswordOperationActivity.FLAG_OPERATION_MODIFY));
                break;
            case R.id.nt_reset:
                startActivity(TradePasswordOperationActivity.buildIntent(this, TradePasswordOperationActivity.FLAG_OPERATION_RESET));
                break;
        }
    }
}

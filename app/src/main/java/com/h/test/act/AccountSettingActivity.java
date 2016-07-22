package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.view.View;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;
import com.zcw.togglebutton.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.lockpattern.LockPatternUtils;
import cn.hdmoney.hdy.view.NavigationTabView;
import cn.hdmoney.hdy.view.TitleBar;
import haibison.android.lockpattern.LockPatternActivity;
import haibison.android.lockpattern.util.AlpSettings;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class AccountSettingActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.nt_username)
    NavigationTabView ntUsername;
    @BindView(R.id.nt_phone)
    NavigationTabView ntPhone;
    @BindView(R.id.nt_bank)
    NavigationTabView ntBank;
    @BindView(R.id.nt_trade_password)
    NavigationTabView ntTradePassword;
    @BindView(R.id.nt_login_password)
    NavigationTabView ntLoginPassword;
    @BindView(R.id.nt_lock_pattern_password)
    NavigationTabView ntLockPatternPassword;
    @BindView(R.id.toggle_button)
    ToggleButton toggleButton;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ntUsername.setTvRight("hd_18111112222");
        ntPhone.setTvRight("18111112222");
        toggleButton.setToggleOn(false);
        toggleButton.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                ToastUtils.show(AccountSettingActivity.this, "toggleButton: " + on);
            }
        });
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
        return R.layout.activity_account_setting;
    }

    @Override
    public void onAttachedUi() {
    }

    @OnClick({R.id.nt_username, R.id.nt_phone, R.id.nt_bank, R.id.nt_trade_password, R.id.nt_login_password, R.id.nt_lock_pattern_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nt_username:
                startActivity(UsernameActivity.buildIntent(this, "hd_18111112222"));
                break;
            case R.id.nt_phone:
                startActivity(new Intent(this, PhoneActivity.class));
                break;
            case R.id.nt_bank:
                startActivity(new Intent(this, BankCardActivity.class));
                break;
            case R.id.nt_trade_password:
                startActivity(new Intent(this, TradePasswordSettingActivity.class));
                break;
            case R.id.nt_login_password:
                startActivity(new Intent(this, LoginPasswordOperationActivity.class));
                break;
            case R.id.nt_lock_pattern_password:
                if (!LockPatternUtils.hasLockPattern(this)) {
                    LockPatternUtils.createLockPattern(this);
                } else {
                    LockPatternUtils.modifyLockPattern(this);
                }
                break;
        }
    }
}

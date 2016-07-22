package cn.hdmoney.hdy.act;

import android.os.Bundle;

import com.liuguangqiang.android.mvp.Presenter;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.fragment.loginPasswordSetting.LoginPasswordModifyFragment;
import cn.hdmoney.hdy.utils.FragmentUtils;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class LoginPasswordOperationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new LoginPasswordModifyFragment());
        }
    }

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {}

    @Override
    public void initData() {}

    @Override
    public int getContentView() {
        return R.layout.activity_login_password_operation;
    }

    @Override
    public void onAttachedUi() {}
}

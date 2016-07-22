package cn.hdmoney.hdy.base;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.PreferencesUtils;

import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.lockpattern.LockPatternUtils;
import cn.hdmoney.hdy.utils.MyCommontUtils;
import cn.hdmoney.hdy.utils.StatusBarTintManager;
import cn.hdmoney.hdy.view.TitleBar;
import haibison.android.lockpattern.LockPatternActivity;

/**
 * Created by Administrator on 2016/5/18.
 */
public abstract class BaseActivity extends FragmentActivity implements Presenter.OnUiAttachedListener{
    private StatusBarTintManager tintManager;
    public Activity context;
    private Presenter presenter;
    private TitleBar titleBar;
    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initStatusBar();
        setStatusBarColor(getResources().getColor(R.color.title_bar));
        ButterKnife.bind(this);
        context = BaseActivity.this;
        presenter = setPresenter();
        showActionBarBack();
//        NetWorkPresenter netWorkPresenter = new NetWorkPresenter(context);
//        netWorkPresenter.judeNet();
//        DialogUtils.showLoading(context);
//        DialogUtils.hideLoading();
        initView();
        initData();
    }

    private void showActionBarBack() {
        titleBar = (TitleBar)findViewById(R.id.titlebar);
        if (titleBar != null) {
            titleBar.showLeft();
            titleBar.setLeftAction(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected boolean isCustomStautsBar() {
        return true;
    }

    protected boolean lockPattern() {
        return !LockPatternUtils.isExculdeClass(this.getClass());
    }

    private void showLockPattern() {
        if (lockPattern() && PreferencesUtils.getBoolean(this, getPackageName(), "isShouldShowLockPattern", false)) {
            PreferencesUtils.putBoolean(this, getPackageName(), "isShouldShowLockPattern", false);
            if (!LockPatternUtils.hasLockPattern(this)) {
                LockPatternUtils.createLockPattern(this);
            } else {
                LockPatternUtils.compareLockPattern(this);
            }
        } else if (getClass().getName().equals(LockPatternActivity.class.getName())) {
            PreferencesUtils.putBoolean(this, getPackageName(), "isShouldShowLockPattern", false);
        }
    }

    private void initStatusBar() {
        if (isCustomStautsBar()) {
            tintManager = onInitStatusBar();
        }
    }

    protected StatusBarTintManager onInitStatusBar() {
        StatusBarTintManager tintManager = new StatusBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.me_purple));
        return tintManager;
    }

    protected void setStatusBarColor(int color) {
        if (tintManager != null && tintManager.isStatusBarAvailable()) {
            tintManager.setStatusBarTintColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(color);
            }
        }
    }

    public void setCustomView(View v) {
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        if (null != titleBar) {
            titleBar.setCustomLayout(v);
        }
    }
    /**
     * 隐藏ActionBar中的返回按钮。
     */
    public void hideActionBarBack() {
        titleBar = (TitleBar) findViewById(R.id.titlebar);
        if (titleBar != null)
            titleBar.hideLeft();
    }
    public abstract Presenter setPresenter();

    //    设置控件属性
    protected abstract void initView();
//  请求数据
    public abstract void initData();
//  设置布局
    public abstract int getContentView();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null && !presenter.isAttachedUi()) {
            presenter.setOnUiAttachedListener(this);
            presenter.attach();
        }
//        showLockPattern();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyCommontUtils.freeToast();
    }
}

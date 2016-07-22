package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.PreferencesUtils;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.guide.ui.UserGuideActivity;
import cn.hdmoney.hdy.utils.IntentUtils;

/**
 * Created by Administrator on 2016/5/18.
 */
public class SplashActivity extends BaseActivity {


    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    @Override
    public int getContentView() {
        return R.layout.act_splash_layout;
    }
    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected boolean isCustomStautsBar() {
        return false;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PreferencesUtils.getBoolean(context, "boolean", "isFirstOpen", true)) {
                    IntentUtils.setIntent(context, UserGuideActivity.class, true);
                    PreferencesUtils.putBoolean(context, "boolean", "isFirstOpen", false);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }, 2000);

    }

    @Override
    public void initData() {
//        图片请求


    }

    @Override
    public void onAttachedUi() {

    }


}

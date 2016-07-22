package cn.hdmoney.hdy.act;

import android.os.Bundle;
import com.liuguangqiang.android.mvp.Presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.fragment.phoneSetting.PhoneBindFragment;
import cn.hdmoney.hdy.fragment.phoneSetting.StepOneFragment;
import cn.hdmoney.hdy.fragment.phoneSetting.StepThreeFragment;
import cn.hdmoney.hdy.fragment.phoneSetting.StepTwoFragment;
import cn.hdmoney.hdy.utils.FragmentUtils;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class PhoneActivity extends BaseActivity {

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (Math.random() > 0.5) {
            EventBus.getDefault().post(new PhoneSettingStepEvent(1));
        } else {
            EventBus.getDefault().post(new PhoneSettingStepEvent(0));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {}

    @Override
    public void initData() {}

    @Override
    public int getContentView() {
        return R.layout.activity_phone;
    }

    @Override
    public void onAttachedUi() {}

    @Subscribe
    public void phoneStepEvent(PhoneSettingStepEvent event) {
        if (event.step == 0) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new PhoneBindFragment());
        } else if (event.step == 1) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new StepOneFragment());
        } else if (event.step == 2) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new StepTwoFragment());
        } else if (event.step == 3) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new StepThreeFragment());
        } else if (event.step > 3) {
            finish();
        }
    }

    public static class PhoneSettingStepEvent{
        private int step;

        public PhoneSettingStepEvent(int step) {
            this.step = step;
        }
    }
}

package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.liuguangqiang.android.mvp.Presenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.fragment.tradePasswordSetting.TradePasswordModifyStepOneFragment;
import cn.hdmoney.hdy.fragment.tradePasswordSetting.TradePasswordModifyStepTwoFragment;
import cn.hdmoney.hdy.fragment.tradePasswordSetting.TradePasswordResetFragment;
import cn.hdmoney.hdy.fragment.tradePasswordSetting.TradePasswordSettingFragment;
import cn.hdmoney.hdy.utils.FragmentUtils;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class TradePasswordOperationActivity extends BaseActivity {

    public static final int FLAG_OPERATION_SET = 1;
    public static final int FLAG_OPERATION_RESET = 2;
    public static final int FLAG_OPERATION_MODIFY = 3;

    private static final String INTENT_OPERATION_TYPE = "intent_operation_type";

    public static Intent buildIntent(Context context, int flag) {
        Intent intent = new Intent(context, TradePasswordOperationActivity.class);
        intent.putExtra(INTENT_OPERATION_TYPE, flag);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int operationType = intent.getIntExtra(INTENT_OPERATION_TYPE, FLAG_OPERATION_SET);
            open(operationType, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onTradeSettingStepEvent(TradeSettingStepEvent event){
        open(event.flag, event.step);
    }

    private void open(int flag, int step) {
        if (flag == FLAG_OPERATION_SET) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new TradePasswordSettingFragment());
        } else if (flag == FLAG_OPERATION_RESET) {
            FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new TradePasswordResetFragment());
        } else {
            if (step == 1) {
                FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new TradePasswordModifyStepOneFragment());
            } else {
                FragmentUtils.pushFragment(getSupportFragmentManager(), R.id.fl_fragment_container, new TradePasswordModifyStepTwoFragment());
            }
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
        return R.layout.activity_trade_password_operation;
    }

    @Override
    public void onAttachedUi() {}

    public static class TradeSettingStepEvent{
        private int flag;
        private int step;
        public TradeSettingStepEvent(int flag) {
            this.flag = flag;
        }

        public TradeSettingStepEvent(int flag, int step) {
            this.flag = flag;
            this.step = step;
        }
    }
}

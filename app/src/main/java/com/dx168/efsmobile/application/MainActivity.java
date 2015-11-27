package com.dx168.efsmobile.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidao.efsmobile.BuildConfig;
import com.baidao.efsmobile.R;
import com.dx168.efsmobile.home.HomeFragment;
import com.dx168.efsmobile.utils.EventIDS;
import com.dx168.efsmobile.widgets.FragmentSwitcher;
import com.baidao.notification.Definition;
import com.baidao.notification.NotificationMessage;
import com.baidao.notification.NotificationType;
import com.baidao.quotation.MessageProxy;
import com.baidao.tools.BusProvider;
import com.baidao.tracker.LogData;
import com.baidao.tracker.Tracker;
import com.google.common.collect.ImmutableList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @InjectView(R.id.iv_index)
    TextView indexView;
    @InjectView(R.id.iv_quote)
    TextView quoteView;
    @InjectView(R.id.iv_find)
    TextView findView;
    @InjectView(R.id.iv_me)
    TextView meView;
    @InjectView(R.id.ll_tab_container)
    LinearLayout tabContainer;

    private int selectedIndex = 0;
    private static final String ARG_SELECTED_INDEX = "selectedIndex";

    private FragmentSwitcher switcher;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "---------------------onSaveInstance");
        outState.putInt(ARG_SELECTED_INDEX, selectedIndex);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        super.onNewIntent(intent);
    }

    private void switchActivity() {
        NotificationMessage notificationMessage = getIntent().getParcelableExtra(Definition.KEY_MESSAGE);
        if (notificationMessage == null) {
            return;
        }
        getIntent().removeExtra(Definition.KEY_MESSAGE);

        NotificationType type = notificationMessage.type;

    }

    @Override
    public void onResume() {
        super.onResume();
        switchActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Log.d(TAG, "MainActivity--onCreate");
        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_SELECTED_INDEX)) {
            selectedIndex = savedInstanceState.getInt(ARG_SELECTED_INDEX, 0);
        }

        BusProvider.getInstance().register(this);

//        DeviceTokenManager.saveDeviceToken(this, PushManager.getInstance().getClientid(this));
//
//        new UpdateManager(this);
//
//        OnlineConfigAgent.getInstance().updateOnlineConfig(this);
//        OnlineConfigAgent.getInstance().setDebugMode(true);

        //update umeng online configuration
//        MobclickAgent.updateOnlineConfig(this);

//        if (com.baidao.tools.Util.getCompanyId(this) >= Server.values()[0].serverId) {
            initFragmentSwitcher(savedInstanceState);
//        } else {
//            Toast.makeText(this, "连接网络失败，请重试", Toast.LENGTH_SHORT).show();
//        }
//
        MessageProxy.getInstance().init(getApplicationContext(), BuildConfig.VERSION_NAME);

        setTabSelected(selectedIndex);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void initFragmentSwitcher(Bundle savedInstanceState) {
        switcher = new FragmentSwitcher(getSupportFragmentManager(), R.id.rl_fragment_content);
        if (savedInstanceState == null) {
            switcher.addFragment(new HomeFragment(), "HomeFragment1");
            switcher.addFragment(new HomeFragment(), "ExpertFragment2");
            switcher.addFragment(new HomeFragment(), "MeFragment3");
            switcher.addFragment(new HomeFragment(), "MeFragment4");
        } else {
            switcher.addFragment(getSupportFragmentManager().findFragmentByTag("HomeFragment1"), "HomeFragment1");
            switcher.addFragment(getSupportFragmentManager().findFragmentByTag("ExpertFragment2"), "ExpertFragment2");
            switcher.addFragment(getSupportFragmentManager().findFragmentByTag("MeFragment3"), "MeFragment3");
            switcher.addFragment(getSupportFragmentManager().findFragmentByTag("MeFragment4"), "MeFragment4");
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            handleBack();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected BaseFragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            return (BaseFragment) switcher.getFragment(this.selectedIndex);
        } else {
            return super.getCurrentFragment();
        }
    }

    private void onAppBeInBackground() {
        isInBackground = true;
        Tracker.getInstance(this).addLog(new LogData.Builder(this).pv(EventIDS.APP_SUSPEND));
        Tracker.getInstance(this).save();
    }


    @Override
    protected void handleBack() {
        FragmentManager fm = getSupportFragmentManager();
        final int entryCount = fm.getBackStackEntryCount();
        if (entryCount == 0) {
            onAppBeInBackground();
            moveTaskToBack(true);
        } else {
            popFragment();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MessageProxy.getInstance().unsubscribe();
    }

    protected void onFragmentEmpty() {
//        DO NOTHING TO AVOID FINISHING ACTIVITY
    }

    @OnClick({R.id.iv_index, R.id.iv_quote, R.id.iv_find, R.id.iv_me})
    public void onTabClick(View v) {
        switch (v.getId()) {
            case R.id.iv_index:
                setTabSelected(0);
                break;
            case R.id.iv_quote:
                setTabSelected(1);
                break;
            case R.id.iv_find:
                setTabSelected(2);
                break;
            case R.id.iv_me:
                setTabSelected(3);
                break;
        }
    }

    public void setTabSelected(int index) {
        selectedIndex = index;
        indexView.setSelected(index == 0 ? true : false);
        quoteView.setSelected(index == 1 ? true : false);
        findView.setSelected(index == 2 ? true : false);
        meView.setSelected(index == 3 ? true : false);
        switcher.switchToFragment(index);
    }

    @Override
    protected void overrideQuiteTransition() {
        overridePendingTransition(0, 0);
    }
}

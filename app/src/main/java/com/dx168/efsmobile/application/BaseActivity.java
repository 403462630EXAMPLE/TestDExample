package com.dx168.efsmobile.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.baidao.efsmobile.BuildConfig;
import com.baidao.efsmobile.R;
import com.dx168.efsmobile.utils.EventIDS;
import com.baidao.quotation.MessageProxy;
import com.baidao.tracker.LogData;
import com.baidao.tracker.Tracker;
import com.tencent.stat.StatService;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by burizado on 14-12-15.
 */
public class BaseActivity extends ActionBarActivity {
    private static final String TAG = "BaseActivity";
    private int stackSize;
    protected ExitController exitCtrl = new ExitController();
    protected static boolean isInBackground = false;

    protected boolean forceAuthChat() {
        return false;
    }

    public class ExitController {
        private static final int TIME_GAP = 5000;
        private long lastBackEventTime;

        public boolean requestExit() {
            long currentTime = System.currentTimeMillis();
            if (lastBackEventTime == 0 || currentTime <= lastBackEventTime || (currentTime - lastBackEventTime) >= TIME_GAP) {
                lastBackEventTime = currentTime;
                Toast.makeText(BaseActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();

                return false;
            }

            try {
                return true;
            } finally {
                lastBackEventTime = 0;
            }
        }

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                BaseFragment fragment = getCurrentFragment();
                int newStackSize = getSupportFragmentManager().getBackStackEntryCount();
                fragment.onStackTop(newStackSize < stackSize);
                stackSize = newStackSize;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        StatService.onResume(this);
        if(isInBackground){
            isInBackground = false;
            Tracker.getInstance(this).addLog(new LogData.Builder(this).pv(EventIDS.APP_RESUME));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        StatService.onPause(this);
        Tracker.getInstance(this).addLog(new LogData.Builder(this).pv(EventIDS.APP_PAUSE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(serviceConnection);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        View v = findViewById(R.id.toolbar);
        if (v != null) {
            Toolbar toolbar = (Toolbar) v;
            initToolBar(toolbar);
        }
    }

    private void initToolBar(Toolbar toolbar) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            onInitToolBar(toolbar);
        }
    }

    protected void onInitToolBar(Toolbar toolbar) {}

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        TextView titleView = (TextView)findViewById(R.id.toolbar_title);
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    protected void pushFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.right_to_left_enter, R.anim.left_to_right_exit, R.anim.pop_left_to_right_enter, R.anim.pop_left_to_right_exit);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.rl_fragment_content, fragment);
        ft.addToBackStack(fragment.getName());
        ft.commit();
    }

    protected boolean popFragment() {
        FragmentManager fm = getSupportFragmentManager();
        final int entryCount = fm.getBackStackEntryCount();
        FragmentTransaction ft = fm.beginTransaction();
        boolean popSucceed = true;
        if (entryCount <= 1) {
            fm.popBackStack();
        } else {
            popSucceed = fm.popBackStackImmediate();
        }
        ft.commit();
        if (popSucceed && entryCount <= 1) {
            onFragmentEmpty();
        }

        return popSucceed;
    }

    protected void onFragmentEmpty() {
        finish();
    }

    protected void clearFragmentStack() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected BaseFragment getCurrentFragment() {
        FragmentManager fm = getSupportFragmentManager();
        return (BaseFragment) fm.findFragmentById(R.id.rl_fragment_content);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handleBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                handleBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        BaseFragment currentFragment = getCurrentFragment();
        if (currentFragment != null && currentFragment.handleDispatchKeyEvent(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    protected void handleBack() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        BaseFragment currentFragment = getCurrentFragment();

        try {
            if (currentFragment != null) {
                if (!currentFragment.handleBack()) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        popFragment();
                    } else {
                        if (exitCtrl.requestExit()) {
                            exit();
                        }
                    }
                }
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            final InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void exit() {
        finish();
    }

    protected void overrideQuiteTransition(){
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish(){
        super.finish();
        overrideQuiteTransition();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MessageProxy.getInstance().init(getApplicationContext(), BuildConfig.VERSION_NAME);
    }
}

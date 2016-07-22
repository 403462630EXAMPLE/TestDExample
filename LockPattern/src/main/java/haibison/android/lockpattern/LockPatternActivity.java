package haibison.android.lockpattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import haibison.android.lockpattern.util.AlpSettings;
import haibison.android.lockpattern.widget.LockPatternView;


public class LockPatternActivity extends BaseActivity implements OnClickListener {

    private static final String RETRY_COUNT = LockPatternActivity.class.getName() + ".retry_count";

    private LockPatternView lockPatternView;
    private TextView noSettingView;
    private TextView forgetView;
    private TextView hint1View;
    private TextView hint2View;
    private View leftView;


    private final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            lockPatternView.clearPattern();
            onPatternCleared();
        }

    };

    private void setRetryCount(int count) {
        SharedPreferences.Editor editor = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE).edit();
        editor.putInt(RETRY_COUNT, count);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_pattern);
        View titleBar = findViewById(R.id.title_bar);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern_view);
        noSettingView = (TextView) findViewById(R.id.tv_no_setting);
        forgetView = (TextView) findViewById(R.id.tv_forget);
        hint1View = (TextView) findViewById(R.id.tv_top_hint1);
        hint2View = (TextView) findViewById(R.id.tv_top_hint2);
        leftView = findViewById(R.id.tv_left);

        noSettingView.setOnClickListener(this);
        forgetView.setOnClickListener(this);
        leftView.setOnClickListener(this);

        init(lockPatternView);
        setUpProgressView(findViewById(R.id.rl_progress_bar_container));

        if (getLockPatternType() == LockPatternType.CREATE_PATTERN) {
            noSettingView.setVisibility(View.VISIBLE);
        } else if (getLockPatternType() == LockPatternType.COMPARE_PATTERN) {
            if (!isModify()) {
                titleBar.setVisibility(View.GONE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
            retryCount = getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE).getInt(RETRY_COUNT, 0);
            if (retryCount >= maxRetries) {
                lockPatternView.setEnabled(false);
                hint2View.setText(retryCount + "次密码输入错误");
                hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_error));
            }
            forgetView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onInit(LockPatternType lockPatternType) {
        super.onInit(lockPatternType);
        lockPatternView.setEnabled(true);
        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
        initHintView(lockPatternType);
    }

    private void initHintView(LockPatternType lockPatternType) {
        switch (lockPatternType) {
            case CREATE_PATTERN:
                hint2View.setText("绘制解锁图案 最少连接" + AlpSettings.Display.getMinWiredDots(this) + "个点");
                break;
            case COMPARE_PATTERN:
                if (isModify()) {
                    hint2View.setText("绘制旧的解锁图案");
                } else {
                    hint1View.setText("Hi!186****9196");
                    hint2View.setText("请绘制手势密码");
                }
                break;
            case VERIFY_CAPTCHA:
                hint2View.setText("请绘制手势密码");
                break;
        }
    }

    @Override
    protected void onPatternStart(LockPatternType lockPatternType) {
        super.onPatternStart(lockPatternType);
//        if (lockPatternType == LockPatternType.CREATE_PATTERN && nextView.getVisibility() == View.VISIBLE) {
//            getIntent().removeExtra(EXTRA_PATTERN);
//        }
        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
        hint2View.setText("完成后松开手指");
    }

    @Override
    protected void onPatternCleared(LockPatternType lockPatternType) {
        super.onPatternCleared(lockPatternType);
//        if (lockPatternType == LockPatternType.CREATE_PATTERN) {
//            confirmView.setVisibility(View.GONE);
//        }
//        if (retryCount < maxRetries) {
//            hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
//            initHint2View(lockPatternType);
//        }
    }

    @Override
    protected void doLockPatternResult(ResuleType resuleType) {
        switch (resuleType) {
            case MIN_DOTS_FAIL:
                hint2View.setText("至少连接" + minWiredDots + "个点,请重试");
                hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_error));
                break;
            case PATTERN_CREATE:
                hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                hint2View.setText("已记录图案");
                lockPatternView.setEnabled(false);
                lockPatternView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lockPatternView.setEnabled(true);
                        hint2View.setText("再次绘制图案");
                        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                        lockPatternView.clearPattern();
                    }
                }, 1000);
                break;
            case COMPARE_OK:
                setRetryCount(0);
                if (getLockPatternType() == LockPatternType.CREATE_PATTERN) {
                    lockPatternView.setEnabled(false);
                    hint2View.setText("手势图案设置成功");
                    hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                    lockPatternView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishWithResultOk(getIntent().getCharArrayExtra(EXTRA_PATTERN));
                        }
                    }, 1000);
                } else {
                    if (isModify()) {
                        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                        hint2View.setText("手势密码一致");
                        lockPatternView.setEnabled(false);
                        lockPatternView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lockPatternView.setEnabled(true);
                                setLockPatternType(LockPatternType.CREATE_PATTERN);
                                lockPatternView.clearPattern();
                                getIntent().removeExtra(EXTRA_PATTERN);
                                hint2View.setText("绘制解锁图案 最少连接" + minWiredDots + "个点");
                                hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                            }
                        }, 1000);
                    } else {
                        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_regular));
                        hint2View.setText("手势密码一致");
                        finishWithResultOk(null);
                    }
                }
                break;
            case COMPARE_FAIL:
                if (getLockPatternType() == LockPatternType.CREATE_PATTERN) {
                    hint2View.setText("与上次绘制不一致,请重试");
                    hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_error));
                } else {
                    setRetryCount(retryCount);
                    if (retryCount >= maxRetries) {
                        lockPatternView.setEnabled(false);
                        hint2View.setText(retryCount + "次解锁图案绘制错误");
                        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_error));
//                        finishWithNegativeResult(RESULT_FAILED);
                    } else {
//                        headerView.setText("还有" + (maxRetries - retryCount) + "次尝试机会");
                        hint2View.setText("输入错误,请重试");
                        hint2View.setTextColor(getResources().getColor(R.color.lock_pattern_head_hint_color_error));
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_forget) {
            finishWithForgotPatternResult(RESULT_FORGOT_PATTERN);
        } else if (i == R.id.tv_no_setting) {
            finishWithNegativeResult(RESULT_CANCELED);
        } else if (i == R.id.tv_left) {
            finishWithNegativeResult(RESULT_CANCELED);
        }
    }
}

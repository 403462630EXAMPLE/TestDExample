package cn.hdmoney.hdy.lockpattern;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.liuguangqiang.framework.utils.PreferencesUtils;
import com.liuguangqiang.framework.utils.ToastUtils;

import cn.hdmoney.hdy.app.MyApplication;
import haibison.android.lockpattern.BaseActivity;
import haibison.android.lockpattern.LockPatternActivity;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class LockPatternResultReceiver extends ResultReceiver {
    public LockPatternResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultData != null) {
            BaseActivity.LockPatternType lockPatternType = (BaseActivity.LockPatternType) resultData.getSerializable(LockPatternActivity.EXTRA_LOCK_PATTERN_TYPE);
            boolean isModify = resultData.getBoolean(LockPatternActivity.EXTRA_IS_MODIFY);
            int retryCount = resultData.getInt(LockPatternActivity.EXTRA_RETRY_COUNT);
            char[] pattern = resultData.getCharArray(LockPatternActivity.EXTRA_PATTERN);

            switch (lockPatternType) {
                case CREATE_PATTERN:
                    if (isModify) {
                        modifyPatternResult(MyApplication.from(), resultCode, 0, pattern);
                    } else {
                        createPatternResult(MyApplication.from(), resultCode, pattern);
                    }
                    break;
                case COMPARE_PATTERN:
                    if (isModify) {
                        modifyPatternResult(MyApplication.from(), resultCode, retryCount, null);
                    } else {
                        comparePatternResult(MyApplication.from(), resultCode, retryCount);
                    }
                    break;
                case VERIFY_CAPTCHA:
                    break;
            }
        }
    }

    private void createPatternResult(Context context, int resultCode, char[] pattern){
        if (resultCode == Activity.RESULT_OK) {
            ToastUtils.show(context, "手势密码设置成功");
        }
    }

    private void modifyPatternResult(Context context, int resultCode, int retryCount, char[] pattern) {
        if (resultCode == Activity.RESULT_OK) {
            ToastUtils.show(context, "手势密码修改成功");
        } else if (resultCode == LockPatternActivity.RESULT_FORGOT_PATTERN) {
            LockPatternUtils.clearLockPattern(context);
        }
    }

    private void comparePatternResult(Context context, int resultCode, int retryCount) {
        if (resultCode == Activity.RESULT_OK) {

        } else if (resultCode == LockPatternActivity.RESULT_FORGOT_PATTERN) {
            LockPatternUtils.clearLockPattern(context);
        } else {

        }
    }
}

package cn.hdmoney.hdy.lockpattern;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.google.common.collect.ImmutableList;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.LoginActivity;
import cn.hdmoney.hdy.act.RegistActivity;
import cn.hdmoney.hdy.act.SplashActivity;
import cn.hdmoney.hdy.guide.ui.UserGuideActivity;
import haibison.android.lockpattern.LockPatternActivity;
import haibison.android.lockpattern.util.Alp;
import haibison.android.lockpattern.util.AlpSettings;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class LockPatternUtils {
    public static final int REQUEST_CREATE_PATTERN = 1;
    public static final int REQUEST_MODIFY_PATTERN = 2;
    private static ImmutableList<String> excludeList = ImmutableList.of(
            SplashActivity.class.getName(),
            UserGuideActivity.class.getName(),
            LoginActivity.class.getName(),
            RegistActivity.class.getName(),
            LockPatternActivity.class.getName()
    );

    public static void createLockPattern(Context context) {
        Intent intent = new Intent(context, LockPatternActivity.class);
        intent.putExtra(LockPatternActivity.EXTRA_LOCK_PATTERN_TYPE, LockPatternActivity.LockPatternType.CREATE_PATTERN);
        intent.putExtra(LockPatternActivity.EXTRA_RESULT_RECEIVER, new LockPatternResultReceiver(new Handler()));
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(intent, REQUEST_CREATE_PATTERN);
        } else {
            context.startActivity(intent);
        }
    }
    public static void modifyLockPattern(Context context) {
        Intent intent = new Intent(context, LockPatternActivity.class);
        intent.putExtra(LockPatternActivity.EXTRA_RESULT_RECEIVER, new LockPatternResultReceiver(new Handler()));
        intent.putExtra(LockPatternActivity.EXTRA_IS_MODIFY, true);
        Intent loginIntent = new Intent(context.getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, loginIntent, 0);
        intent.putExtra(LockPatternActivity.EXTRA_PENDING_INTENT_FORGOT_PATTERN, pendingIntent);
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(intent, REQUEST_MODIFY_PATTERN);
        } else {
            context.startActivity(intent);
        }
    }
    public static void compareLockPattern(Context context) {
        Intent intent = new Intent(context, LockPatternActivity.class);
        intent.putExtra(LockPatternActivity.EXTRA_RESULT_RECEIVER, new LockPatternResultReceiver(new Handler()));
        intent.putExtra(LockPatternActivity.EXTRA_LOCK_PATTERN_TYPE, LockPatternActivity.LockPatternType.COMPARE_PATTERN);
        intent.putExtra(LockPatternActivity.EXTRA_IS_FORBIDDEN_BACK, true);
        Intent loginIntent = new Intent(context.getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, loginIntent, 0);
        intent.putExtra(LockPatternActivity.EXTRA_PENDING_INTENT_FORGOT_PATTERN, pendingIntent);
        if (context instanceof Activity) {
            ((Activity)context).startActivityForResult(intent, REQUEST_MODIFY_PATTERN);
        } else {
            context.startActivity(intent);
        }
    }

    public static boolean isExculdeClass(Class c) {
        return excludeList.contains(c.getName());
    }

    //不同账号的手势密码标记不一致
    public static void setFlag(Context context, String flag) {
        AlpSettings.Security.setFlag(context, flag);
    }

    public static boolean hasLockPattern(Context context) {
        return AlpSettings.Security.getPattern(context) != null;
    }

    public static void clearLockPattern(Context context) {
        AlpSettings.Security.setPattern(context, null);
    }

    public static void saveLockPattern(Context context, char[] patterns) {
        AlpSettings.Security.setPattern(context, patterns);
    }

    public static void init(Context context) {
        AlpSettings.Security.setAutoSavePattern(context, true); //是否自动保存手势
        AlpSettings.Display.setMaxRetries(context, Integer.MAX_VALUE); //最大尝试错误次数
        AlpSettings.Display.setMinWiredDots(context, 4);//最少连接多少个点
    }
}

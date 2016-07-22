package cn.hdmoney.hdy.utils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IntentUtils {

//	/**
//	 * 跳转到报料页面
//	 *
//	 * @param activity
//	 */
//	public static void toUploadActivity(Activity activity) {
//		//旧：http://jrsx.strtv.cn:7337
//		Intent intent = new Intent(activity, ServiceWebViewActivity.class);
//		intent.putExtra("url", "http://olive1.strtv.cn:7337/jrsx/jrsx.php?jrsx=b1");
//		intent.putExtra("title", "新闻报料");
//		activity.startActivity(intent);
//		activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
//	}
	
	// 跳转方法
    public static void setIntent(Activity activity, Class<?> targetActivity) {
        Intent intent = new Intent(activity, targetActivity);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }
    // 跳转方法
    public static void setIntent(Activity activity, Class<?> targetActivity,String key,String value) {
        Bundle bundle = new Bundle();
        bundle.putString(key,value);
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//        activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
    }

    //
    public static void setIntent(Activity activity, Class<?> targetActivity, Bundle b) {
        Intent intent = new Intent(activity, targetActivity);
        intent.putExtras(b);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void setIntent(Activity activity, Class<?> targetActivity, boolean isFinish) {
        if (isFinish) {
            activity.finish();
        }
        setIntent(activity, targetActivity);
    }

    public static void finishActivity(Activity activity) {
        activity.finish();
    }
}

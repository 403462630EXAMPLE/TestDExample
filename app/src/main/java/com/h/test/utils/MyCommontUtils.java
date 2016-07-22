package cn.hdmoney.hdy.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/21.
 */
public class MyCommontUtils {
    private static Toast toast;

    public static void print(String name, String str){
        System.out.print(name + "---------"+str);
    }
    public static void makeToast(Context context,String str) {
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    public static void makeToast(Context context,int str) {
        Toast.makeText(context,context.getString(str),Toast.LENGTH_SHORT).show();
    }
    public static void freeToast() {
        toast = null;
        System.gc();
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

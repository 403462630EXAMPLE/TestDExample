package cn.hdmoney.hdy.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class SpannableUtils {

    public static void setTextSize(SpannableString span, int startIndex, int endIndex, int fontSize) {
        if(TextUtils.isEmpty(span.toString()) || fontSize <= 0 || startIndex >= endIndex || startIndex < 0 || endIndex > span.length()){
            return ;
        }
        span.setSpan(new AbsoluteSizeSpan(fontSize), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void setTextForeground(SpannableString span, int startIndex, int endIndex, int foregroundColor){
        if(TextUtils.isEmpty(span.toString()) || startIndex < 0 || endIndex > span.length() || startIndex >= endIndex ){
            return ;
        }
        span.setSpan(new ForegroundColorSpan(foregroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void setTextBackground(SpannableString span, int startIndex, int endIndex, int backgroundColor){
        if( TextUtils.isEmpty(span.toString()) || startIndex < 0 || endIndex > span.length() || startIndex >= endIndex ){
            return ;
        }
        span.setSpan(new BackgroundColorSpan(backgroundColor), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}

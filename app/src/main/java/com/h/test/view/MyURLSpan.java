package cn.hdmoney.hdy.view;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import cn.hdmoney.hdy.act.WebViewActivity;
import cn.hdmoney.hdy.utils.IntentUtils;

/**
 * Created by Administrator on 2016/6/7.
 */
public class MyURLSpan extends ClickableSpan {

    private final Activity activity;
    private final String url;

    public MyURLSpan(Activity act,String url) {
        this.activity = act;
        this.url = url;
    }

    @Override
    public void onClick(View widget) {
        IntentUtils.setIntent(activity, WebViewActivity.class,"url",url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#7ABFFF"));
        ds.setUnderlineText(true);
    }
}

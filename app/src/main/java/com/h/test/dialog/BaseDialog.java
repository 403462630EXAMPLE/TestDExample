package cn.hdmoney.hdy.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        this(context, R.style.dialog);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        initView();
    }

    public abstract void initView();

    public abstract int getLayoutResource();
}

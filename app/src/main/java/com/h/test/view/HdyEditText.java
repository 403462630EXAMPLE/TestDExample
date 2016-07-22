package cn.hdmoney.hdy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class HdyEditText extends EditText {
    public HdyEditText(Context context) {
        super(context);
    }

    public HdyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HdyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}

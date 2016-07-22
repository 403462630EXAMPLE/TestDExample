package cn.hdmoney.hdy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/6/29 0029.
 */
public class NestedScrollView extends ScrollView {
    private float lastY;
    private float lastX;
    private boolean flag;
    public NestedScrollView(Context context) {
        super(context);
    }

    public NestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                lastX = ev.getX();
                flag = false;
                break;
            case MotionEvent.ACTION_UP:
                flag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = ev.getY();
                float newX = ev.getX();
                float distanceY = lastY - newY;
                float distanceX = lastX - newX;
                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    if (distanceY > 0) {
                        flag = canScrollVertically(1);
                    } else if (distanceY < 0) {
                        flag = canScrollVertically(1);
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev) || flag;
    }
}

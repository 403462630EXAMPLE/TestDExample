package cn.hdmoney.hdy.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/6/24 0024.
 */
public class ScrollWebView extends WebView {
    private float lastX;
    private float lastY;

    public ScrollWebView(Context context) {
        super(context);
    }

    public ScrollWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = e.getY();
                float newX = e.getX();
                float distanceY = lastY - newY;
                float distanceX = lastX - newX;
                if (Math.abs(distanceX) < Math.abs(distanceY)) {
                    if (distanceY < 0) {
                        if (isScrollToTop()) {
                            requestDisallowInterceptTouchEvent(false);
                        } else {
                            requestDisallowInterceptTouchEvent(true);
                        }
                    } else {
                        if (isScrollToBottom()) {
                            requestDisallowInterceptTouchEvent(false);
                        } else {
                            requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    public boolean isScrollToBottom() {
        return !canScrollVertically(1);
    }

    public boolean isScrollToTop() {
        return !canScrollVertically(-1);
    }
}

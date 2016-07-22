package cn.hdmoney.hdy.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class ScrollRecycleView extends RecyclerView {
    private float lastX;
    private float lastY;

    public ScrollRecycleView(Context context) {
        super(context);
    }

    public ScrollRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
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

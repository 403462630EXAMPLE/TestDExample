package com.dx168.efsmobile.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by burizado on 15-1-16.
 */

public class KeyboardDetectorRelativeLayout extends RelativeLayout {

    private OnSoftKeyboardListener listener;

    public KeyboardDetectorRelativeLayout(Context context) {
        this(context, null);
    }

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardDetectorRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int proposedheight = MeasureSpec.getSize(heightMeasureSpec);
        final int actualHeight = getHeight();

        int diff = actualHeight - proposedheight;

        if (actualHeight > proposedheight) {
            listener.onShown();
        } else {
            listener.onHidden();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null != listener) {
            listener.onMeasureFinished();
        }
    }

    public void setOnSoftKeyboardListener(OnSoftKeyboardListener listener) {
        this.listener = listener;
    }

    public static interface OnSoftKeyboardListener {
        public void onShown();

        public void onHidden();

        public void onMeasureFinished();
    }
}

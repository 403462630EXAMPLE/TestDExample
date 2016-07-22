package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class CircleBgView extends LinearLayout {
    private static final String TAG = "CircleBgView";
    private final int DEFAULT_SROKE_WIDTH = 10;
    private final int DEFAULT_BG_COLOR = Color.parseColor("#ffe1e1e1");
    private final int DEFAULT_FG_COLOR = Color.parseColor("#ffd77441");
    private RectF oval;
    private Paint paint;
    private int strokeWidth = DEFAULT_SROKE_WIDTH;
    private int bgColor = DEFAULT_BG_COLOR;
    private int fgColor = DEFAULT_FG_COLOR;
    private float percent = 0;

    public CircleBgView(Context context) {
        this(context, null);
    }

    public CircleBgView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBgView);
        strokeWidth = (int) typedArray.getDimension(R.styleable.CircleBgView_circle_stroke_width, DEFAULT_SROKE_WIDTH);
        percent = typedArray.getFloat(R.styleable.CircleBgView_circle_precent, 0);
        bgColor = typedArray.getColor(R.styleable.CircleBgView_circle_bg_color, DEFAULT_BG_COLOR);
        fgColor = typedArray.getColor(R.styleable.CircleBgView_circle_fg_color, DEFAULT_FG_COLOR);
        typedArray.recycle();

        initPaint();
        setWillNotDraw(false);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        paint.setColor(bgColor);
        canvas.drawArc(oval, 0, 360, false, paint);

        paint.setColor(fgColor);
        canvas.drawArc(oval, -90, percent * 3.6f, false, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, w + "**" + h + "**" + oldw + "**" + oldh);
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingBottom() + getPaddingTop());

        float wwd = (float)w - xpad;

        float hhd = (float)h - ypad;
        oval = new RectF(getPaddingLeft() + strokeWidth, getPaddingTop() + strokeWidth, getPaddingLeft() + wwd - strokeWidth * 2, getPaddingTop() + hhd - strokeWidth * 2);
    }

    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
    }
}

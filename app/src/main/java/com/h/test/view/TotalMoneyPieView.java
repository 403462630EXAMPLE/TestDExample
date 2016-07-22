package cn.hdmoney.hdy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Random;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class TotalMoneyPieView extends View {

    private Paint paint;

    private float enableNum = 25;
    private float inNum = 25;
    private float frozenNum = 25;

    private int enableColor;
    private int frozenColor;
    private int inColor;

    public void setNum(float enableNum, float frozenNum, float inNum) {
        this.enableNum = enableNum;
        this.frozenNum = frozenNum;
        this.inNum = inNum;
        invalidate();
    }

    public TotalMoneyPieView(Context context) {
        this(context, null);
    }

    public TotalMoneyPieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TotalMoneyPieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        enableColor = getResources().getColor(R.color.enable_money);
        frozenColor = getResources().getColor(R.color.frozen_money);
        inColor = getResources().getColor(R.color.in_money);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int strokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics());
        paint.setStrokeWidth(strokeWidth);

        int left = getPaddingLeft() + strokeWidth / 2 + 1;
        int top = getPaddingTop() + strokeWidth / 2 + 1;
        int right = width - getPaddingRight() - strokeWidth / 2 - 1;
        int bottom = height - getPaddingBottom() - strokeWidth / 2 - 1;
        RectF rectF = new RectF(left, top, right, bottom);

        float totalNum = enableNum + frozenNum + inNum;

        int startAngle = -new Random().nextInt(180);
        float enableAngle = enableNum / totalNum * 360;
        float frozenAngle = frozenNum / totalNum * 360;
        float inAngle = inNum / totalNum * 360;
        if (inAngle != 0) {
            inAngle = Math.max(360 - enableAngle - frozenAngle, inAngle);
        }

        if (inAngle < 0) {
            inAngle = 0;
        }
        if (enableAngle != 0) {
            paint.setColor(enableColor);
            canvas.drawArc(rectF, startAngle, enableAngle + 1, false, paint);
        }

        startAngle += enableAngle;
        if (frozenAngle != 0) {
            paint.setColor(frozenColor);
            canvas.drawArc(rectF, startAngle, frozenAngle + 1, false, paint);
        }

        startAngle += frozenAngle;
        if (inAngle != 0) {
            paint.setColor(inColor);
            canvas.drawArc(rectF, startAngle, inAngle + 1, false, paint);
        }
    }
}

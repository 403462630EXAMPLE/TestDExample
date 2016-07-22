package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class HdyProgressView extends View {
    private Paint paint;
    private Paint spotPaint;

    private int bgColor = Color.parseColor("#efeff4");
    private int progressColor = Color.parseColor("#ffc751");
    private int spotColor = Color.parseColor("#ffc751");
    private float progressWidth = 14;
    private float spotWidth = 4;

    private int max = 100;
    private int progress;

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public HdyProgressView(Context context) {
        this(context, null);
    }

    public HdyProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HdyProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HdyProgressView);
        progress = typedArray.getInteger(R.styleable.HdyProgressView_hdy_progress, 0);
        max = typedArray.getInteger(R.styleable.HdyProgressView_hdy_max, 100);
        progressWidth = typedArray.getDimension(R.styleable.HdyProgressView_hdy_progressWidth, 14);
        spotWidth = typedArray.getDimension(R.styleable.HdyProgressView_hdy_spotWidth, 4);
        bgColor = typedArray.getColor(R.styleable.HdyProgressView_hdy_bgColor, Color.parseColor("#efeff4"));
        progressColor = typedArray.getColor(R.styleable.HdyProgressView_hdy_progressColor, Color.parseColor("#ffc751"));
        spotColor = typedArray.getColor(R.styleable.HdyProgressView_hdy_spotColor, progressColor);
        typedArray.recycle();
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height ;
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        } else{
            height = (int) (progressWidth + spotWidth * 2);
        }
        setMeasuredDimension(widthSize, height);
    }

    private void initPaint() {
        paint = new Paint();
//      抗锯齿
        paint.setAntiAlias(true);
        paint.setStrokeWidth(progressWidth);
//        交界处锐角或圆弧
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);

        spotPaint = new Paint();
        spotPaint.setAntiAlias(true);
        spotPaint.setStrokeWidth(spotWidth);
        spotPaint.setStyle(Paint.Style.STROKE);
        spotPaint.setStrokeCap(Paint.Cap.ROUND);
        spotPaint.setColor(spotColor);
    }

    private float getScale() {
        double scale = progress * 1.0 / max;
        if (scale > 1) {
            scale = 1;
        }
        return (float) scale;
    }

    private void adjustmentSize() {
        if (getHeight() < progressWidth + spotWidth * 2) {
            float distance = progressWidth + spotWidth * 2 - getHeight();
            progressWidth -= distance;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        adjustmentSize();
        float radius = (progressWidth + spotWidth) / 2;
        float paddingLeft = getPaddingLeft() + radius + spotWidth / 2;
        float paddingRight = getPaddingRight() + radius + spotWidth / 2;
        float paddingTop = getPaddingTop();
        float paddingBottom = getPaddingBottom();

        int width = getWidth();
        int height = getHeight();
        RectF rectF = new RectF(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);

        paint.setStrokeWidth(progressWidth);
        paint.setColor(bgColor);
        canvas.drawLine(rectF.left, rectF.height() / 2, rectF.right, rectF.height() / 2, paint);
        paint.setColor(progressColor);
        float stopX = getScale() * rectF.width() + rectF.left;
        if (stopX != rectF.left) {
            canvas.drawLine(rectF.left, rectF.height() / 2, stopX, rectF.height() / 2, paint);
        }

        if (progress == 0 || progress >= max) {
            return ;
        }
        float startX = getScale() * rectF.width() + rectF.left;
        spotPaint.setStrokeWidth(spotWidth);
        spotPaint.setStyle(Paint.Style.FILL);
        spotPaint.setColor(Color.WHITE);
        canvas.drawCircle(startX, rectF.height() / 2, radius - 1, spotPaint);
        spotPaint.setStyle(Paint.Style.STROKE);
        spotPaint.setColor(spotColor);
        canvas.drawCircle(startX, rectF.height() / 2, radius - 1, spotPaint);
    }
}

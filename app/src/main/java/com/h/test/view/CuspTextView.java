package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class CuspTextView extends TextView {

    private final int DEFAULT_CUSP_WIDTH = 40;
    private Paint paint;
    private int cuspWidth = DEFAULT_CUSP_WIDTH;
    private int cuspColor = 0;

    public CuspTextView(Context context) {
        this(context, null);
    }

    public CuspTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CuspTextView);
        cuspWidth = (int) typedArray.getDimension(R.styleable.CuspTextView_cusp_width, 0);
        cuspColor = typedArray.getColor(R.styleable.CuspTextView_cusp_color, 0);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(cuspColor);
    }

    public void setCuspWidth(int cuspWidth) {
        this.cuspWidth = cuspWidth;
        setPadding(getPaddingLeft() + this.cuspWidth, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cuspColor != 0) {
            Path path1 = new Path();
            path1.moveTo(0, 0);
            path1.lineTo(cuspWidth, 0);
            path1.lineTo(cuspWidth, getMeasuredHeight()/2);
            path1.close();
            canvas.drawPath(path1, paint);

            Path path2 = new Path();
            path2.moveTo(0, getMeasuredHeight());
            path2.lineTo(cuspWidth, getMeasuredHeight());
            path2.lineTo(cuspWidth, getMeasuredHeight()/2);
            path2.close();
            canvas.drawPath(path2, paint);

            canvas.drawRect(cuspWidth, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        }
        super.onDraw(canvas);
    }
}

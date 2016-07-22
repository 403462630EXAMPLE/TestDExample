package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class SpotView extends View {

    private Paint paint;

    public SpotView(Context context) {
        this(context, null);
    }

    public SpotView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpotView);
        int spotColor = typedArray.getColor(R.styleable.SpotView_spot_color, 0);
        typedArray.recycle();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(spotColor);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        int radius = width <= height ? width / 2 : height / 2;

        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }
}

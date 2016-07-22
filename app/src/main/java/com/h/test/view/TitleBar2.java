package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.hdmoney.hdy.R;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 自定义标题栏
 * Created by WZT on 2016/4/13.
 */
public class TitleBar2 extends LinearLayout {

    public static final int FLAG_ICON = 1;
    public static final int FLAG_TEXT = 2;
    public static final int FLAG_ALL = 3;
    public static final int FLAG_NONE = 0;

    private ImageView ivLeft;
    private TextView tvLeft;

    private TextView tvTitle;

    private ImageView ivRight;
    private TextView tvRight;

    private LinearLayout customLayout;
    private CircleImageView ivHead;

    public TitleBar2(Context context) {
        super(context);
        init(context);
    }

    public TitleBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        String leftText = typedArray.getString(R.styleable.TitleBar_left_text);
        int leftDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_left_icon, R.mipmap.ic_back);
        int ivhead = typedArray.getResourceId(R.styleable.TitleBar_iv_head, R.mipmap.countset_img);

        String title = typedArray.getString(R.styleable.TitleBar_bar_title);

        int rightDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_right_icon, 0);
        String rightText = typedArray.getString(R.styleable.TitleBar_right_text);

        int leftShow = typedArray.getInt(R.styleable.TitleBar_left_show, 1);
        int rightShow = typedArray.getInt(R.styleable.TitleBar_right_show, 0);

        typedArray.recycle();

        showLeft(leftShow);
        showRight(rightShow);
        setLeftText(leftText);
        tvTitle.setText(title);
        setLeftIcon(leftDrawableRes);
        setRightIcon(rightDrawableRes);
        setRightText(rightText);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_titlebar2, this, true);

        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvLeft = (TextView) findViewById(R.id.tv_left);
        ivHead = (CircleImageView) findViewById(R.id.iv_head);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        ivRight = (ImageView) findViewById(R.id.iv_right);
        tvRight = (TextView) findViewById(R.id.tv_right);

        customLayout = (LinearLayout) findViewById(R.id.layout_custom);
    }

    /**
     * 设置标题
     *
     * @param stringRes
     */
    public void setTitle(@NonNull int stringRes) {
        customLayout.removeAllViews();
        customLayout.addView(tvTitle);
        tvTitle.setText(stringRes);
    }


    public void setTitle(@NonNull String titleStr) {
        customLayout.removeAllViews();
        customLayout.addView(tvTitle);
        tvTitle.setText(titleStr);
    }

    /**
     * 设置左边圆角图片
     * @param imgRes
     */
    public void setLeftHead(@NonNull int imgRes) {
        ivHead.setVisibility(View.VISIBLE);
        ivHead.setImageResource(imgRes);
    }


    /**
     * 设置左边图标
     *
     * @param imgRes
     */
    public void setLeftIcon(@NonNull int imgRes) {
        ivLeft.setImageResource(imgRes);
    }

    public void setLeftText(String text) {
        tvLeft.setText(text);
    }

    /**
     * 设置左点击事件
     *
     * @param listener
     */
    public void setLeftAction(@NonNull OnClickListener listener) {
        ivLeft.setOnClickListener(listener);
    }

    /**
     * 设置左图标及点击事件
     *
     * @param imgRes
     * @param listener
     */
    public void setLeftIconAndAction(@NonNull int imgRes, @NonNull OnClickListener listener) {
        setLeftIcon(imgRes);
        setLeftAction(listener);
    }

    public void setLeftTextAndAction(@NonNull int stringId, @NonNull OnClickListener listener) {
        tvLeft.setText(getResources().getString(stringId));
        tvLeft.setOnClickListener(listener);
    }

    public void setLeftTextAction(@NonNull OnClickListener listener) {
        tvLeft.setOnClickListener(listener);
    }

    /**
     * 设置右图标
     *
     * @param imgRes
     */
    public void setRightIcon(@NonNull int imgRes) {
        ivRight.setImageResource(imgRes);
    }

    public void setRightText(String text) {
        tvRight.setText(text);
    }

    /**
     * 设置右点击事件
     *
     * @param listener
     */
    public void setRightAction(@NonNull OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setRightTextAction(@NonNull OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 设置右图标及点击事件
     *
     * @param imgRes
     * @param listener
     */
    public void setRightIconAndAction(@NonNull int imgRes, @NonNull OnClickListener listener) {
        setRightIcon(imgRes);
        setRightAction(listener);
    }

    /**
     * 设置右边文字及点击事件
     *
     * @param stringId
     * @param listener
     */
    public void setRightTextAndAction(@NonNull int stringId, @NonNull OnClickListener listener) {
        tvRight.setText(getResources().getString(stringId));
        tvRight.setOnClickListener(listener);
    }

    /**
     * 隐藏左图标
     */
    public void hideLeft() {
        showLeft(FLAG_NONE);
    }

    /**
     * 显示左图标
     */
    public void showLeft() {
        showLeft(FLAG_ICON);
    }

    /**
     * 隐藏右图标
     */
    public void hideRight() {
        showRight(FLAG_NONE);
    }

    public void showRight(int flag) {
        ivRight.setVisibility((flag & FLAG_ICON) == FLAG_ICON ? View.VISIBLE : View.GONE);
        tvRight.setVisibility((flag & FLAG_TEXT) == FLAG_TEXT ? View.VISIBLE : View.GONE);
    }

    public void showLeft(int flag) {
        ivLeft.setVisibility((flag & FLAG_ICON) == FLAG_ICON ? View.VISIBLE : View.GONE);
        tvLeft.setVisibility((flag & FLAG_TEXT) == FLAG_TEXT ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示右图标
     */
    public void showRight() {
        showRight(FLAG_ICON);
    }

    /**
     * 自定义显示页面
     *
     * @param v
     */
    public void setCustomLayout(@NonNull View v) {
        customLayout.removeAllViews();
        customLayout.addView(v);
    }
}

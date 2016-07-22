package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class NavigationTabView extends RelativeLayout {

    @BindView(R.id.iv_left_icon)
    ImageView ivLeftIcon;
    @BindView(R.id.tv_left_text)
    TextView tvLeft;
    @BindView(R.id.tv_right_text)
    TextView tvRight;
    @BindView(R.id.tv_center_text)
    TextView tvCenter;
    @BindView(R.id.iv_right_icon)
    ImageView ivRightIcon;

    public NavigationTabView(Context context) {
        this(context, null);
    }

    public NavigationTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_navigation_tab, this, true);
        ButterKnife.bind(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationTabView);
        String leftText = typedArray.getString(R.styleable.NavigationTabView_tab_left_text);
        String centerText = typedArray.getString(R.styleable.NavigationTabView_tab_center_text);
        String rightText = typedArray.getString(R.styleable.NavigationTabView_tab_right_text);
        int leftIconRes = typedArray.getResourceId(R.styleable.NavigationTabView_tab_left_icon, 0);
        int rightIconRes = typedArray.getResourceId(R.styleable.NavigationTabView_tab_right_icon, 0);
        int leftColor = typedArray.getColor(R.styleable.NavigationTabView_tab_left_text_color, -1);
        float leftSize = typedArray.getDimension(R.styleable.NavigationTabView_tab_left_text_size, -1f);
        int centerColor = typedArray.getColor(R.styleable.NavigationTabView_tab_center_text_color, -1);
        float centerSize = typedArray.getDimension(R.styleable.NavigationTabView_tab_center_text_size, -1);
        int rightColor = typedArray.getColor(R.styleable.NavigationTabView_tab_right_text_color, -1);
        float rightSize = typedArray.getDimension(R.styleable.NavigationTabView_tab_right_text_size, -1);
        typedArray.recycle();

        if (leftColor != -1) {
            tvLeft.setTextColor(leftColor);
        }
        if (leftSize != -1) {
            tvLeft.setTextSize(leftSize);
        }
        tvLeft.setText(leftText);

        if (centerColor != -1) {
            tvCenter.setTextColor(centerColor);
        }
        if (centerSize != -1) {
            tvCenter.setTextSize(centerSize);
        }
        tvCenter.setText(centerText);

        if (rightColor != -1) {
            tvRight.setTextColor(rightColor);
        }
        if (rightSize != -1) {
            tvRight.setTextSize(rightSize);
        }
        tvRight.setText(rightText);
        setIvLeftIcon(leftIconRes);
        setIvRightIcon(rightIconRes);
    }

    public void setIvLeftIcon(@DrawableRes int leftIcon) {
        if (leftIcon == 0) {
            ivLeftIcon.setVisibility(View.GONE);
        } else {
            ivLeftIcon.setVisibility(View.VISIBLE);
            ivLeftIcon.setImageResource(leftIcon);
        }

    }

    public void setIvRightIcon(@DrawableRes int rightIcon) {
        if (rightIcon == 0) {
            ivRightIcon.setVisibility(View.GONE);
        } else {
            ivRightIcon.setVisibility(View.VISIBLE);
            ivRightIcon.setImageResource(rightIcon);
        }
    }

    public void setTvLeft(String text) {
        tvLeft.setText(text);
    }

    public void setTvCenter(String text) {
        tvCenter.setText(text);
    }

    public void setTvRight(String text) {
        tvRight.setText(text);
    }

}

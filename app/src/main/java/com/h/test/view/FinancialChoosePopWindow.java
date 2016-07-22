package cn.hdmoney.hdy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class FinancialChoosePopWindow extends PopupWindow implements PopupWindow.OnDismissListener, OnClickListener{

    public static final int FLAG_ALL = Constants.BID_TYPE_ALL;
    public static final int FLAG_GLY = Constants.BID_TYPE_GLY;
    public static final int FLAG_WLY = Constants.BID_TYPE_WLY;
    public static final int FLAG_HLY = 4;

    private View container;
    private TextView allView;
    private TextView glyView;
    private TextView wlyView;
    private TextView hlyView;
    private View view;
    private int selectedFlag = FLAG_ALL;

    private OnFinancialChooseItemClickListener listener;

    public void setListener(OnFinancialChooseItemClickListener listener) {
        this.listener = listener;
    }

    private Context context;
    public FinancialChoosePopWindow(Context context) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;
        setContentView(LayoutInflater.from(context).inflate(R.layout.popup_window_financial_chooise, null));

        container = getContentView().findViewById(R.id.ll_container);
        allView = (TextView) getContentView().findViewById(R.id.tv_all);
        glyView = (TextView) getContentView().findViewById(R.id.tv_gly);
        wlyView = (TextView) getContentView().findViewById(R.id.tv_wly);
        hlyView = (TextView) getContentView().findViewById(R.id.tv_hly);
        view = getContentView().findViewById(R.id.view);
        allView.setOnClickListener(this);
        glyView.setOnClickListener(this);
        wlyView.setOnClickListener(this);
        hlyView.setOnClickListener(this);
        view.setOnClickListener(this);

        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);
        setAnimationStyle(R.style.FinancialChoosePopWindowStyle);
        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);
        setOnDismissListener(this);
    }

    public void setSelectedFlag(int selectedFlag){
        this.selectedFlag = selectedFlag;
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        setBackgroundAlpha(0.5f);
        notifySelectedChanged();
    }

    private void notifySelectedChanged() {
        allView.setSelected(selectedFlag == FLAG_ALL);
        glyView.setSelected(selectedFlag == FLAG_GLY);
        wlyView.setSelected(selectedFlag == FLAG_WLY);
        hlyView.setSelected(selectedFlag == FLAG_HLY);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
        if (listener != null) {
            listener.onDismiss();
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        if (context instanceof Activity) {
            WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                    .getAttributes();
            lp.alpha = bgAlpha;
            ((Activity) context).getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onClick(View v) {
        int bidType = -1;
        if (v.getId() == R.id.tv_all) {
            bidType = FLAG_ALL;
            setSelectedFlag(FLAG_ALL);
            dismiss();
        } else if (v.getId() == R.id.tv_gly) {
            bidType = FLAG_GLY;
            setSelectedFlag(FLAG_GLY);
            dismiss();
        } else if (v.getId() == R.id.tv_hly) {
            bidType = FLAG_HLY;
            setSelectedFlag(FLAG_HLY);
            dismiss();
        } else if (v.getId() == R.id.tv_wly) {
            bidType = FLAG_WLY;
            setSelectedFlag(FLAG_WLY);
            dismiss();
        } else {
//            dismiss();
            view.setBackgroundColor(Color.parseColor("#00000000"));
        }
        if (listener != null && bidType != -1) {
            listener.onChooseItemClick(bidType);
        }
    }

    public static interface OnFinancialChooseItemClickListener {
        public void onChooseItemClick(int bidType);
        public void onDismiss();
    }
}

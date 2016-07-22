package cn.hdmoney.hdy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class DetailAddressPopWindow extends PopupWindow implements PopupWindow.OnDismissListener, View.OnClickListener{

    EditText detailAddress;
    private TextView cancel;
    private TextView confirm;

    private Context context;
    private OnDetailAddressWindowListener onDetailAddressWindowListener;
    public void setOnDetailAddressWindowListener(OnDetailAddressWindowListener onDetailAddressWindowListener) {
        this.onDetailAddressWindowListener = onDetailAddressWindowListener;
    }

    public DetailAddressPopWindow(final Context context) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;

        setContentView(LayoutInflater.from(context).inflate(R.layout.popup_window_detail_address, null));
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        detailAddress = (EditText) getContentView().findViewById(R.id.et_detail_address);
        cancel = (TextView) getContentView().findViewById(R.id.tv_cancle);
        confirm = (TextView) getContentView().findViewById(R.id.tv_confirm);

        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);

        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);
        setOnDismissListener(this);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);

        setBackgroundAlpha(0.5f);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        setBackgroundAlpha(0.5f);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
        if (onDetailAddressWindowListener != null) {
            onDetailAddressWindowListener.onCancel();
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
        if (v.getId() == R.id.tv_confirm) {
            if (onDetailAddressWindowListener != null) {
                onDetailAddressWindowListener.onOk(detailAddress.getText().toString());
            }
        } else if (v.getId() == R.id.tv_cancle) {
            dismiss();
        }
    }

    public static interface OnDetailAddressWindowListener{
        public void onOk(String address);
        public void onCancel();
    }
}

package cn.hdmoney.hdy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class ListPopWindow extends PopupWindow implements PopupWindow.OnDismissListener, OnClickListener{

    TextView cancelView;
    ListView listView;
    ArrayAdapter adapter;
    private OnListPopWindowListener onListPopWindowListener;
    private Context context;

    public void setOnListPopWindowListener(OnListPopWindowListener onListPopWindowListener) {
        this.onListPopWindowListener = onListPopWindowListener;
    }

    public ListPopWindow(final Context context) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;

        setContentView(LayoutInflater.from(context).inflate(R.layout.popup_window, null));
        listView = (ListView) getContentView().findViewById(R.id.list_view);
        cancelView = (TextView) getContentView().findViewById(R.id.tv_cancle);
        cancelView.setOnClickListener(this);

        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter = new ArrayAdapter(context, R.layout.item_popup_window);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onListPopWindowListener != null) {
                    onListPopWindowListener.onItemClick(position);
                }
            }
        });
        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);

        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);
        setOnDismissListener(this);
    }

    public void setDate(List<String> list) {
        adapter.setNotifyOnChange(false);
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
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
        if (onListPopWindowListener != null) {
            onListPopWindowListener.onDismiss();
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
        dismiss();
    }

    public static interface OnListPopWindowListener{
        public void onItemClick(int position);
        public void onDismiss();
    }
}

package cn.hdmoney.hdy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class CouponPopWindow extends PopupWindow implements PopupWindow.OnDismissListener, OnClickListener{
    public static final int FLAG_RED_PACKET = 1;
    public static final int FLAG_ADD_RATE = 2;
    RecyclerView recyclerView;
    private OnCouponPopWindowListener onCouponPopWindowListener;
    private Context context;
    private CouponRecycleAdapter adapter;
    private TextView titleView;
    private ImageView selectedView;
    private RelativeLayout noUseContainer;
    private int type = FLAG_RED_PACKET;

    public void setOnCouponPopWindowListener(OnCouponPopWindowListener onCouponPopWindowListener) {
        this.onCouponPopWindowListener = onCouponPopWindowListener;
    }

    public CouponPopWindow(final Context context) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.context = context;

        setContentView(LayoutInflater.from(context).inflate(R.layout.popup_window_coupon, null));
        recyclerView = (RecyclerView) getContentView().findViewById(R.id.recycle_view);
        titleView = (TextView) getContentView().findViewById(R.id.tv_title);
        selectedView = (ImageView) getContentView().findViewById(R.id.iv_selected);
        noUseContainer = (RelativeLayout) getContentView().findViewById(R.id.rl_no_use_container);
        noUseContainer.setOnClickListener(this);

        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CouponRecycleAdapter();
        adapter.setRecycleAdapterListener(new RecycleAdapterListener() {
            @Override
            public void onItemClick(int position) {
                if (onCouponPopWindowListener != null) {
                    onCouponPopWindowListener.onConfirm(adapter.getItem(position), type);
                }
            }
        });
        recyclerView.setAdapter(adapter);

        setOutsideTouchable(true);
        setFocusable(true);
        setTouchable(true);

        ColorDrawable background = new ColorDrawable(0x4f000000);
        this.setBackgroundDrawable(background);
        setOnDismissListener(this);
    }

    public void setData(int type, List<Coupon> list, int checkedPosition) {
        setData(type, list, list != null && list.size() > checkedPosition ? list.get(checkedPosition) : null);
    }

    public void setData(int type, List<Coupon> list, Coupon checkedCoupon) {
        this.type = type;
        if (type == FLAG_ADD_RATE) {
            titleView.setText("不使用加息卷");
        } else if (type == FLAG_RED_PACKET){
            titleView.setText("不使用红包");
        }
        if (checkedCoupon == null) {
            selectedView.setVisibility(View.VISIBLE);
        } else {
            selectedView.setVisibility(View.GONE);
        }
        adapter.setData(list, checkedCoupon);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);

        setBackgroundAlpha(0.2f);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        setBackgroundAlpha(0.2f);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1f);
        if (onCouponPopWindowListener != null) {
            onCouponPopWindowListener.onDismiss();
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
        if (onCouponPopWindowListener != null) {
            onCouponPopWindowListener.onConfirm(null, type);
        }
    }

    public static interface OnCouponPopWindowListener {
        public void onConfirm(Coupon coupon, int type);
        public void onDismiss();
    }

    static interface RecycleAdapterListener{
        public void onItemClick(int position);
    }

    private static class CouponRecycleAdapter extends RecyclerView.Adapter{
        private List<Coupon> list;
        private Coupon checkedCoupon;
        private RecycleAdapterListener recycleAdapterListener;

        public void setRecycleAdapterListener(RecycleAdapterListener recycleAdapterListener) {
            this.recycleAdapterListener = recycleAdapterListener;
        }

        public void setData(List<Coupon> list) {
            setData(list, null);
        }

        public Coupon getSelectedCoupon() {
            return checkedCoupon;
        }

        public void setData(List<Coupon> list, Coupon checkedCoupon) {
            this.list = list;
            this.checkedCoupon = checkedCoupon;
            notifyDataSetChanged();
        }

        public Coupon getItem(int position) {
            return list != null && list.size() > position ? list.get(position) : null;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CouponViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pop_window_coupon, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CouponViewHolder viewHolder = (CouponViewHolder) holder;
            Coupon coupon = getItem(position);
            if (coupon != null && checkedCoupon != null && coupon.cid == checkedCoupon.cid) {
                viewHolder.selectedView.setVisibility(View.VISIBLE);
            } else {
                viewHolder.selectedView.setVisibility(View.GONE);
            }
            viewHolder.nameView.setText(coupon.couponName);
            viewHolder.descView.setText(coupon.description);

            viewHolder.itemView.setTag(position);
            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recycleAdapterListener != null) {
                        recycleAdapterListener.onItemClick((Integer) v.getTag());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }
    }

    private static class CouponViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        TextView descView;
        ImageView selectedView;

        public CouponViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.tv_name);
            descView = (TextView) itemView.findViewById(R.id.tv_desc);
            selectedView = (ImageView) itemView.findViewById(R.id.iv_selected);
        }
    }
}

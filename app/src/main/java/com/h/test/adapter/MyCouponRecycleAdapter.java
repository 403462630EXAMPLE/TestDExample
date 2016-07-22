package cn.hdmoney.hdy.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.utils.SpannableUtils;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyCouponRecycleAdapter extends RecyclerView.Adapter {
    private List<Coupon> data = new ArrayList<>();
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setDatas(List<Coupon> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDatas(List<Coupon> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    public Coupon getItem(int position) {
        return data.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCouponViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_coupon, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyCouponViewHolder viewHolder = (MyCouponViewHolder) holder;
        Coupon coupon = getItem(position);
        if (coupon.status == 1) {
            viewHolder.llLeftContainer.setBackgroundResource(R.mipmap.bg_coupon_enable);
        } else {
            viewHolder.llLeftContainer.setBackgroundResource(R.mipmap.bg_coupon_disable);
        }
        if (type == Constants.MY_COUPON_TYPE_RED_PACKET) {
            SpannableString span = new SpannableString("¥" + coupon.amount);
            SpannableUtils.setTextSize(span, 0, 1, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, viewHolder.tvNumber.getResources().getDisplayMetrics()));
            SpannableUtils.setTextSize(span, 1, span.length(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, viewHolder.tvNumber.getResources().getDisplayMetrics()));
            viewHolder.tvNumber.setText(span);
        } else {
            SpannableString span = new SpannableString(coupon.amount + "%");
            SpannableUtils.setTextSize(span, span.length() - 1, span.length(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, viewHolder.tvNumber.getResources().getDisplayMetrics()));
            SpannableUtils.setTextSize(span, 0, span.length() - 1, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 30, viewHolder.tvNumber.getResources().getDisplayMetrics()));
            viewHolder.tvNumber.setText(span);
        }

        viewHolder.tvDesc.setText(coupon.description);
        viewHolder.tvEnableProduct.setText(coupon.enableProduct);
        viewHolder.tvExpireTime.setText("到期时间：" + coupon.expireTime);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyCouponViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_left_container)
        LinearLayout llLeftContainer;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_left_text)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_enable_product)
        TextView tvEnableProduct;
        @BindView(R.id.tv_expire_time)
        TextView tvExpireTime;
        public MyCouponViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

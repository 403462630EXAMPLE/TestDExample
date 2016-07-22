package cn.hdmoney.hdy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.Entity.PaymentRecord;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class PaymentRecordRecycleAdapter extends RecyclerView.Adapter {

    private List<PaymentRecord> data = new ArrayList<>();
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    public void setDatas(List<PaymentRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDatas(List<PaymentRecord> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.PAYMENT_RECORD_TYPE_ALL) {
            return new PaymentRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_record_all, parent, false));
        } else {
            return new PaymentRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_record, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PaymentRecordViewHolder viewHolder = (PaymentRecordViewHolder) holder;
        PaymentRecord paymentRecord = data.get(position);
        if (type == Constants.PAYMENT_RECORD_TYPE_ALL) {
            viewHolder.tvL1.setText(paymentRecord.actionName);
            viewHolder.tvL2.setText("余额：" + paymentRecord.amount);
            viewHolder.tvR1.setText(paymentRecord.date);
            viewHolder.tvR2.setText(String.valueOf(paymentRecord.actionMoney));
        } else {
            viewHolder.tvL1.setText(paymentRecord.date);
            viewHolder.tvL2.setText("金额：" + paymentRecord.actionMoney);
            viewHolder.tvR1.setText("成功");
            viewHolder.tvR2.setText("手续费：0.00");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PaymentRecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_l1)
        TextView tvL1;
        @BindView(R.id.tv_l2)
        TextView tvL2;
        @BindView(R.id.tv_r1)
        TextView tvR1;
        @BindView(R.id.tv_r2)
        TextView tvR2;
        public PaymentRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

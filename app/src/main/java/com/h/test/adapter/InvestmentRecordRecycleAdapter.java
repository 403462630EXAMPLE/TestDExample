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
import cn.hdmoney.hdy.Entity.InvestmentRecord;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentRecordRecycleAdapter extends RecyclerView.Adapter {

    private List<InvestmentRecord> data = new ArrayList<>();

    public void setDatas(List<InvestmentRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDatas(List<InvestmentRecord> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InvestmentRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_investment_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        InvestmentRecordViewHolder viewHolder = (InvestmentRecordViewHolder) holder;
        InvestmentRecord record = data.get(position);
        viewHolder.tvName.setText(record.bidName);
        viewHolder.tvStartDate.setText("起息日:  " + record.interestDate);
        viewHolder.tvEndDate.setText("到期日:  " + record.expireDate);
        viewHolder.tvInvestmentMoney.setText("¥" + record.investMoney);
        viewHolder.tvProfitMoney.setText("¥" + record.interestAmount);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class InvestmentRecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_left_text)
        TextView tvName;
        @BindView(R.id.tv_start_date)
        TextView tvStartDate;
        @BindView(R.id.tv_end_date)
        TextView tvEndDate;
        @BindView(R.id.tv_investment_money)
        TextView tvInvestmentMoney;
        @BindView(R.id.tv_profit_money)
        TextView tvProfitMoney;
        public InvestmentRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

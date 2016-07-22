package cn.hdmoney.hdy.fragment.financialDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.Entity.InvestRecord;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class InvestmentRecordFragment extends BaseFragment {
    private static final String ARG_INVESTMENT_RECORDS = "arg_investment_records";
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    private List<InvestRecord> records;
    InvestmentRecordRecycleAdapter adapter;

    public static InvestmentRecordFragment build(ArrayList<InvestRecord> list) {
        InvestmentRecordFragment fragment = new InvestmentRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_INVESTMENT_RECORDS, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_financial_detail_investment_record;
    }

    @Override
    protected void initViews() {
        super.initViews();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new InvestmentRecordRecycleAdapter();
        if (getArguments() != null) {
            records = getArguments().getParcelableArrayList(ARG_INVESTMENT_RECORDS);
        }
        adapter.setDatas(records);
        recyclerView.setAdapter(adapter);
    }

    private static class InvestmentRecordRecycleAdapter extends RecyclerView.Adapter {
        private List<InvestRecord> datas;

        public void setDatas(List<InvestRecord> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InvestmentRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_detail_investment_record, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            InvestmentRecordViewHolder viewHolder = (InvestmentRecordViewHolder) holder;
            InvestRecord record = getItem(position);
            if (record != null) {
                viewHolder.tvUsername.setText(record.purchaser);
                viewHolder.tvCommitTime.setText(record.date);
                viewHolder.tvMoney.setText(String.valueOf(record.money));
            }
        }

        public InvestRecord getItem(int position) {
            return datas != null && datas.size() > position ? datas.get(position) : null;
        }

        @Override
        public int getItemCount() {
            return datas != null ? datas.size() : 0;
        }
    }

    static class InvestmentRecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_commit_time)
        TextView tvCommitTime;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        public InvestmentRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

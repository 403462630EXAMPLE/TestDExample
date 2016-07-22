package cn.hdmoney.hdy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.view.HdyProgressView;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class FinancialRecycleAdapter extends RecyclerView.Adapter {

    private List<Bid> datas;
    private OnFinancialItemClickListener listener;

    public void setListener(OnFinancialItemClickListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<Bid> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<Bid> datas) {
        this.datas.addAll(datas);
        notifyItemRangeInserted(this.datas.size() - datas.size(), datas.size());
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FinancialRecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FinancialRecycleViewHolder viewHolder = (FinancialRecycleViewHolder) holder;
        Bid bid = getItem(position);
        viewHolder.tvLeftText.setText(bid.name);
        viewHolder.tvRate.setText(String.valueOf(bid.apr));
        viewHolder.tvDay.setText(bid.period);
        viewHolder.tvNumber.setText(String.valueOf((int)bid.residueShare));
        if (Math.random() > 0.5) {
            viewHolder.iconNew.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iconNew.setVisibility(View.GONE);
        }
        if (Math.random() > 0.5) {
            viewHolder.iconAddrate.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iconAddrate.setVisibility(View.GONE);
        }
        if (bid.addInterest > 0) {
            viewHolder.tvPlus.setVisibility(View.VISIBLE);
            viewHolder.tvAddrate.setVisibility(View.VISIBLE);
            viewHolder.tvAddrate.setText(String.valueOf(bid.addInterest));
        } else {
            viewHolder.tvPlus.setVisibility(View.GONE);
            viewHolder.tvAddrate.setVisibility(View.GONE);
        }
        viewHolder.progressBar.setProgress((int) bid.schedule);

        viewHolder.itemView.setTag(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick((int) v.getTag());
                }
            }
        });
    }

    public Bid getItem(int position) {
        return datas != null && datas.size() > position ? datas.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    static class FinancialRecycleViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.tv_left_text)
//        TextView nameView;
//        @BindView(R.id.tv_hint)
//        TextView hintView;
//        @BindView(R.id.tv_rate)
//        TextView rateView;
//        @BindView(R.id.tv_day)
//        TextView dayView;
//        @BindView(R.id.tv_number)
//        TextView numberView;
//        @BindView(R.id.tv_cusp_hint)
//        TextView cuspHintView;
//        @BindView(R.id.circle_bg_view)
//        CircleBgView circleBgView;

        @BindView(R.id.tv_left_text)
        TextView tvLeftText;
        @BindView(R.id.icon_new)
        ImageView iconNew;
        @BindView(R.id.icon_addrate)
        ImageView iconAddrate;
        @BindView(R.id.tv_rate)
        TextView tvRate;
        @BindView(R.id.tv_plus)
        TextView tvPlus;
        @BindView(R.id.tv_addrate)
        TextView tvAddrate;
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.progressBar)
        HdyProgressView progressBar;

        public FinancialRecycleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static interface OnFinancialItemClickListener {
        public void onItemClick(int position);
    }
}

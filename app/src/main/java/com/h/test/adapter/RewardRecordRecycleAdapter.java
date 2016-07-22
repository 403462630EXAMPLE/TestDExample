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
import cn.hdmoney.hdy.Entity.AwardRecord;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class RewardRecordRecycleAdapter extends RecyclerView.Adapter {

    private List<AwardRecord> data = new ArrayList<>();

    public void setDatas(List<AwardRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    public void addDatas(List<AwardRecord> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RewardRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reward_record, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RewardRecordViewHolder viewHolder = (RewardRecordViewHolder) holder;
        if (position % 2 == 0) {
            viewHolder.container.setBackgroundColor(viewHolder.container.getResources().getColor(R.color.me_white));
        } else {
            viewHolder.container.setBackgroundColor(viewHolder.container.getResources().getColor(R.color.me_item_bg_gray));
        }
        AwardRecord awardRecord = data.get(position);
        viewHolder.tvLeftHint.setText(awardRecord.name);
        viewHolder.tvCenterHint.setText(awardRecord.way);
        viewHolder.tvRightHint.setText(awardRecord.date);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RewardRecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_container)
        View container;
        @BindView(R.id.tv_left_hint)
        TextView tvLeftHint;
        @BindView(R.id.tv_center_hint)
        TextView tvCenterHint;
        @BindView(R.id.tv_right_hint)
        TextView tvRightHint;
        public RewardRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

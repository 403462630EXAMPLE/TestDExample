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
import cn.hdmoney.hdy.Entity.Award;
import cn.hdmoney.hdy.Entity.Recommend;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.constant.Constants;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyRecommendRecycleAdapter extends RecyclerView.Adapter {

    private List data = new ArrayList<>();

    public void setDatas(List data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addDatas(List data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    public int getNextPage() {
        return (getItemCount() % Constants.PAGE_SIZE == 0 ? getItemCount() / Constants.PAGE_SIZE : (getItemCount() / Constants.PAGE_SIZE + 1)) + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecommendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_recommend, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyRecommendViewHolder viewHolder = (MyRecommendViewHolder) holder;
        Object t = getItem(position);
        if (position % 2 == 0) {
            viewHolder.view.setBackgroundColor(viewHolder.view.getResources().getColor(R.color.me_white));
        } else {
            viewHolder.view.setBackgroundColor(viewHolder.view.getResources().getColor(R.color.me_item_bg_gray));
        }
        if (t instanceof Award) {
            Award award = (Award) t;
            viewHolder.tvLeftHint.setText(award.date);
            viewHolder.tvCenterHint.setText(award.typeName);
            viewHolder.tvRightHint.setText(award.award);
        } else if (t instanceof Recommend) {
            Recommend recommend = (Recommend) t;
            viewHolder.tvLeftHint.setText(recommend.date);
            viewHolder.tvCenterHint.setText(recommend.name);
            viewHolder.tvRightHint.setText(recommend.mobile);
        }
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyRecommendViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)
        View view;
        @BindView(R.id.tv_left_hint)
        TextView tvLeftHint;
        @BindView(R.id.tv_center_hint)
        TextView tvCenterHint;
        @BindView(R.id.tv_right_hint)
        TextView tvRightHint;
        public MyRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

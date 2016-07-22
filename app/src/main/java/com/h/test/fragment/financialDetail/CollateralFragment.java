package cn.hdmoney.hdy.fragment.financialDetail;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class CollateralFragment extends BaseFragment {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @Override
    protected int getContentView() {
        return R.layout.fragment_financial_detail_collateral;
    }

    @Override
    protected void initViews() {
        super.initViews();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new SpacesItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11, getResources().getDisplayMetrics())));
        recyclerView.setAdapter(new CollateralRecycleAdapter());
    }

    private static class CollateralRecycleAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CollateralRecordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_detail_collateral, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    static class CollateralRecordViewHolder extends RecyclerView.ViewHolder {

        public CollateralRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.right = space;
            outRect.bottom = space;
            int position = parent.getChildPosition(view);
            if(position == 0 || position == 1) {
                outRect.top = space;
            }
            if (position % 2 == 0) {
                outRect.left = space;
            }
        }
    }
}

package cn.hdmoney.hdy.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.hdy.loadmorerecycleview.adapter.LoadMoreCombinationFcAdapter;
import com.hdy.swuperereshlayout.SwipeRefreshLayout;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.FinancialRecycleAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.mvp.presenter.FinancialPresenter;
import cn.hdmoney.hdy.mvp.ui.FinancialUi;
import cn.hdmoney.hdy.mvp.ui.FinancialUiCallBack;
import cn.hdmoney.hdy.view.FinancialChoosePopWindow;
import cn.hdmoney.hdy.view.TitleBar;
import ru.vang.progressswitcher.ProgressWidget;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinancialFragment extends BaseFragment implements FinancialUi, FinancialRecycleAdapter.OnFinancialItemClickListener, OnLoadMoreListener, FinancialChoosePopWindow.OnFinancialChooseItemClickListener {

    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    FinancialRecycleAdapter adapter;
    private FinancialUiCallBack callBack;
    private FinancialChoosePopWindow financialChoosePopWindow;
    LoadMoreCombinationFcAdapter loadMoreAdapter;
    private int currentBidType;

    @Override
    protected int getContentView() {
        return R.layout.fragment_financial;
    }

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected void initViews() {
        titleBar.setRightAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleBar.setRightIcon(R.mipmap.ic_filter);
                if (financialChoosePopWindow == null) {
                    financialChoosePopWindow = new FinancialChoosePopWindow(getActivity());
                    financialChoosePopWindow.setListener(FinancialFragment.this);
                }
                financialChoosePopWindow.showAsDropDown(titleBar);
            }
        });
        adapter = new FinancialRecycleAdapter();
        adapter.setListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        loadMoreAdapter = (LoadMoreCombinationFcAdapter)recyclerView.getAdapter();
        loadMoreAdapter.setOnLoadMoreListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callBack.onRefresh(currentBidType);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (financialChoosePopWindow != null) {
            financialChoosePopWindow.dismiss();
        }
        callBack.onDestroy();
    }

    @Override
    public Presenter setPresenter() {
        return new FinancialPresenter(getActivity(), this);
    }

    @Override
    public void showProgress() {
        progressWidget.showProgress(false);
    }

    @Override
    public void showFinancials(List<Bid> bids) {
        swipeRefreshLayout.setRefreshing(false);
        loadMoreAdapter.notifyNormal();
        if (bids.size() > 0) {
            adapter.setDatas(bids);
            progressWidget.showContent();
        } else {
            progressWidget.showEmpty();
        }
    }

    @Override
    public void showMoreFinancials(List<Bid> bids) {
        if (bids != null && bids.size() > 0) {
            adapter.addDatas(bids);
            loadMoreAdapter.notifyNormal();
        } else {
            loadMoreAdapter.notifyLoadedAll();
        }
    }

    @Override
    public void showError(boolean isLoadMore) {
        loadMoreAdapter.notifyError();
        if (!isLoadMore) {
            progressWidget.showContent();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setUiCallback(FinancialUiCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onItemClick(int position) {
        callBack.onItemFinancialClick(adapter.getItem(position));
    }

    @Override
    public void onLoadMore() {
        callBack.onLoadMore(currentBidType, adapter.getNextPage());
    }

    @Override
    public void onChooseItemClick(int bidType) {
        if (currentBidType != bidType) {
            currentBidType = bidType;
            progressWidget.showProgress();
            callBack.onRefresh(currentBidType);
        }
    }

    @Override
    public void onDismiss() {
        titleBar.setRightIcon(R.mipmap.ic_filter_normal);
    }
}

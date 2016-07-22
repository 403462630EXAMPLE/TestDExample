package cn.hdmoney.hdy.fragment.investmentRecord;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.hdy.loadmorerecycleview.LoadMoreRecycleView;
import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.hdy.swuperereshlayout.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.InvestmentRecord;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.InvestmentRecordRecycleAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import ru.vang.progressswitcher.ProgressWidget;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentRecordListFragment extends BaseFragment implements OnLoadMoreListener {
    private static final String ARG_TYPE = "arg_type";
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycle_view)
    LoadMoreRecycleView recycleView;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;

    InvestmentRecordRecycleAdapter adapter;
    private boolean isFirstVisible = true;
    private int type = Constants.INVESTMENT_RECORD_TYPE_ALL;

    public static InvestmentRecordListFragment build(int type) {
        InvestmentRecordListFragment fragment = new InvestmentRecordListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_investment_list_record;
    }

    @Override
    protected void initViews() {
        super.initViews();
        swipeRefreshLayout.setEnabled(false);
        adapter = new InvestmentRecordRecycleAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setOnLoadMoreListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        if (getArguments() != null && getArguments().containsKey(ARG_TYPE)) {
            type = getArguments().getInt(ARG_TYPE);
        }

        getView().post(new Runnable() {
            @Override
            public void run() {
                progressWidget.showProgress(false);
                if (getUserVisibleHint() && isFirstVisible) {
                    loadData(type, 1);
                    isFirstVisible = false;
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && progressWidget != null && isFirstVisible) {
            loadData(type, 1);
            isFirstVisible = false;
        }
    }

    public void showInvestmentRecords(List<InvestmentRecord> records) {
        progressWidget.showContent();
        adapter.setDatas(records);
        recycleView.notifyNormal();
    }

    public void showMoreInvestmentRecords(List<InvestmentRecord> records) {
        adapter.addDatas(records);
        recycleView.notifyNormal();
    }

    @Override
    public void onLoadMore() {
        loadData(type, adapter.getNextPage());
    }

    private void loadData(int type, final int page) {
        ApiFactory.getHdyApi().getInvestmentRecords(1000056, type, page, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.ListResult<InvestmentRecord>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        if (page > 1) {
                            showMoreInvestmentRecords(getTempDatas());
                        } else {
                            showInvestmentRecords(getTempDatas());
                        }
                    }

                    @Override
                    public void onNext(Result<Result.ListResult<InvestmentRecord>> listResultResult) {
                        if (page > 1) {
                            showMoreInvestmentRecords(getTempDatas());
                        } else {
                            showInvestmentRecords(getTempDatas());
                        }
                    }
                });
    }

    private List<InvestmentRecord> getTempDatas() {
        List<InvestmentRecord> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            InvestmentRecord record = new InvestmentRecord();
            record.id = 100001;
            record.bidName = "高利银22期";
            record.investMoney = 1000;
            record.interestAmount = "6.5";
            record.interestDate = "2016-07-09";
            record.expireDate = "2016-08-09";
            record.period = "30天";
            list.add(record);
        }
        return  list;
    }
}

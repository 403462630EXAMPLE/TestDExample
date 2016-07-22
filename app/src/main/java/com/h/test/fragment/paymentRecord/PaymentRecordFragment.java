package cn.hdmoney.hdy.fragment.paymentRecord;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.hdy.loadmorerecycleview.LoadMoreRecycleView;
import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.hdy.swuperereshlayout.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.PaymentRecord;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.PaymentRecordRecycleAdapter;
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
public class PaymentRecordFragment extends BaseFragment implements OnLoadMoreListener{
    private static final String ARG_TYPE = "arg_type";
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycle_view)
    LoadMoreRecycleView recycleView;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    PaymentRecordRecycleAdapter adapter;

    int type = Constants.PAYMENT_RECORD_TYPE_ALL;
    boolean isFirstVisible = true;

    public static PaymentRecordFragment build(int type) {
        PaymentRecordFragment fragment = new PaymentRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_payment_record;
    }

    @Override
    protected void initViews() {
        super.initViews();
        if (getArguments() != null && getArguments().containsKey(ARG_TYPE)) {
            type = getArguments().getInt(ARG_TYPE);
        }
        swipeRefreshLayout.setEnabled(false);
        initProgressWidget();
        adapter = new PaymentRecordRecycleAdapter();
        adapter.setType(type);
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

    private void initProgressWidget() {
        progressWidget.setEmptyText("暂无数据", R.id.empty_view);
        ((ImageView)progressWidget.findViewById(R.id.empty_image)).setImageResource(R.mipmap.ic_empty_no_data);
    }


    public void showPaymentRecords(List<PaymentRecord> list) {
        if (Math.random() > 0.5) {
            progressWidget.showEmpty();
        } else {
            progressWidget.showContent();
        }
        adapter.setDatas(list);
        recycleView.notifyNormal();
    }


    public void showMorePaymentRecords(List<PaymentRecord> list) {
        adapter.addDatas(list);
        recycleView.notifyNormal();
    }


    public void showError() {
        progressWidget.showContent();
        recycleView.notifyError();
    }

    @Override
    public void onLoadMore() {
        loadData(type, adapter.getNextPage());
    }

    public void loadData(final int type, final int page) {
        ApiFactory.getHdyApi().getPaymentRecordList(1000056, type, page, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.ListResult<PaymentRecord>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        if (page > 1) {
                            showMorePaymentRecords(getTempDatas(type));
                        } else {
                            showPaymentRecords(getTempDatas(type));
                        }
                    }

                    @Override
                    public void onNext(Result<Result.ListResult<PaymentRecord>> listResultResult) {
                        if (page > 1) {
                            showMorePaymentRecords(getTempDatas(type));
                        } else {
                            showPaymentRecords(getTempDatas(type));
                        }
                    }
                });
    }

    private List<PaymentRecord> getTempDatas(int type) {
        List<PaymentRecord> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.id = 100002;
            if (type == 1) {
                paymentRecord.actionName = "充值";
            } else if (type == 2) {
                paymentRecord.actionName = "提现";
            } else {
                paymentRecord.actionName = Math.random() > 0.8 ? "购买(高利银会员专享10期)" : Math.random() > 0.5 ? "充值" : "提现";
            }
            paymentRecord.actionMoney = 1000;
            paymentRecord.amount = 9000;
            paymentRecord.date = "2016-07-09";
            list.add(paymentRecord);
        }
        return list;
    }
}

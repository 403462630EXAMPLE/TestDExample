package cn.hdmoney.hdy.fragment.myCoupon;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import com.hdy.loadmorerecycleview.LoadMoreRecycleView;
import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.hdy.swuperereshlayout.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.MyCouponRecycleAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import ru.vang.progressswitcher.ProgressWidget;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyCouponListFragment extends BaseFragment implements OnLoadMoreListener {
    private static final String ARG_TYPE = "arg_type";
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycle_view)
    LoadMoreRecycleView recycleView;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;

    MyCouponRecycleAdapter adapter;
    private int type = Constants.MY_COUPON_TYPE_RED_PACKET;
    private boolean isFirstVisible = true;
    private Subscription subscription;

    public static MyCouponListFragment build(int type) {
        MyCouponListFragment fragment = new MyCouponListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_coupon_list;
    }

    @Override
    protected void initViews() {
        super.initViews();
        if (getArguments() != null && getArguments().containsKey(ARG_TYPE)) {
            type =  getArguments().getInt(ARG_TYPE);
        }
        swipeRefreshLayout.setEnabled(false);
        adapter = new MyCouponRecycleAdapter();
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
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && progressWidget != null && isFirstVisible) {
            loadData(type, 1);
            isFirstVisible = false;
        }
    }

    public void showMyCoupon(List<Coupon> coupons) {
        progressWidget.showContent();
        adapter.setDatas(coupons);
        recycleView.notifyNormal();
    }

    public void showMoreMyCoupon(List<Coupon> coupons) {
        adapter.addDatas(coupons);
        recycleView.notifyNormal();
    }

    @Override
    public void onLoadMore() {
        loadData(type, adapter.getNextPage());
    }

    private void loadData(final int type, final int page) {
        subscription = ApiFactory.getHdyApi().getCouponList(1000056, page, 0, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.ListResult<Coupon>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        if (page > 1) {
                            showMoreMyCoupon(getTempDatas(type));
                        } else {
                            showMyCoupon(getTempDatas(type));
                        }
                    }

                    @Override
                    public void onNext(Result<Result.ListResult<Coupon>> listResultResult) {
                        if (page > 1) {
                            showMoreMyCoupon(getTempDatas(type));
                        } else {
                            showMyCoupon(getTempDatas(type));
                        }
                    }
                });
    }

    private List<Coupon> getTempDatas(int type) {
        List<Coupon> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Coupon coupon = new Coupon();
            coupon.id = 100001;
            coupon.cid = 2;
            if (type == Constants.MY_COUPON_TYPE_RED_PACKET) {
                coupon.amount = 20;
            } else {
                coupon.amount = 1;
            }
            coupon.couponName = "20现金优惠劵";
            coupon.description = "高利银满1000元";
            coupon.expireTime = "2016-08-09";
            coupon.status = Math.random() > 0.5 ? 1 : 2;
            coupon.enableProduct = "可投产品: 高利银, 稳利银";
            list.add(coupon);
        }
        return list;
    }
}

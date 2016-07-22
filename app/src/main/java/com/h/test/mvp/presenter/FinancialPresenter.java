package cn.hdmoney.hdy.mvp.presenter;

import android.content.Context;
import android.content.Intent;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.act.FinancialDetailActivity;
import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.mvp.model.FinancialModel;
import cn.hdmoney.hdy.mvp.ui.FinancialUi;
import cn.hdmoney.hdy.mvp.ui.FinancialUiCallBack;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class FinancialPresenter extends Presenter<FinancialUi, FinancialUiCallBack> implements FinancialUiCallBack{

    @Inject
    FinancialModel financialModel;
    private Context context;
    private Subscription subscription;
    private Subscription loadMoreSubscription;

    public FinancialPresenter(Context context, FinancialUi ui) {
        super(ui);
        this.context = context;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(FinancialUi ui) {
        ui.showProgress();
        onRefresh(Constants.BID_TYPE_ALL);
    }

    @Override
    protected FinancialUiCallBack createUiCallback(FinancialUi ui) {
        return this;
    }

    @Override
    public void onItemFinancialClick(Bid bid) {
        Intent intent = new Intent(context, FinancialDetailActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh(final int bidType) {
        unsubscribe();
        subscription = ApiFactory.getHdyApi().getBidList(bidType, 1).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.BidResult>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        getUi().showError(false);
                        getUi().showFinancials(getTempDatas(bidType));
                    }

                    @Override
                    public void onNext(Result<Result.BidResult> bidResultResult) {
//                        if (bidResultResult.isSuccess()) {
//                            getUi().showFinancials(bidResultResult.result.bidList);
//                        } else {
//                            getUi().showError(false);
//                        }
                        getUi().showFinancials(getTempDatas(bidType));
                    }
                });
    }

    private List<Bid> getTempDatas(int type) {
        List<Bid> bids = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Bid bid = new Bid();
            bid.id = 100001;
            String typeStr;
            if (type == Constants.BID_TYPE_ALL) {
                typeStr = (Math.random() > 0.5 ? "稳利银" : "高利银");
            } else {
                typeStr = (Constants.BID_TYPE_WLY == type ? "稳利银" : "高利银");
            }
            bid.name = typeStr + "21期";
            bid.type = type;
            bid.typeName = typeStr;
            bid.apr = 9;
            bid.addInterest = Math.random() > 0.5 ? 0.5 : 0;
            bid.period = String.valueOf((int)(Math.random() * 365));
            bid.schedule = Math.random() * 100;
            bid.residueShare = (100 - bid.schedule) * 10;
            bids.add(bid);
        }
        return bids;
    }

    @Override
    public void onLoadMore(final int bidType, int page) {
        if (loadMoreSubscription != null && !loadMoreSubscription.isUnsubscribed()) {
            loadMoreSubscription.unsubscribe();
        }
        loadMoreSubscription = ApiFactory.getHdyApi().getBidList(bidType, page).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.BidResult>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        getUi().showError(true);
                        getUi().showMoreFinancials(getTempDatas(bidType));
                    }

                    @Override
                    public void onNext(Result<Result.BidResult> bidResultResult) {
//                        if (bidResultResult.isSuccess()) {
//                            getUi().showMoreFinancials(bidResultResult.result.bidList);
//                        } else {
//                            getUi().showError(true);
//                        }
                        getUi().showMoreFinancials(getTempDatas(bidType));
                    }
                });
    }

    @Override
    public void onDestroy() {
        unsubscribe();
    }

    private void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        if (loadMoreSubscription != null && !loadMoreSubscription.isUnsubscribed()) {
            loadMoreSubscription.unsubscribe();
        }
    }
}

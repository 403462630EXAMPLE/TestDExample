package cn.hdmoney.hdy.mvp.presenter;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Account;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.OrderSuccessResult;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.FinancialModel;
import cn.hdmoney.hdy.mvp.ui.FinancialBuyUi;
import cn.hdmoney.hdy.mvp.ui.FinancialBuyUiCallBack;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class FinancialBuyPresenter extends Presenter<FinancialBuyUi, FinancialBuyUiCallBack> implements FinancialBuyUiCallBack {

    @Inject
    FinancialModel financialModel;
    private int bidId;

    public FinancialBuyPresenter(FinancialBuyUi ui, int bidId) {
        super(ui);
        this.bidId = bidId;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(FinancialBuyUi ui) {
        loadData(bidId);
    }

    private void loadData(int bidId) {
        Observable.zip(ApiFactory.getHdyApi().goBuy(1456, bidId, "f33239446565d76c0e204f09b778caf2"),
                ApiFactory.getHdyApi().getAccountInfo(1456, "f33239446565d76c0e204f09b778caf2"),
                new Func2<Result<Result.GoBuyResult>, Result<Account>, List>() {
                    @Override
                    public List call(Result<Result.GoBuyResult> goBuyResultResult, Result<Account> accountResult) {
                        List list = new ArrayList();
                        list.add(goBuyResultResult);
                        list.add(accountResult);
                        return list;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<List>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                      getUi().showError("网络异常");
                        getUi().showFinancial(getTempBid(), null, getTempAccount());
                    }

                    @Override
                    public void onNext(List list) {
//                        Result<Result.GoBuyResult> goBuyResultResult = (Result<Result.GoBuyResult>) list.get(0);
//                        Result<Account> accountResult = (Result<Account>) list.get(1);
//                        if (!goBuyResultResult.isSuccess()) {
//                            getUi().showError(goBuyResultResult.resultDesc);
//                            return ;
//                        }
//                        if (!accountResult.isSuccess()) {
//                            getUi().showError(goBuyResultResult.resultDesc);
//                            return ;
//                        }
//                        getUi().showFinancial(goBuyResultResult.result.bidInfo, goBuyResultResult.result.couponList, accountResult.result);

                        getUi().showFinancial(getTempBid(), null, getTempAccount());
                    }
                });
    }

    @Override
    protected FinancialBuyUiCallBack createUiCallback(FinancialBuyUi ui) {
        return this;
    }

    @Override
    public void confirmBuy(int bidId, double amount, String couponIds, String tradePassword) {
        ApiFactory.getHdyApi().createOrder(1000056, bidId, amount, couponIds, 1, tradePassword, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<OrderSuccessResult>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        getUi().showError("网络异常");
                        getUi().gotoFinancialBuySuccessUI(null);
                    }

                    @Override
                    public void onNext(Result<OrderSuccessResult> orderSuccessResultResult) {
//                        if (orderSuccessResultResult.isSuccess()) {
//                            getUi().gotoFinancialBuySuccessUI(orderSuccessResultResult.result);
//                        } else {
//                            getUi().showError(orderSuccessResultResult.resultDesc);
//                        }
                        getUi().gotoFinancialBuySuccessUI(null);
                    }
                });
    }

    private Bid getTempBid() {
        Bid bid = new Bid();
        bid.id = 100001;
        bid.name = "高利银会员专享10期";
        bid.type = 2;
        bid.apr = 9;
        bid.addInterest = 0.5;
        bid.residueShare = 1200;
        bid.baseAmount = 100;
        bid.avgAmount = 1;
        return bid;
    }

    private Account getTempAccount() {
        Account account = new Account();
        account.uid = 1456;
        account.balance = 1000.20;
        account.freezeAmount = 0;
        account.investing = 2000;
        account.interestAmount = 102.56;
        return account;
    }
}

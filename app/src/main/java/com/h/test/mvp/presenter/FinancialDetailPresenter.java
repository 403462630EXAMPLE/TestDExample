package cn.hdmoney.hdy.mvp.presenter;

import android.content.Context;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.InvestRecord;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.mvp.model.FinancialModel;
import cn.hdmoney.hdy.mvp.ui.FinancialDetailUi;
import cn.hdmoney.hdy.mvp.ui.FinancialDetailUiCallBack;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class FinancialDetailPresenter extends Presenter<FinancialDetailUi, FinancialDetailUiCallBack> implements FinancialDetailUiCallBack{

    @Inject
    FinancialModel financialModel;
    private Context context;

    public FinancialDetailPresenter(Context context, FinancialDetailUi ui) {
        super(ui);
        this.context = context;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(FinancialDetailUi ui) {
    }

    @Override
    protected FinancialDetailUiCallBack createUiCallback(FinancialDetailUi ui) {
        return this;
    }

    @Override
    public void immediateBuy(Bid bid) {
        getUi().gotoFinancialBuyUI();
    }

    @Override
    public void getBidDetailInfo(int bidId) {
        ApiFactory.getHdyApi().getBidInfo(bidId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.BidInfoResult>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        getUi().showError();
                        getUi().showFinancialDetail(getTempBidData(), getTempInvestRecordDatas());
                    }

                    @Override
                    public void onNext(Result<Result.BidInfoResult> bidInfoResultResult) {
//                        if (bidInfoResultResult.isSuccess()) {
//                            getUi().showFinancialDetail(bidInfoResultResult.result.bidInfo, bidInfoResultResult.result.investRecords);
//                        } else {
//                            getUi().showError();
//                        }
                        getUi().showFinancialDetail(getTempBidData(), getTempInvestRecordDatas());
                    }
                });
    }

    public Bid getTempBidData() {
        Bid bid = new Bid();
        bid.id = 100001;
        int bidType = Math.random() > 0.5 ? Constants.BID_TYPE_GLY : Constants.BID_TYPE_WLY;
        String typeStr = (bidType == Constants.BID_TYPE_WLY ? "稳利银" : "高利银");
        bid.name = typeStr + "21期";
        bid.type = bidType ;
        bid.typeName = typeStr;
        bid.apr = 9;
        bid.addInterest = Math.random() > 0.5 ? 0.5 : 0;
        bid.period = String.valueOf((int)(Math.random() * 365));
        bid.schedule = Math.random() * 100;
        bid.residueShare = (100 - bid.schedule) * 10;
        bid.acceptBank = "深圳农业银行科苑支行";
        bid.baseAmount = 100;
        bid.interestDate = "2016-06-06";
        bid.repayDate = "2016-07-08";
        bid.descriptionUrl = "http://www.baidu.com";
        return bid;
    }

    public List<InvestRecord> getTempInvestRecordDatas() {
        List<InvestRecord> investRecords = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            InvestRecord record = new InvestRecord();
            record.date = "20160608";
            record.money = (int)(Math.random() * 20000);
            record.purchaser = "180****9115";
            investRecords.add(record);
        }
        return investRecords;
    }
}

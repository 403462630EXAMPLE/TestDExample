package cn.hdmoney.hdy.mvp.presenter;

import android.app.Activity;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Program;
import cn.hdmoney.hdy.act.FinancialDetailActivity;
import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.guide.entity.BannerItem;
import cn.hdmoney.hdy.mvp.model.InvestModel;
import cn.hdmoney.hdy.mvp.ui.InvestUi;
import cn.hdmoney.hdy.mvp.ui.InvestUiCallback;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.utils.MyCommontUtils;

/**
 * Created by Administrator on 2016/5/25.
 */
public class InvestPresenter extends Presenter<InvestUi, InvestUiCallback> {
    private final Activity context;

    @Inject
    InvestModel investModel;

    public InvestPresenter(Activity context, InvestUi ui) {
//       需要注意的地方
        super(ui);
        this.context = context;
        MyApplication.from().inject(this);

    }

    @Override
    protected void populateUi(InvestUi ui) {
//        请求数据，并调用ui里面的显示方法
        investModel.getBannerList(context, ui);
        investModel.getProductList(context, ui,1);
        investModel.getProgrameList(context, ui);
    }

    @Override
    protected InvestUiCallback createUiCallback(final InvestUi ui) {
        return new InvestUiCallback() {
            @Override
            public void onBannerItemClick(BannerItem bannerItem) {
                MyCommontUtils.makeToast(context, bannerItem.imgUrl);
            }

            @Override
            public void onProgramItemClick(Program program) {
                MyCommontUtils.makeToast(context, program.proname);
            }

            @Override
            public void onProduckItemClick(Bid product) {
                IntentUtils.setIntent(context, FinancialDetailActivity.class);
            }

            @Override
            public void pulltoloadMore(int page) {
                investModel.getProductList(context, ui, page);

            }

        };
    }

}

package cn.hdmoney.hdy.mvp.presenter;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.InvestmentModel;
import cn.hdmoney.hdy.mvp.ui.InvestmentRecordUi;
import cn.hdmoney.hdy.mvp.ui.InvestmentRecordUiCallBack;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentRecordPresenter extends Presenter<InvestmentRecordUi, InvestmentRecordUiCallBack> {

    public final static int FLAG_ONLINE = 0;
    public final static int FLAG_COMPLETE = 1;

    @Inject
    InvestmentModel model;

    private int flag;

    public InvestmentRecordPresenter(InvestmentRecordUi ui) {
        this(ui, FLAG_ONLINE);
    }

    public InvestmentRecordPresenter(InvestmentRecordUi ui, int flag) {
        super(ui);
        this.flag = flag;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(InvestmentRecordUi ui) {
        if (flag == 0) {
            ui.showOnlineProjects(model.getOnlineInvestmentRecords());
        } else {
            ui.showCompleteProjects(model.getCompleteInvestmentRecords());
        }
    }

    @Override
    protected InvestmentRecordUiCallBack createUiCallback(InvestmentRecordUi ui) {
        return null;
    }
}

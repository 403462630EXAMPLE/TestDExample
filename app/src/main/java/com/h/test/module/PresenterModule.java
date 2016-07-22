package cn.hdmoney.hdy.module;


import cn.hdmoney.hdy.mvp.presenter.ActivityPresenter;
import cn.hdmoney.hdy.mvp.presenter.FinancialBuyPresenter;
import cn.hdmoney.hdy.mvp.presenter.FinancialDetailPresenter;
import cn.hdmoney.hdy.mvp.presenter.FinancialPresenter;
import cn.hdmoney.hdy.mvp.presenter.InvestPresenter;
import cn.hdmoney.hdy.mvp.presenter.InvestmentRecordPresenter;
import cn.hdmoney.hdy.mvp.presenter.MainPresenter;
import cn.hdmoney.hdy.mvp.presenter.MyCouponPresenter;
import cn.hdmoney.hdy.mvp.presenter.MyRecommendPresenter;
import cn.hdmoney.hdy.mvp.presenter.PaymentRecordPresenter;
import dagger.Module;

/**
 * Created by wzt on 15/5/12.
 */
@Module(
        injects = {
                MainPresenter.class,
                InvestPresenter.class,
                FinancialPresenter.class,
                FinancialDetailPresenter.class,
                FinancialBuyPresenter.class,
                PaymentRecordPresenter.class,
                InvestmentRecordPresenter.class,
                MyCouponPresenter.class,
                MyRecommendPresenter.class,
                ActivityPresenter.class
//                LegalPresenter.class,
//                FeedbackPresenter.class,
//                SettingsPresenter.class,
//                HudongPresenter.class,
//                VCodePresenter.class,
//                SmsCodePresenter.class,
//                Login2Presenter.class,
//                RegisterPresenter.class,
//                MePresenter.class,
//                MyProfilePresenter.class,
//                VotePresenter.class,
//                MediaPresenter.class,
//                CouponPresenter.class,
//                CouponDetailsPresenter.class,
//                NewsPresenter.class
        }
)
public class PresenterModule {
}

package cn.hdmoney.hdy.mvp.presenter;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.RecommendModel;
import cn.hdmoney.hdy.mvp.ui.MyRecommendUi;
import cn.hdmoney.hdy.mvp.ui.MyRecommendUiCallBack;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyRecommendPresenter extends Presenter<MyRecommendUi, MyRecommendUiCallBack> {

    public final static int FLAG_FRIENDS_RECOMMEND = 0;
    public final static int FLAG_REWARD_DETAILS = 1;

    @Inject
    RecommendModel model;

    private int flag;

    public MyRecommendPresenter(MyRecommendUi ui) {
       this(ui, FLAG_FRIENDS_RECOMMEND);
    }

    public MyRecommendPresenter(MyRecommendUi ui, int flag) {
        super(ui);
        this.flag = flag;
        MyApplication.from().inject(this);
    }

    @Override
    protected void populateUi(MyRecommendUi ui) {
        if (flag == FLAG_FRIENDS_RECOMMEND) {
            ui.showRecommends(model.getRecommends());
        } else {
            ui.showRewardDetails(model.getRewardDetails());
        }
    }

    @Override
    protected MyRecommendUiCallBack createUiCallback(MyRecommendUi ui) {
        return null;
    }
}

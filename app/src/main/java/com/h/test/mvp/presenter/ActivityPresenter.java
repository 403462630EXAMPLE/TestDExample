package cn.hdmoney.hdy.mvp.presenter;

import android.content.Context;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Activity;
import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.ActivityModel;
import cn.hdmoney.hdy.mvp.ui.ActivityUi;
import cn.hdmoney.hdy.mvp.ui.ActivityUiCallback;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class ActivityPresenter extends Presenter<ActivityUi,ActivityUiCallback> implements ActivityUiCallback {

    private Context context;
    @Inject
    ActivityModel activityModel;

    public ActivityPresenter(Context context, ActivityUi ui) {
        super(ui);
        this.context = context;
        MyApplication.from().inject(this);

    }
    @Override
    protected void populateUi(ActivityUi ui) {
        activityModel.getActivities(context, ui);
    }

    @Override
    protected ActivityUiCallback createUiCallback(ActivityUi ui) {
        return this;
    }


    @Override
    public void onItemClick(Activity activity) {

    }

    @Override
    public void pullToloadMore(int bidType, int page) {

    }

    @Override
    public void pullToRefresh(int bidType) {

    }
}

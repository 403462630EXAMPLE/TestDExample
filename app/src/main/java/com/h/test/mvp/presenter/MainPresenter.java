package cn.hdmoney.hdy.mvp.presenter;

import android.app.Activity;

import com.liuguangqiang.android.mvp.Presenter;

import javax.inject.Inject;

import cn.hdmoney.hdy.app.MyApplication;
import cn.hdmoney.hdy.mvp.model.MainModel;
import cn.hdmoney.hdy.mvp.ui.MainUi;
import cn.hdmoney.hdy.mvp.ui.MainUiCallback;

/**
 * Created by Administrator on 2016/5/24.
 */
public class MainPresenter extends Presenter<MainUi,MainUiCallback> {
    private  Activity mContext;
    @Inject
    MainModel mainModel;

    public MainPresenter(Activity context,MainUi ui) {
        super(ui);
        mContext = context;
        MyApplication.from().inject(this);
        mainModel.checkUpdate(mContext);
    }
//ui接收model传过来的数据
    @Override
    protected void populateUi(MainUi ui) {
        ui.showMenu(mainModel.getMenu(mContext));
    }

    @Override
    protected MainUiCallback createUiCallback(MainUi ui) {
        return null;
    }
}

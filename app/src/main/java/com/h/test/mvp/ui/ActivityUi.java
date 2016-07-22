package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Activity;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public interface ActivityUi extends BaseUi<ActivityUiCallback>{

    public void showList(List<Activity> activityList);

}

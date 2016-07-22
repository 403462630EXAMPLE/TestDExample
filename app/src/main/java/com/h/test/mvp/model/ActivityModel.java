package cn.hdmoney.hdy.mvp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Activity;
import cn.hdmoney.hdy.mvp.ui.ActivityUi;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class ActivityModel {
    @Inject
    public ActivityModel() {

    }
    public List<Activity> getActivities(Context context, ActivityUi ui) {
        List<Activity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        ui.showList(list);
        return  list;
    }
}

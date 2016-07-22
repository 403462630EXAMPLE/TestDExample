package cn.hdmoney.hdy.mvp.ui;

import cn.hdmoney.hdy.Entity.Activity;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public interface ActivityUiCallback {
    public void onItemClick(Activity activity);
    public void pullToloadMore(int bidType,int page);
    public void pullToRefresh(int bidType);
}

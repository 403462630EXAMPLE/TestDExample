package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.MainMenuItem;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface MainUi extends BaseUi<MainUiCallback> {
    void showMenu(List<MainMenuItem> list);
}

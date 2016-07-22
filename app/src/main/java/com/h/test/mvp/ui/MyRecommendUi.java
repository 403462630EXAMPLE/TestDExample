package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Recommend;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public interface MyRecommendUi extends BaseUi<MyRecommendUiCallBack>{
    public void showRecommends(List<Recommend> recommends);
    public void showRewardDetails(List<Recommend> recommends);
}

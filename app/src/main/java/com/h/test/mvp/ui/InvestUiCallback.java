package cn.hdmoney.hdy.mvp.ui;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Program;
import cn.hdmoney.hdy.guide.entity.BannerItem;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface InvestUiCallback {
    void onBannerItemClick(BannerItem bannerItem);
    void onProgramItemClick(Program program);
    void onProduckItemClick(Bid product);
    void pulltoloadMore(int page);
}

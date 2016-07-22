package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Program;
import cn.hdmoney.hdy.guide.entity.BannerItem;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface InvestUi extends BaseUi<InvestUiCallback>{
    void showBanner(List<BannerItem> list);
    void showPrograme(List<Program> list);
    void showProduct(List<Bid> list);

}

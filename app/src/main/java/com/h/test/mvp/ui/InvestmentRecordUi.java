package cn.hdmoney.hdy.mvp.ui;

import com.liuguangqiang.android.mvp.BaseUi;

import java.util.List;

import cn.hdmoney.hdy.Entity.InvestmentRecord;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public interface InvestmentRecordUi extends BaseUi<InvestmentRecordUiCallBack>{
    public void showOnlineProjects(List<InvestmentRecord> records);
    public void showCompleteProjects(List<InvestmentRecord> records);
}

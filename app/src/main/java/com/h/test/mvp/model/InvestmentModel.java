package cn.hdmoney.hdy.mvp.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.InvestmentRecord;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentModel {
    @Inject
    public InvestmentModel() {
    }

    public List<InvestmentRecord> getOnlineInvestmentRecords() {
        List<InvestmentRecord> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }

    public List<InvestmentRecord> getCompleteInvestmentRecords() {
        List<InvestmentRecord> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }
}

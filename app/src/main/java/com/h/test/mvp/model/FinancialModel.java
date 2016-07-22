package cn.hdmoney.hdy.mvp.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Financial;
import cn.hdmoney.hdy.Entity.FinancialDetail;

/**
 * Created by Administrator on 2016/5/30 0030.
 */
public class FinancialModel {

    @Inject
    public  FinancialModel() {}

    public List<Financial> getFinancials() {
        List<Financial> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }

    public FinancialDetail getFinancialDetail() {
        return null;
    }
}

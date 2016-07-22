package cn.hdmoney.hdy.mvp.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Recommend;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class RecommendModel {

    @Inject
    public RecommendModel() {
    }

    public List<Recommend> getRecommends() {
        List<Recommend> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }

    public List<Recommend> getRewardDetails() {
        List<Recommend> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        return list;
    }
}

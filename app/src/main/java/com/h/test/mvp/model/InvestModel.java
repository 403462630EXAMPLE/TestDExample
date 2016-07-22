package cn.hdmoney.hdy.mvp.model;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Program;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.guide.entity.BannerItem;
import cn.hdmoney.hdy.mvp.ui.InvestUi;

/**
 * Created by Administrator on 2016/5/25.
 */
public class InvestModel {
    @Inject
    public InvestModel() {

    }
    public ArrayList<Bid> list2;
    public List<BannerItem> getBannerList(Context context, InvestUi ui) {
        String[] urls = new String[]{"http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
                "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
                "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
                "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
                "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
        };
        String[] titles = new String[]{
                "伪装者:胡歌演绎'痞子特工'",
                "无心法师:生死离别!月牙遭虐杀",
                "花千骨:尊上沦为花千骨",
                "综艺饭:胖轩偷看夏天洗澡掀波澜",
                "碟中谍4:阿汤哥高塔命悬一线,超越不可能",
        };
        ArrayList<BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            BannerItem bannerItem = new BannerItem();
            bannerItem.imgUrl = urls[i];
            bannerItem.title = titles[i];
            list.add(bannerItem);
        }
//       必须调用这一句
        ui.showBanner(list);
        return list;
    }
//    获取理财产品列表

    public List<Bid> getProductList(Context context, InvestUi ui,int page) {

//        ApiFactory.getHdyApi().getHomeResult("100000000000",1).subscribe(new Observer<Result<Result.HomeResult>>() {
//
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Result<Result.HomeResult> bidResultResult) {
//                if (bidResultResult.isSuccess()) {
//                    Logs.i(bidResultResult.result.bidList.size());
//                    list2 = new ArrayList<>();
//                    list2.addAll(bidResultResult.result.bidList);
//                }
//            }
//        });
        ArrayList<Bid> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Bid bid = new Bid();
            bid.name = "高利银第11期";
            bid.typeName = "高利银";
            bid.apr = 9;
            bid.addInterest = 0.5;
            bid.schedule = 50;
            bid.period = "5";
            bid.residueShare = 170;
            list2.add(bid);
        }
        ui.showProduct(list2);
        return  list2;
    }
//    获取项目列表
    public List<Program> getProgrameList(Context context, InvestUi ui) {
        ArrayList<Program> list = new ArrayList<>();
        String[] proname = new String[]{"新手指引", "产品介绍", "安全保障", "运营数据"};

        TypedArray proimg = context.getResources().obtainTypedArray(R.array.fra_home_img);
        for (int i = 0; i < proname.length; i++) {
            Program program = new Program();
            program.proimg = proimg.getResourceId(i, 0);
            program.proname = proname[i];
            list.add(program);
        }
        ui.showPrograme(list);
        return list;
    }


}

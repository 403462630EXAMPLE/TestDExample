package cn.hdmoney.hdy.Entity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public  class Result<T> {
    /**
     * resultCode : ”000000”
     * resultDesc : ”成功”
      */

    public String resultCode;
    public String resultDesc;

    public T result;

    public boolean isSuccess() {
        return "000000".equals(resultCode);
    }

    public static class CheckRegisterResult {
        public static final int NO_EXIST = 0;
        public static final int EXIST = 1;

        public int exist;
    }

    public static class RegisterResult{
        public String uid;
    }

    public static class LoginResult{
        public String token;
        public User user;
    }

    public static class BidResult{
        public int count;
        public List<Bid> bidList;
    }

    public static class BidInfoResult{
        public Bid bidInfo;
        public List<InvestRecord> investRecords;
    }

    public static class ListResult<T> {
        public int pages;
        public List<T> orderList;
        public List<T> recommendList;
        public List<T> logList;
        public List<T> awardList;
    }

    public static class ActivityListResult{
        public int pages;
        public List<Activity> activityList;
    }

    public static class MessageListResult{
        public int pages;
        public List<Message> messageList;
    }

    public static class UploadResult{
        public long fileId;
        public String fileUrl;
    }

    public static class RecommendResult{
        public String url;
        public String url2code;
        public List<Recommend> recommendList;
        public List<Award> awardList;
    }

    /**
     * 首页
     */
    public static class HomeResult{
        public List<Banner> bannerList;
        public List<Link> linkList;
        public List<Bid> bidList;
    }

    public static class GoBuyResult{
        public Bid bidInfo;
        public List<Coupon> couponList;
    }

}

package cn.hdmoney.hdy.constant;

/**
 * Created by Administrator on 2016/5/24.
 */
public class Constants {
    public static String REAL_PACKAGE_NAME = "cn.hdmoney.hdy";
    public static final int PAGE_SIZE = 10;

    //标的类型
    public static final int BID_TYPE_ALL = 0;
    public static final int BID_TYPE_WLY = 1; //稳利银
    public static final int BID_TYPE_GLY = 2; //高利银

    //收支记录类型
    public static final int PAYMENT_RECORD_TYPE_ALL = 0;
    public static final int PAYMENT_RECORD_TYPE_IN = 1; //充值
    public static final int PAYMENT_RECORD_TYPE_OUT = 2; //提现

    //投资记录类型
    public static final int INVESTMENT_RECORD_TYPE_ALL = 0;
    public static final int INVESTMENT_RECORD_TYPE_ONGOING = 1; //投资中
    public static final int INVESTMENT_RECORD_TYPE_COMPLETE = 2; //已经投资

    //我的推荐类型
    public static final int MY_RECOMMEND_TYPE_ALL = 0;
    public static final int MY_RECOMMEND_TYPE_RECOMMEND = 1; //投资中
    public static final int MY_RECOMMEND_TYPE_AWARD = 2; //已经投资

    //我的优惠劵类型
    public static final int MY_COUPON_TYPE_ALL = 0;
    public static final int MY_COUPON_TYPE_RED_PACKET = 1; //红包
    public static final int MY_COUPON_TYPE_ADD_RATE = 2; //加息卷

    //交易密码设置类型
    public static final int TRADE_PASSWORD_TYPE_SETTING = 1;
    public static final int TRADE_PASSWORD_TYPE_MODIFY = 2;
    public static final int TRADE_PASSWORD_TYPE_RESET = 3;
}

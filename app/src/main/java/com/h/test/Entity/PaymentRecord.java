package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class PaymentRecord {
    public long id; //记录ID
    public String actionName; //操作名称
    public double actionMoney; //操作金额
    public double amount; //用户余额
    public String date; //起息日期
}

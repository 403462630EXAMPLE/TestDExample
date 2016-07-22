package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class Account {
    public long uid; //用户ID
    public double balance; //可用余额(保留2位小数)
    public double freezeAmount; //冻结金额(保留2位小数)
    public double investing; //在投本金(保留2位小数)
    public double interestAmount; //累计收益(保留2位小数)

    public double getTotalMoney() {
        return balance + freezeAmount + investing;
    }
}

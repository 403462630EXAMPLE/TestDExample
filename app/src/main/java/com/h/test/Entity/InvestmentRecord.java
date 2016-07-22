package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class InvestmentRecord {
    public long id; //订单ID
    public String bidName; //标的名称
    public double investMoney; //投资金额
    public String interestAmount; //预期收益
    public String interestDate; //起息日期
    public String expireDate; //到期日期
    public String period; //投资周期（如30天，3个月，1年）
}

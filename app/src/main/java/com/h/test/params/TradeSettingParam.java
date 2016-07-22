package cn.hdmoney.hdy.params;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class TradeSettingParam {
    public int type; //yes	1：设置支付密码 2：修改支付密码 3：重置支付密码
    public long uid; //Yes	,
    public String mobile; //yes	手机号码
    public String verifyCode; //yes	收到的6位数字验证码
    public String oldPayPasswor; //no	Type为2时必填
    public String payPassword; //no	Type为1,2时必填 新登录密码，客户端需要MD5加密大写后传输密码应为6~20位字符，包含数字与大小字母符号中的2种！
    public String name; //no	用户真实姓名，type为3时必填
    public String idNumber; //no	用户身份证，type为3时必填
    public String bankCardNo; //no	用户银行卡号，type为3时必填
    public String token; //Yes	登录授权码
}

package cn.hdmoney.hdy.params;

/**
 * Created by Administrator on 2016/5/20.
 */
public class LoginParam {

    public LoginParam() {

    }
    public LoginParam( String mod, String act, String phone, String imei) {
//        this.app = app;
        this.mod = mod;
        this.act = act;
        this.phone = phone;
        this.imei = imei;
    }
    public String app = "api";
    public String mod = "BBAccount";
    public String act = "login";
    public String phone = "13424446822";
    public String imei = "123";



}

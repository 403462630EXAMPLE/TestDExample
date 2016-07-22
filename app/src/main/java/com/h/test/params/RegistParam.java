package cn.hdmoney.hdy.params;

/**
 * Created by Administrator on 2016/6/22.
 */
public class RegistParam {

    public RegistParam() {
    }

    /**
     * mobile : 13823242942
     * platform : 1
     * password : F33239446565D76C0E204F09B778CAF2
     * recomCode : 123
     */



    public RegistParam(String mobile, String platform, String password, String recomCode) {
        this.mobile = mobile;
        this.platform = platform;
        this.password = password;
        this.recomCode = recomCode;
    }
    private String mobile;
    private String platform;
    private String password;
    private String recomCode;

}

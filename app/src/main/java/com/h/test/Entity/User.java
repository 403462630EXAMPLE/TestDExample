package cn.hdmoney.hdy.Entity;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class User {
    public long uid;
    public String userName;
    public String sex;
    public String mobile;
    public long headImgFileId;
    public String headImgUrl;
    public String city;
    public String address;

    public int getSex() {
        if ("男".equals(sex)) {
            return 1;
        } else if ("女".equals(sex)) {
            return 2;
        } else {
            return 0;
        }
    }
}

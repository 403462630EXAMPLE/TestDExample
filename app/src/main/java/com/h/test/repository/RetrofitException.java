package cn.hdmoney.hdy.repository;

/**
 * Created by Administrator on 2016/6/27 0027.
 */
public class RetrofitException extends Exception {
    public String code;
    public String message;

    public RetrofitException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

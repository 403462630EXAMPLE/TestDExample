package cn.hdmoney.hdy.utils;

/**
 * API工具类，生产不同的API地址。
 */
public class ApiUtils {
    public static final String URL_BASE_HDY = "http://192.168.11.53:8093/hdy-web-app";
    public static final String URL_REGIST = URL_BASE_HDY+ "/user/registry.jspx";
    public static final String URL_LOGIN = URL_BASE_HDY+ "/user/login.jspx";
    public static final String URL_ISREGIST = URL_BASE_HDY+ "/user/check.jspx";


}

package cn.hdmoney.hdy.repository;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class ApiFactory {
    public static final String API_BASE_URL = "http://192.168.11.53:8093/hdy-web-app/";
    private static HdyApi hdyApi;

    public static HdyApi getHdyApi() {
        if (hdyApi == null) {
            hdyApi = RetrofitFactory.getRetrofit(API_BASE_URL).create(HdyApi.class);
        }
        return hdyApi;
    }
}

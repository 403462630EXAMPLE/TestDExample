package cn.hdmoney.hdy.utils;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import cn.hdmoney.hdy.params.LoginParam;

/**
 * Created by Administrator on 2016/5/20.
 */
public class JsonUtils {
    static Map<String,String> map = new HashMap<>();
    static Gson gson = new Gson();

    public static String getLogin(LoginParam data){
        map.clear();
//        Head head = new Head();
//        map.put("head", head);
//        map.put("data", data);
        Logs.i(gson.toJson(data));
        return gson.toJson(map);
    }

    public static Map<String,String> getGson(Object data) {
        map.clear();
        String str = DESWrapper.encryptDES(gson.toJson(data),"12345678");
        map.put("content", str);
        Logs.i("content="+str);
        return map;
    }
}

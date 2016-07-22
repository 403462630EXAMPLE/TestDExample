package cn.hdmoney.hdy.utils;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Map;


import cn.hdmoney.hdy.params.User;
import cn.hdmoney.hdy.callback.baseCallback.BeanCallback;


public class OkUtils {
    /**
     *  post 请求
     */
//    public static void post(String url, String json, Callback callback) {
//            OkHttpUtils.postString()
//                    .content(json)
//                    .url(url)
//                    .mediaType(MediaType.parse("application/json;charset=utf-8"))
//                    .build()
//                    .execute(callback);
//    }
    public static void post(String url, Map<String,String> map, BeanCallback<?> callback) {
        OkHttpUtils.post().params(map)
                .url(url)
                .build()
                .execute(callback);
    }

    /**
     *  post 上传文件请求
     */
    public static void postFile(String url, File file, StringCallback callback) {
        OkHttpUtils.postFile()
                .url(url)
                .file(file)
                .build()
                .execute(callback);
    }

    public static void get(String url,BeanCallback<User> callback) {
        OkHttpUtils.get().url(url).build().execute(callback);
    }

}

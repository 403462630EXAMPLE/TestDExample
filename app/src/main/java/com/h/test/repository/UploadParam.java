package cn.hdmoney.hdy.repository;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class UploadParam {
    private Map<String, Object> param = new HashMap<>();

    private UploadParam(){}

    public static class UploadParamBuilder {
        private UploadParam uploadParam;
        public UploadParamBuilder() {
            uploadParam = new UploadParam();
        }
        public UploadParamBuilder put(String key, Object value) {
            uploadParam.param.put(key, value);
            return this;
        }
        public UploadParam build() {
            return uploadParam;
        }
    }

    public String toJson() {
        return new Gson().toJson(param);
    }
}

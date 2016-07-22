package cn.hdmoney.hdy.callback.baseCallback;

import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLDecoder;

import cn.hdmoney.hdy.utils.DESWrapper;
import cn.hdmoney.hdy.utils.Logs;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/23.
 */
public abstract class BeanCallback<T> extends Callback<T> {

    @Override
    public T parseNetworkResponse(Response response) throws Exception {
        ParameterizedType genericType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type[] types = genericType.getActualTypeArguments();
        Type type = types[0];
        String json = URLDecoder.decode(response.body().string(),"UTF-8");

        Logs.i(json);
        String decjson = DESWrapper.decryptDES(json,"12345678");
        String decjson2 =URLDecoder.decode(decjson,"UTF-8");
        Logs.i(decjson2);

        return new Gson().fromJson(decjson2,type);
    }

}


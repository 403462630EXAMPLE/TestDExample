package cn.hdmoney.hdy.callback.baseCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/20.
 */
public abstract class MyStringCallback extends com.zhy.http.okhttp.callback.Callback<String>{

    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return null;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(String response) {

    }
}

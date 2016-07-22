package cn.hdmoney.hdy.callback.baseCallback;

import com.google.gson.Gson;

import cn.hdmoney.hdy.params.User;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/23.
 */
public abstract class LoginCallback extends com.zhy.http.okhttp.callback.Callback<User> {
    @Override
    public User parseNetworkResponse(Response response) throws Exception {

        return new Gson().fromJson(response.body().string(), User.class);
    }

    @Override
    public void onError(Call call, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(User response) {

    }
}

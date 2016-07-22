package cn.hdmoney.hdy.repository;

import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import cn.hdmoney.hdy.BuildConfig;
import cn.hdmoney.hdy.utils.DESWrapper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public class RetrofitFactory {

    private static final String TAG = "RetrofitFactory";

    public static Retrofit getRetrofit(String baseUrl) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }
        okHttpClientBuilder.addInterceptor(logging);
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (request.body() instanceof MultipartBody) {
                    return chain.proceed(request);
                } else {
                    if ("POST".equalsIgnoreCase(request.method())) {
                        return chain.proceed(newRequest(request));
                    } else {
                        return chain.proceed(request);
                    }
                }
            }
        });
        Retrofit.Builder builder = null;
        try {
            builder = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(UploadConvertFactory.create())
                    .addConverterFactory(HydConverterFacotory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(okHttpClientBuilder.build());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return builder.build();
    }

    private static Request newRequest(Request request) throws IOException {
        Request.Builder builder = request.newBuilder();
        RequestBody requestBody = request.body();
        String data = getRequestBodyData(requestBody);
        String content = data;
        RequestBody newBody = requestBody;
//        get方式 form表单
        if (requestBody.contentType().subtype().equalsIgnoreCase("x-www-form-urlencoded")) {
//            参数加密
            content = "content=" + DESWrapper.encryptDES(new Gson().toJson(convertToMap(data)), "12345678");
            newBody = RequestBody.create(requestBody.contentType(), content);
        } else if (requestBody.contentType().subtype().equalsIgnoreCase("json")) {
            content = "content=" + DESWrapper.encryptDES(data, "12345678");
            newBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), content);
        }
        Log.d(TAG, content);
        builder.post(newBody);
        return builder.build();
    }

    private static String getRequestBodyData(RequestBody requestBody) throws IOException {
        BufferedSink sink = Okio.buffer(Okio.sink(new ByteArrayOutputStream()));
        requestBody.writeTo(sink);
        String content = sink.buffer().readString(Charset.forName("utf-8"));
        sink.close();
        return content;
    }

    private static Map<String, Object> convertToMap(String param) {
        Map<String, Object> paramMap = new HashMap<>();
        if (!TextUtils.isEmpty(param)) {
            String[] params = param.split("&");
            for (String temp : params) {
                if (!TextUtils.isEmpty(temp)) {
                    String[] keyValue = temp.split("=");
                    if (keyValue.length == 1) {
                        paramMap.put(keyValue[0], null);
                    } else {
                        paramMap.put(keyValue[0], keyValue[1]);
                    }
                }
            }
        }
        return paramMap;
    }
}

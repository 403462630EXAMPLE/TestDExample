package com.ytx.library.provider;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.ytx.library.provider.Domain.DomainType;
import com.ytx.library.provider.converter.MyGsonConverter;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Bruce on 12/1/14.
 */
public class ApiFactory {
    private static JryApi jryApi;
    private static WwwApi wwwApi;
    private static QuotesApi quotesApi;
    private static MobileServiceApi mobileServiceApi;
    private static AudioApi audioApi;
    private static StatisticsApi statisticsApi;
    private static QuotationLoginApi quotationLoginApi;
    private static UserPermissionApi userPermissionApi;
    private static CrmApi crmApi;
    private static OpenApi openApi;

    public static void init(TokenGetter tokenGetter) {
       RetrofitFactory.setTokenGetter(tokenGetter);
    }

    public static void setTokenGetter() {}

    public synchronized static JryApi getJryApi() {
        if (jryApi == null) {
            Log.d("ApiFactory", "-----------getJryApi");
            jryApi = RetrofitFactory.getRestAdapter(DomainType.JRY).create(JryApi.class);
        }
        return jryApi;
    }

    public synchronized static QuotationLoginApi getQuotationLoginApi(){
        if(quotationLoginApi == null){
            quotationLoginApi = RetrofitFactory.getRestAdapter(DomainType.LOGINSERVER).create(QuotationLoginApi.class);
        }
        return quotationLoginApi;
    }

    public synchronized static WwwApi getWwwApi(){
        if(wwwApi == null){
            wwwApi = RetrofitFactory.getRestAdapter(DomainType.WWW).create(WwwApi.class);
        }
        return wwwApi;
    }

    public synchronized static QuotesApi getQuotesApi(){
        if(quotesApi == null){
            quotesApi = RetrofitFactory.getRestAdapter(DomainType.QUOTES).create(QuotesApi.class);
        }
        return quotesApi;
    }

    public synchronized static MobileServiceApi getMobileServiceApi(){
        if(mobileServiceApi == null){
            mobileServiceApi = RetrofitFactory.getRestAdapter(DomainType.MOBILESERVICE).create(MobileServiceApi.class);
        }
        return mobileServiceApi;
    }

    public synchronized static AudioApi getAudioApi(){
        if(audioApi == null){
            audioApi = RetrofitFactory.getRestAdapter(DomainType.AUDIO).create(AudioApi.class);
        }
        return audioApi;
    }

    public synchronized  static StatisticsApi getStatisticsApi() {
        if (statisticsApi == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(Domain.get(DomainType.STATISTICS));
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
            builder.setConverter(new MyGsonConverter(new Gson()));

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
            OkClient okClient = new OkClient(okHttpClient);
            builder.setClient(okClient);

            statisticsApi = builder.build().create(StatisticsApi.class);
        }
        return statisticsApi;
    }

    public synchronized static UserPermissionApi getUserPermissionApi() {
        if (userPermissionApi == null) {
            userPermissionApi = RetrofitFactory.getRestAdapter(DomainType.USER_PERMISSION).create(UserPermissionApi.class);
        }
        return userPermissionApi;
    }

    public synchronized static CrmApi getCrmApi() {
        if (crmApi == null) {
            crmApi = RetrofitFactory.getRestAdapter(DomainType.CRM).create(CrmApi.class);
        }
        return crmApi;
    }

    public synchronized static OpenApi getOpenApi() {
        if (openApi == null) {
            openApi = RetrofitFactory.getRestAdapter(DomainType.QUERY_USER).create(OpenApi.class);
        }
        return openApi;
    }

    public static void clear() {
        jryApi = null;
        wwwApi = null;
        quotesApi = null;
        mobileServiceApi = null;
        audioApi = null;
        statisticsApi = null;
        userPermissionApi = null;
        crmApi = null;
        openApi = null;
    }


    public static ReportStartApi getReportStartApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint("http://tt.device.baidao.com");
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        OkClient okClient = new OkClient(okHttpClient);
        builder.setClient(okClient);

        RestAdapter restAdapter = builder.build();
        return restAdapter.create(ReportStartApi.class);
    }
}

package com.ytx.library.provider;

import android.text.TextUtils;

import com.squareup.okhttp.OkHttpClient;
import com.ytx.library.provider.exception.ResponseError;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Bruce on 12/1/14.
 */
class RetrofitFactory {

    private static final HashMap<Domain.DomainType, RestAdapter> adapters = new HashMap<Domain.DomainType, RestAdapter>();
    private static TokenGetter tokenGetter;

    private RetrofitFactory() {
    }

    public static void setTokenGetter(TokenGetter tokenGetter) {
        RetrofitFactory.tokenGetter = tokenGetter;
    }

    static private RestAdapter.Builder createAdapterBuilder(final String domain) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(domain);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        return builder;
    }

    public static RestAdapter getAgentRestAdapter() {
        return createAdapterBuilder("http://az.compass-service.baidao.com").build();
    }

    public static RestAdapter getRestAdapter(Domain.DomainType domainType) {
//        if (adapters.containsKey(domainType)) {
//            return adapters.get(domainType);
//        }

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(Domain.get(domainType));
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        OkClient okClient = new OkClient(okHttpClient);
        builder.setClient(okClient);

        if (domainType == Domain.DomainType.JRY) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    if (tokenGetter != null) {
                        String token = tokenGetter.get();
                        if (!TextUtils.isEmpty(token)) {
                            request.addQueryParam("token", token);
                        }
                    }

                }
            })
                    .setErrorHandler(new ErrorHandler() {
                                         @Override
                                         public Throwable handleError(RetrofitError cause) {
                                             Response response = cause.getResponse();
                                             if (response == null) {
                                                 return cause;
                                             }
                                             BufferedReader reader = null;
                                             String msg = null;
                                             try {
                                                 InputStream is = response.getBody().in();
                                                 reader = new BufferedReader(new InputStreamReader(is));
                                                 StringBuffer buffer = new StringBuffer();
                                                 String line = null;
                                                 while ((line = reader.readLine()) != null) {
                                                     buffer.append(line);
                                                 }
                                                 msg = buffer.toString();
                                             } catch (IOException e) {

                                             } finally {
                                                 if (reader != null) {
                                                     try {
                                                         reader.close();
                                                     } catch (IOException e) {

                                                     }
                                                 }
                                             }
                                             ResponseError error = new ResponseError(response.getStatus(), msg);
                                             return error;
                                         }
                                     }

                    );
        }

        RestAdapter restAdapter = builder.build();
//        adapters.put(domainType, restAdapter);
        return restAdapter;
    }
}

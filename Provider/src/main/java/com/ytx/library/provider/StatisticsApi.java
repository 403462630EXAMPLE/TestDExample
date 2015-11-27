package com.ytx.library.provider;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by hexi on 15/3/12.
 */
public interface StatisticsApi {

    @Headers("Content-Encoding: gzip")
    @POST("/")
    public Response sendLog(@Query("os") String os,
                            @Query("appVersion") String appVersion,
                            @Query("deviceId") String deviceId,
                            @Body String logData);
}

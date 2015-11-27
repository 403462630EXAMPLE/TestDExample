package com.ytx.library.provider;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by hexi on 15/4/17.
 */
public interface ReportStartApi {

    @FormUrlEncoded
    @POST("/jry-device/analytics/start")
    public Observable<Void> reportStart(@Field("deviceToken") String deviceId,
                                        @Field("username") String username,
                                        @Field("marketType") int marketId,
                                        @Field("imei") String imei,
                                        @Field("deviceModel") String deviceModel,
                                        @Field("versionId") String versionId,
                                        @Field("appId") String appId,
                                        @Field("osName") String osName,
                                        @Field("osVersion") String osVersion,
                                        @Field("serverId") int serverId);
}

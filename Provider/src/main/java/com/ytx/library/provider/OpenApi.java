package com.ytx.library.provider;

import com.baidao.data.QueryUserResult;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by chengxin on 10/12/15.
 */
public interface OpenApi {
    @FormUrlEncoded
    @POST("/openaccount-server/api/1/ytx/001000/query/user/username/json")
    Observable<QueryUserResult> queryUserInfo(@Field("userName") String userName, @Field("server") String server);
}

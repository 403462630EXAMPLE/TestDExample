package com.ytx.library.provider;

import com.baidao.data.Authentication;
import com.baidao.data.CheckUserResult;
import com.baidao.data.LoginResult;
import com.baidao.data.News;
import com.baidao.data.Result;
import com.baidao.data.SSOLogin;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rjhy on 14-12-5.
 */
public interface WwwApi {

    @GET("/news.do")
    public Observable<Result<ArrayList<News>>> getNews(@Query("channelIds") String channelIds, @Query("size") int size);

    @POST("/sso/authentications/open")
    public Observable<LoginResult> ssoLogin(@Body SSOLogin ssoLogin);

    @POST("/sso/authentications/bindAccount")
    public Observable<LoginResult> ssoBindAccount(@Body Authentication authentication);

    @GET("/sso/ajax/needLogout")
    public Observable<CheckUserResult> needLogout(@Query("username") String username);
}

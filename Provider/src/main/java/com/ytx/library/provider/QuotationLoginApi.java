package com.ytx.library.provider;

import com.baidao.data.LoginMessage;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by BurizaDo on 3/18/15.
 */
public interface QuotationLoginApi {
    @GET("/api/hq0/login")
    LoginMessage getLoginServer(@Query("un") String userName,
                                @Query("up") String userPassword,
                                @Query("ct") int clientType,
                                @Query("cv") String clientVersion,
                                @Query("ut") int userType,
                                @Query("vi") int isVisitor);

}

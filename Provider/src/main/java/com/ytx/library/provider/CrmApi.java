package com.ytx.library.provider;

import com.baidao.data.Result;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rjhy on 15-8-27.
 */
public interface CrmApi {

    @GET("/crm-mobile/callInfo/judgeCallValid")
    Observable<Result> judgeCallValid(@Query("companyId") int companyId, @Query("empId") long empId);

    @GET("/crm-mobile/callInfo/updateCusCallFlag")
    Observable<Result> updateCusCallFlag(@Query("companyId") int companyId, @Query("cusName") String username, @Query("flag") int flag);

    @FormUrlEncoded
    @POST("/crm-mobile/callInfo/phoneNotfaze")
    Observable<Result> phoneNotfaze(@Field("companyId") int companyId, @Field("cusName") String username, @Field("flag") int flag, @Field("empId") long empId);

    @GET("/crm-mobile/callInfo/getPhoneFaze")
    Observable<Result> getPhoneFaze(@Query("companyId") int companyId, @Query("ytxUserName") String username);
}

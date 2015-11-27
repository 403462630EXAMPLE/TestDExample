package com.ytx.library.provider;

import com.baidao.data.Agent;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by burizado on 14-12-3.
 */
public interface DomainApi {
    @GET("/agent")
    public Observable<Agent> getAgent(@Query("marketId") int id, @Query("appId") String appId, @Query("system") String system);
}

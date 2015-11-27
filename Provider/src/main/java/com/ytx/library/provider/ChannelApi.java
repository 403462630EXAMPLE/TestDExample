package com.ytx.library.provider;

import com.baidao.data.Channel;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Bruce on 12/1/14.
 */
public interface ChannelApi {
    @GET("/channels/mine/v3")
    Observable<List<Channel>> getChannels();
}

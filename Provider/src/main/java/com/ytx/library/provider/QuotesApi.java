package com.ytx.library.provider;

import com.baidao.data.CJCalendarResult;
import com.baidao.data.CalendarHistory;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rjhy on 14-12-5.
 */
public interface QuotesApi {
    @GET("/api/hq2/jry-cms/cJRLCalendar/getCJCalendarListForWeb")
    public Observable<ArrayList<CJCalendarResult>> getCJCalendars(@Query("timestamp") String timestamp, @Query("size") int size);

    @GET("/api/hq2/jry-cms/cJRLCalendar/histories")
    public Observable<ArrayList<CalendarHistory>> getCalendarHistories(@Query("fid") String fid, @Query("size") int size);
}

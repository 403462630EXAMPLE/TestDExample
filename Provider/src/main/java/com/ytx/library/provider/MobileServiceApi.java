package com.ytx.library.provider;

import com.baidao.data.FinanceCalendar;
import com.baidao.data.ImportantEvent;
import com.baidao.data.Article;
import com.baidao.data.TopMessage;
import com.ytx.library.provider.path.MobileServicePathname;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rjhy on 14-12-5.
 */
public interface MobileServiceApi {

    @GET("/articlelist")
    public Observable<ArrayList<Article>> getArticles(@Query("timestamp") String publishTimestamp, @Query("count") int size, @Query("expertId")  long expertId);

    @GET("/bulletin/latest")
    public Observable<TopMessage> getTopMessage(@Query("userType") int userType);

    @GET("/aggregate/list")
    public Observable<List<ImportantEvent>> getImportEvents(@Query("userType") int userType, @Query("time") long time, @Query("token") String token);

    @GET("/aggregate/list")
    public Observable<List<ImportantEvent>> getImportEvents(@Query("userType") int userType, @Query("token") String token);

    @GET("/aggregate/marketinformation")
    public Observable<List<ImportantEvent>> getQuoteInformation(@Query("userType") int userType, @Query("time") long time, @Query("token") String token);

    @GET(MobileServicePathname.getFinanceCalendar)
    public Observable<ArrayList<FinanceCalendar>> getFinanceCalendar(@Query("beginDate") long beginDate, @Query("endDate") long endDate);

    @GET("/aggregate/article")
    public Observable<ImportantEvent> getArticleOrActivity(@Query("id") int id);


}

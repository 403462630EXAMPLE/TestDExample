package com.ytx.library.provider;

import com.baidao.data.ChatResult;
import com.baidao.data.Gift;
import com.baidao.data.Live;
import com.baidao.data.LiveRoom;
import com.baidao.data.LoginResult;
import com.baidao.data.MyNote;
import com.baidao.data.NewsCenter;
import com.baidao.data.NoticeHistoryResult;
import com.baidao.data.Point;
import com.baidao.data.Register;
import com.baidao.data.Result;
import com.baidao.data.Retrieve;
import com.baidao.data.Room;
import com.baidao.data.Roomer;
import com.baidao.data.Strategy;
import com.baidao.data.TeacherZone;
import com.baidao.data.TeacherZoneAndLive;
import com.baidao.data.UpdateAppResult;
import com.baidao.data.WarningSetting;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by rjhy on 14-12-1.
 */
public interface JryApi {

    @GET("/jry-device/ajax/liveRoom/rooms")
    public Observable<ArrayList<Room>> getRooms();

    @GET("/jry-device/ajax/liveRoom/followedRoom")
    public Observable<ArrayList<LiveRoom>> getFollowedRoom();

    @GET("/jry-device/ajax/liveRoom/lives")
    public Observable<List<Live>> getLives(
            @Query("roomId") long roomId,
            @Query("recordId") long recordId,
            @Query("pointId") long pointId,
            @Query("noteTimestamp") long noteTimestamp,
            @Query("size") int size
    );

    @GET("/jry-device/ajax/liveRoom/teacherAndLives")
    public Observable<ArrayList<TeacherZoneAndLive>> getTeacherZoneAndLives(
            @Query("roomId") long roomId,
            @Query("recordId") long recordId,
            @Query("lastTimestamp") long lastTimestamp,
            @Query("size") int size
    );

    @GET("/jry-device/ajax/liveRoom/top")
    public Observable<ArrayList<Point>> getTopPoints(@Query("roomId") long roomId);

    @GET("/jry-device/ajax/liveRoom/getChatVOs_v2.do")
    public Observable<ChatResult> getChats(
            @Query("recordId") long recordId,
            @Query("readId") long readId,
            @Query("size") int size,
            @Query("direction") String direction);

    @GET("/jry-device/ajax/liveRoom/teacherZone")
    public Observable<List<TeacherZone>> getTeacherZone(@Query("roomId") long roomId, @Query("readId") long updateTime, @Query("size") int size);

    @GET("/jry-device/ajax/liveRoom/articles")
    public Observable<List<Strategy>> getStrategy(@Query("roomId") long roomId, @Query("lastTimestamp") long lastTimestamp, @Query("size") int size);

    @GET("/jry-device/ajax/liveRoom/myNotes")
    public Observable<ArrayList<MyNote>> getMyNotes(
            @Query("roomId") long roomId,
            @Query("recordId") long recordId,
            @Query("noteTimestamp") long noteTimestamp,
            @Query("size") int size
    );

    @GET("/jry-device/ajax/liveRoom/myAllNotes")
    public Observable<ArrayList<MyNote>> getMyAllNotes(
            @Query("roomId") long roomId,
            @Query("recordId") long recordId,
            @Query("noteTimestamp") long noteTimestamp,
            @Query("size") int size
    );

    @FormUrlEncoded
    @POST("/jry-device/ajax/liveRoom/sendScript")
    public Observable<Result> sendNote(@Field("roomId") long roomId, @Field("question") String question, @Field("tUsername") String tUserName);

    @FormUrlEncoded
    @POST("/jry-device/ajax/liveRoom/sendChat")
    public Observable<Result> sendChat(@Field("roomId") long roomId, @Field("content") String content, @Field("chatId") long chatId);

    @GET("/jry-device/ajax/liveRoom/getZBRoomers_v2")
    public Observable<ArrayList<Roomer>> getRoomers(@Query("roomId") long roomId, @Query("recordId") long recordId);

    @GET("/jry-device/ajax/warningSettings/id_v2.do")
    public Observable<ArrayList<WarningSetting>> getWarningSettingsOfSid(@Query("deviceId") String deviceId, @Query("sid") String sid);

    @FormUrlEncoded
    @POST("/jry-device/ajax/warningSetting/id_v2.do")
    public Observable<WarningSetting> updateWarningSetting(@Field("deviceId") String deviceId,
                                                           @Field("id") Long id,
                                                           @Field("priceNow") Double priceNow,
                                                           @Field("priceTarget") Double priceTarget);

    @DELETE("/jry-device/ajax/warningSetting_v2.do")
    public Observable<Void> deleteWarningSettingById(@Query("id") Long id);

    @FormUrlEncoded
    @POST("/jry-device/ajax/warningSetting_v2.do")
    public Observable<WarningSetting> saveWarningSetting(@Field("deviceId") String deviceId,
                                                         @Field("sid") String sid,
                                                         @Field("priceNow") double priceNow,
                                                         @Field("priceTarget") double priceTarget,
                                                         @Field("isOpen") Boolean isOpen);

    @FormUrlEncoded
    @POST("/jry-device/ajax/warningSetting/id/openStatus_v2.do")
    public Observable<WarningSetting> switchStatusOfWarningSetting(@Field("id") Long id,
                                                                   @Field("isOpen") boolean isOpen,
                                                                   @Field("priceNow") double priceNow);

    @GET("/jry-device/ajax/notification/v5-2-1/newsCentre")
    public Observable<ArrayList<NewsCenter>> getNewsCenters(
            @Query("newsTypes") String newsTypes,
            @Query("appId") String appId,
            @Query("deviceId") String deviceId,
            @Query("timestamp") long timestamp,
            @Query("size") int size);

    @GET("/jry-device/gifts")
    public Observable<ArrayList<Gift>> getGifts(@Query("type") int type, @Query("page") int page);

    @GET("/jry-device/gifts/recommendation")
    public Observable<ArrayList<Gift>> getRecommendationGifts();

    @FormUrlEncoded
    @POST("/jry-device/openAccount/verificationCode/verify")
    public Observable<Result> checkVerificationCode(@Field("phoneNumber") String phone, @Field("verificationCode") String code);

    @FormUrlEncoded
    @POST("/jry-device/login")
    public Observable<Result<LoginResult>> login(@Field("username") String username, @Field("password") String password, @Field("appVersion") String appVersion);

    @GET("/jry-device/ajax/user/updateUserDetail.do")
    public Observable<Result> updateUserDetail(@Query("nickname") String nickname, @Query("token") String token);

    @FormUrlEncoded
    @POST("/jry-device/ajax/user/verificationCode")
    public Observable<Result> getRetrieveVerifyCode(@Field("phoneNumber") String phoneNumber, @Field("serverId") int serverId);

    @POST("/jry-device/ajax/user/resetPassword")
    public Observable<Result<LoginResult>> resetPassword(@Body Retrieve retrieve);

    @FormUrlEncoded
    @POST("/jry-device/ajax/verificationCode")
    public Observable<Result> getRegisterVerifyCode(@Field("phoneNumber") String phoneNumber);

    @POST("/jry-device/ajax/register")
    public Observable<Result<LoginResult>> register(@Body Register register);

    @FormUrlEncoded
    @POST("/jry-device/verificationCodes")
    public Observable<Result> getBindPhoneVerifyCode(@Field("phoneNumber") String phoneNumber, @Field("type") String type);

    @FormUrlEncoded
    @POST("/jry-device/ajax/user/bindPhonePassword")
    public Observable<LoginResult> bindPhone(@Field("token") String token, @Field("phoneNumber") String phoneNumber,
                                             @Field("verificationCode") String verificationCode, @Field("password") String password);

    @GET("/jry-device/ajax/device/getUserByToken")
    public Observable<Result<LoginResult>> getUserByToken(@Query("token") String token);

    @FormUrlEncoded
    @POST("/jry-device/ajax/user/feedback.do")
    public Observable<String> feedback(@Field("token") String token, @Field("content") String content,
                                       @Field("email") String email, @Field("qqnumber") String qqnumber,
                                       @Field("imgPath") String imgPath);

    @GET("/jry-device/canUpdateApp.do")
    public Observable<UpdateAppResult> checkUpdate(@Query("versionId") String versionId, @Query("marketType") int marketType,
                                                   @Query("appId") String appId);

    @FormUrlEncoded
    @POST("/jry-device/ajax/notification/saveAndroidDeviceToken.do")
    public Observable<Result> saveDeviceToken(@Field("deviceToken") String deviceToken,
                                              @Field("deviceId") String deviceId,
                                              @Field("imei") String imei,
                                              @Field("appId") String appId,
                                              @Field("serverId") Integer serverId,
                                              @Field("appVersion") String appVersion);

    @FormUrlEncoded
    @POST("/jry-device/ajax/notification/pushDevice/logout")
    public Observable<Void> clearUsernameOfDeviceToken(@Field("appId") String appId,
                                                       @Field("deviceId") String deviceId);

    @FormUrlEncoded
    @POST("/jry-device/ajax/notification/saveAndroidDeviceTokenForTradeAccount")
    public Observable<Result> saveDeviceTokenForTradeAccount(@Field("account") String account,
                                                             @Field("deviceToken") String deviceToken,
                                                             @Field("username") String username,
                                                             @Field("deviceId") String deviceId,
                                                             @Field("appId") String appId,
                                                             @Field("serverId") Integer serverId,
                                                             @Field("appVersion") String appVersion);

    @FormUrlEncoded
    @POST("/jry-device/ajax/notification/pushAccount/logout")
    public Observable<Void> clearTradeAccountOfDeviceToken(@Field("account") String account);

    @FormUrlEncoded
    @POST("/jry-device/ajax/device/push/visitor/list")
    public Observable<List<NoticeHistoryResult>> getNotificationList(@Field("serverId") int serverId,
                                                                     @Field("createTime") long createTime,
                                                                     @Field("limit") int limit);

}

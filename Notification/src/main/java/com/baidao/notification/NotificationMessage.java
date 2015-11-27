package com.baidao.notification;

import android.app.Notification;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.baidao.data.GetuiMessage;
import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by rjhy on 14-12-12.
 */
@ParcelablePlease
public class NotificationMessage implements Parcelable {

    public String title;
    public String text;
    public NotificationType type;
    public String ticker;
    public boolean play_sound;
    public boolean play_lights;
    public boolean play_vibrate;
    public String uMessage;//统计umeng notification点击量专用
    public String sid;
    public String klineType;
    public int contentId;
    public int detailId;
    public String sound;

    public NotificationMessage() {
    }

    public NotificationMessage(String title, String text, NotificationType type, String ticker, boolean play_sound, boolean play_lights, boolean play_vibrate) {
        this.title = title;
        this.text = text;
        this.type = type;
        this.ticker = ticker;
        this.play_sound = play_sound;
        this.play_lights = play_lights;
        this.play_vibrate = play_vibrate;
    }

    public static NotificationMessage fromGetuiMessage(String message) throws JSONException {
        GetuiMessage getuiMessage = new Gson().fromJson(message, GetuiMessage.class);
        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.title = getuiMessage.body.title;
        notificationMessage.text = getuiMessage.body.text;
        notificationMessage.type = NotificationType.fromInt(getuiMessage.extra.dataType);
        notificationMessage.ticker = getuiMessage.body.ticker;
        notificationMessage.play_sound = getuiMessage.isPlaySound();
        notificationMessage.play_lights = getuiMessage.isPlayLights();
        notificationMessage.play_vibrate = getuiMessage.isPlayVibrate();
        notificationMessage.sid = getuiMessage.extra.sid;
        notificationMessage.klineType = getuiMessage.extra.klineType;
        notificationMessage.sound = getuiMessage.body.sound;
        notificationMessage.contentId = getuiMessage.extra.contentId;
        notificationMessage.detailId = getuiMessage.extra.detailId;
        return notificationMessage;
    }

    public Uri getSound() {
        return Uri.parse("android.resource://com.baidao.ytxmobile/raw/" + sound);
    }

    public int getDefaults() {
        int defaults = 0;
        if (play_sound && TextUtils.isEmpty(sound)) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (play_lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        if (play_vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }

        return defaults;
    }


    public boolean getAutoCancel() {
        switch (type.getValue()) {

            default:
                return true;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        NotificationMessageParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<NotificationMessage> CREATOR = new Creator<NotificationMessage>() {
        public NotificationMessage createFromParcel(Parcel source) {
            NotificationMessage target = new NotificationMessage();
            NotificationMessageParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public NotificationMessage[] newArray(int size) {
            return new NotificationMessage[size];
        }
    };
}

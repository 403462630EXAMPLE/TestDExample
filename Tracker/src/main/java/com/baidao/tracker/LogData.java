package com.baidao.tracker;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.baidao.data.User;
import com.baidao.tools.NetworkUtil;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexi on 15/3/9.
 */
public class LogData {
    private String os = "android";
    private String osVersion;
    private String device;//设备型号
    private String screen;//分辨率
    private String network;// WIFI|3G|4G
    private String appVersion;
    private String deviceId;
    private String ip;
    private int company;//serverId
    private Integer channel;

    private String username;
    private Integer userType;
    private String customerId;

    private String eventType;
    private String event;
    private long time;

    private Map<String, String> data;

    public LogData withChannel(Integer channel) {
        this.channel = channel;
        return this;
    }

    public LogData withScreen(String screen) {
        this.screen = screen;
        return this;
    }

    public LogData withAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public LogData withDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public static enum EventType {
        PAGE("page"),
        EVENT("event");

        private EventType(String value) {
            this.value = value;
        }

        public String value;
    }

    private LogData() {
        super();
    }

    public static class Builder {
        private LogData logData;

        public Builder(Context context) {
            this.logData = new LogData();

            this.logData.osVersion = Build.VERSION.RELEASE;
            this.logData.device = Build.MODEL;

            this.logData.network = NetworkUtil.getNetworkType(context).value;
            this.logData.ip = NetworkUtil.getIp();

            this.logData.company = Util.getCompanyId(context);

            if (UserHelper.getInstance(context).isLogin()) {
                User user = UserHelper.getInstance(context).getUser();
                this.logData.username = user.getUsername();
                this.logData.userType = user.getUserType();
                this.logData.customerId = user.cusUniqueId;
            }

        }

        private Builder withEventType(EventType eventType) {
            this.logData.eventType = eventType.value;
            return this;
        }
        private Builder withEvent(String event) {
            this.logData.event = event;
            return this;
        }
        private Builder withTime(long time) {
            this.logData.time = time;
            return this;
        }

        private LogData build() {
            return this.logData;
        }

        public LogData pv(String name) {
            return this.withEventType(LogData.EventType.PAGE)
            .withEvent(name)
            .withTime(System.currentTimeMillis())
            .build();

        }

        public LogData event(String name) {
            return this.withEventType(LogData.EventType.EVENT)
                    .withEvent(name)
                    .withTime(System.currentTimeMillis())
                    .build();
        }
    }

    public LogData append(String key, String value) {
        if (TextUtils.isEmpty(key) || value == null || TextUtils.isEmpty(value.toString())) {
            return this;
        }
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.data.put(key, value);
        return this;
    }

    public LogData append(Map<String, String> values) {
        if (values == null || values.isEmpty()) {
            return this;
        }
        if (this.data == null) {
            this.data = values;
        } else {
            this.data.putAll(values);
        }
        return this;
    }
}

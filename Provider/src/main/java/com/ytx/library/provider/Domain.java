package com.ytx.library.provider;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidao.data.Agent;
import com.baidao.data.e.Server;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;

/**
 * Created by burizado on 14-11-28.
 */
public class Domain {

    public static enum DomainType {
        QUOTES("quotes"),
        QUOTESROOTER("quotesRooter"),
        WWW("www"),
        JRY("jry"),
        CHAT("chat"),
        MOBILESERVICE("mobileService"),
        AUDIO("audio"),
        OPEN_ACCOUNT_PAGE("openAccountPage"),
        TJ_PERMISSION("tjPermission"),
        LOGINSERVER("loginServer"),
        STATISTICS("statistics"),
        USER_PERMISSION("userPermission"),
        CRM("crm"),
        QUERY_USER("query_user");

        public String type;

        DomainType(String type) {
            this.type = type;
        }
    }

    static private Map<String, String> BSY = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://api.baidao.com");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://www.baidao.com");
        put(DomainType.JRY.type, "http://bsy.device.baidao.com");
        put(DomainType.CHAT.type, "bsy.chat-socket.baidao.com:11050");
        put(DomainType.MOBILESERVICE.type, "http://bsy.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://bsy.audio.baidao.com");
        put(DomainType.TJ_PERMISSION.type, "http://bsy.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://app-logs.baidao.com/post");
        put(DomainType.LOGINSERVER.type, "http://m.gwstation.baidao.com:5063");
        put(DomainType.USER_PERMISSION.type, "http://api.baidao.com/ucenter-permission");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};


    static private Map<String, String> SSY = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://api.baidao.com");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://www.baidao.com");
        put(DomainType.JRY.type, "http://ssy.device.baidao.com");
        put(DomainType.CHAT.type, "ssy.chat-socket.baidao.com:11050");
        put(DomainType.MOBILESERVICE.type, "http://ssy.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://ssy.audio.baidao.com");
        put(DomainType.TJ_PERMISSION.type, "http://ssy.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://app-logs.baidao.com/post");
        put(DomainType.LOGINSERVER.type, "http://m.gwstation.baidao.com:5063");
        put(DomainType.USER_PERMISSION.type, "http://api.baidao.com/ucenter-permission");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};

    static private Map<String, String> RJHY = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://api.baidao.com");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://www.baidao.com");
        put(DomainType.JRY.type, "http://tt.device.baidao.com");
        put(DomainType.CHAT.type, "tt.chat-socket.baidao.com:11050");
        put(DomainType.MOBILESERVICE.type, "http://tt.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://tt.audio.baidao.com");
        put(DomainType.OPEN_ACCOUNT_PAGE.type, "http://weixin2.baidao.com/assets/openAccountTT");
        put(DomainType.TJ_PERMISSION.type, "http://tt.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://app-logs.baidao.com/post");
        put(DomainType.LOGINSERVER.type, "http://m.gwstation.baidao.com:5063");
        put(DomainType.USER_PERMISSION.type, "http://api.baidao.com/ucenter-permission");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};

    static private Map<String, String> JXYR = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://api.baidao.com");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://www.baidao.com");
        put(DomainType.JRY.type, "http://yg.device.baidao.com");
        put(DomainType.CHAT.type, "yg.chat-socket.baidao.com:10050");
        put(DomainType.MOBILESERVICE.type, "http://yg.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://yg.audio.baidao.com");
        put(DomainType.OPEN_ACCOUNT_PAGE.type, "http://weixin2.baidao.com/assets/openAccountYG");
        put(DomainType.TJ_PERMISSION.type, "http://yg.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://app-logs.baidao.com/post");
        put(DomainType.LOGINSERVER.type, "http://m.gwstation.baidao.com:5063");
        put(DomainType.USER_PERMISSION.type, "http://api.baidao.com/ucenter-permission");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};

    static private Map<String, String> BSY_TEST = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://117.74.136.35:6092");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://test.www.baidao.com");
        put(DomainType.JRY.type, "http://test.bsy.device.baidao.com");
        put(DomainType.CHAT.type, "192.168.26.90:12050");
        put(DomainType.MOBILESERVICE.type, "http://test.bsy.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://192.168.26.22:3008");
        put(DomainType.TJ_PERMISSION.type, "http://test.bsy.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://192.168.26.30:12121");
        put(DomainType.LOGINSERVER.type, "http://117.74.136.35:5063");
        put(DomainType.USER_PERMISSION.type, "http://192.168.26.30:8084");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};


    static private Map<String, String> SSY_TEST = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://117.74.136.35:6092");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://test.www.baidao.com");
        put(DomainType.JRY.type, "http://test.ssy.device.baidao.com");
        put(DomainType.CHAT.type, "192.168.26.90:12050");
        put(DomainType.MOBILESERVICE.type, "http://test.ssy.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://192.168.26.22:3008");
        put(DomainType.TJ_PERMISSION.type, "http://test.ssy.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://192.168.26.30:12121");
        put(DomainType.LOGINSERVER.type, "http://117.74.136.35:5063");
        put(DomainType.USER_PERMISSION.type, "http://192.168.26.30:8084");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};


    static private Map<String, String> RJHY_TEST = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://117.74.136.35:6092");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://test.www.baidao.com");
        put(DomainType.JRY.type, "http://test.tt.device.baidao.com");
//        put(DomainType.MOBILESERVICE.type, "http://192.168.19.174:6013");
        put(DomainType.MOBILESERVICE.type, "http://test.tt.mobile-service.baidao.com");
        put(DomainType.CHAT.type, "192.168.19.176:56000");
        put(DomainType.AUDIO.type, "http://192.168.26.22:3008");
        put(DomainType.OPEN_ACCOUNT_PAGE.type, "http://180.166.190.142/assets/openAccountTT");
        put(DomainType.TJ_PERMISSION.type, "http://test.tt.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://192.168.26.30:12121");
        put(DomainType.LOGINSERVER.type, "http://117.74.136.35:5063");
        put(DomainType.USER_PERMISSION.type, "http://192.168.26.30:8084");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};

    static private Map<String, String> JXYR_TEST = new HashMap<String, String>() {{
        put(DomainType.QUOTES.type, "http://117.74.136.35:6092");
        put(DomainType.QUOTESROOTER.type, "/api/hq/");
        put(DomainType.WWW.type, "http://test.www.baidao.com");
        put(DomainType.JRY.type, "http://test.yg.device.baidao.com");
        put(DomainType.CHAT.type, "192.168.19.176:46000");
        put(DomainType.MOBILESERVICE.type, "http://test.yg.mobile-service.baidao.com");
        put(DomainType.AUDIO.type, "http://test.yg.audio.baidao.com");
        put(DomainType.OPEN_ACCOUNT_PAGE.type, "http://180.166.190.142/assets/openAccountYG");
        put(DomainType.TJ_PERMISSION.type, "http://test.yg.mobile-service.baidao.com/permission/tjx");
        put(DomainType.STATISTICS.type, "http://192.168.26.30:12121");
        put(DomainType.LOGINSERVER.type, "http://117.74.136.35:5063");
        put(DomainType.USER_PERMISSION.type, "http://192.168.26.30:8084");
        put(DomainType.CRM.type, "http://192.168.19.131");
        put(DomainType.QUERY_USER.type, "http://test.open-api.baidao.com");
    }};
    private static Map<String, String> DOMAIN = JXYR_TEST;

    static private boolean isDebug = true;

    public static void setIsDebug(boolean isDebug) {
        Domain.isDebug = isDebug;
    }

    public static void setDOMAIN(Server serverType) {
        Log.d("DOMAIN", "----------" + serverType.name);
        if (isDebug) {
            if (serverType == Server.TT) {
                DOMAIN = RJHY_TEST;
            } else if (serverType == Server.YG) {
                DOMAIN = JXYR_TEST;
            } else if (serverType == Server.SSY) {
                DOMAIN = SSY_TEST;
            }else if(serverType == Server.BSY){
                DOMAIN = BSY_TEST;
            }
        } else {
            if (serverType == Server.TT) {
                DOMAIN = RJHY;
            } else if (serverType == Server.YG) {
                DOMAIN = JXYR;
            } else if (serverType == Server.SSY) {
                DOMAIN = SSY;
            } else if(serverType == Server.BSY){
                DOMAIN = BSY;
            }
        }
    }

    public interface OnAgentFetchedListener {
        void onAgentFetched(int agentId);
    }

    public static void setupServerDomain(final Context context, int marketId, final OnAgentFetchedListener onAgentFetchedListener) {
        RetrofitFactory.getAgentRestAdapter().create(DomainApi.class).getAgent(marketId, context.getPackageName(), "Android").subscribe(new Observer<Agent>() {
            @Override
            public void onCompleted() {
                Intent intent = new Intent("domain_setup");
                context.sendBroadcast(intent);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Domain", "fetch agent error", e);
                Intent intent = new Intent("domain_setup_fail");
                context.sendBroadcast(intent);
            }

            @Override
            public void onNext(Agent agent) {
                Log.d("Domain", "agent: " + new Gson().toJson(agent));
                onAgentFetchedListener.onAgentFetched(agent.serverId);
                setDOMAIN(Server.from(agent.serverId));
            }
        });
    }

    public static String get(DomainType domainType) {
        return DOMAIN.get(domainType.type);
    }
}
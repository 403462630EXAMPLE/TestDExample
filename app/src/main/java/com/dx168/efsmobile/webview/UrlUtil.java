package com.dx168.efsmobile.webview;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.baidao.data.User;
import com.baidao.efsmobile.BuildConfig;
import com.baidao.tools.UserHelper;

/**
 * Created by Bruce on 2/5/15.
 */
public class UrlUtil {
    public static String newUrlWithTokenAgent(Context context, String url) {
        Uri.Builder uriBuilder = Uri.parse(url)
                .buildUpon();
        if (url == null) {
            return null;
        }

        if (!TextUtils.isEmpty(UserHelper.getInstance(context).getToken())) {
            uriBuilder.appendQueryParameter("token", UserHelper.getInstance(context).getToken());
        }

        String agent = com.baidao.tools.Util.getServer(context).getName();

        if (!TextUtils.isEmpty(agent)) {
            uriBuilder.appendQueryParameter("agent", agent);
        }

        return uriBuilder
                .build()
                .toString();
    }

    public static String addTokenQueryString(Context context, String url) {
        if (!TextUtils.isEmpty(Uri.parse(url).getQueryParameter("token"))) {
            return url;
        }

        return Uri.parse(url)
            .buildUpon()
            .appendQueryParameter("token", UserHelper.getInstance(context).getToken())
            .build()
            .toString();
    }

    public static String newUrlWithTokenAgentEnv(Context context, String url) {
        if (url == null) {
            return "";
        }
        String env = BuildConfig.DEBUG ? "integration" : "production";
        url = url.trim();
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return newUrlWithTokenAgent(context, url) + "&env=" + env;
        } else {
            return "http://" + newUrlWithTokenAgent(context, url) + "&env=" + env;
        }
    }

    public static String newUrlWithUsername(Context context, String url) {
        if (url == null) {
            return "";
        }
        User user = UserHelper.getInstance(context).getUser();
        if (user == null) {
            return url + "&username=";
        }
        url = url.trim();
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url + "&username=" + user.getUsername();
        } else {
            return "http://" + url + "&username=" + user.getUsername();
        }
    }

    public static String filterTokenFromUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return url.replaceAll("token=.*&", "&");
    }
}

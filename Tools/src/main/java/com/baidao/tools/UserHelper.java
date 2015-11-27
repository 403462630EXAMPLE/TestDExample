package com.baidao.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.baidao.data.User;
import com.google.gson.Gson;

/**
 * Created by chengxin on 1/13/15.
 */
public class UserHelper {

    public static String MARKET_ID = "marketId";
    public static String TOKEN = "token";
    public static String SUCCESS = "success";
    public static String PASSWORD = "password";
    public static String NEED_RE_LOGIN = "need_re_login";
    public static final String TRADE_NUMBER = "trade_number";

    private static final String KEY_USER = User.class.getName();
    private static UserHelper instance;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private User user;

    public static UserHelper getInstance(Context context) {
        if(instance == null){
            instance = new UserHelper();
            instance.sharedPreferences = context.getSharedPreferences(UserHelper.class.getName(), Context.MODE_PRIVATE);
            instance.editor = instance.sharedPreferences.edit();
        }
        instance.context = context;
        return instance;
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public User getUser() {
        if (user == null) {
            String userJson = sharedPreferences.getString(KEY_USER, "");
            if (TextUtils.isEmpty(userJson)) {
                return new User();
            }
            user = new Gson().fromJson(userJson, User.class);
        }
        return user;
    }

    public void saveUser(final User user) {
        doTransaction(new Runnable() {
            @Override
            public void run() {
                editor.putString(KEY_USER, user.toString());
                editor.putBoolean(SUCCESS, true);
            }
        });
        this.user = null;
    }

    public void doTransaction(Runnable runnable) {
        runnable.run();
        editor.commit();
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

    public void logout() {
        doTransaction(new Runnable() {
            @Override
            public void run() {
                Util.setAgentId(context, getUser().getServerId());
                editor.remove(KEY_USER);
                editor.remove(SUCCESS);
                editor.remove(TOKEN);
                editor.remove(PASSWORD);
                editor.remove(NEED_RE_LOGIN);
                editor.remove(TRADE_NUMBER);
            }
        });
        user = null;
    }

    public boolean isNeedReLogin() {
        return getBoolean(NEED_RE_LOGIN, false);
    }

    public void setNeedReLogin(boolean isNeedLogout) {
        putBoolean(NEED_RE_LOGIN, isNeedLogout);
    }

    public boolean isLogin() {
        return getBoolean(SUCCESS, false);
    }

    public String getToken() {
        return getString(TOKEN, "");
    }

    public String getPassword() {
        return getString(PASSWORD, null);
    }

    public void setPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return;
        }
        putString(PASSWORD, EncryptUtil.md5Hex(password));
    }

    /**
     *
     * REVIEW
     * @Description: 判断账号是否是交易账号
     * @author xi.he@baidao.com
     * @return
     */
    public boolean isTradeAccount() {
        String username = getUser().getUsername();
        return !TextUtils.isEmpty(username) && (isTtAccount(username) || isYgAccount(username));
    }

    private boolean isTtAccount(String username) {
        return username.indexOf("160") == 0 && username.length() == 15;
    }

    private boolean isYgAccount(String username) {
        return username.indexOf("003") == 0 && username.length() == 15;
    }

}

package com.dx168.efsmobile.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidao.chart.model.LineType;
import com.baidao.data.UserPermission;
import com.baidao.tools.UserHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytx.library.provider.ApiFactory;
import com.ytx.library.provider.UserPermissionApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observer;
import rx.functions.Action0;

/**
 * Created by hexi on 15/3/19.
 */
public class UserPermissionHelper extends ConfigHelper{
    private static final String TAG = "UserPermissionHelper";

    private static final String KEY_PERMISSIONS_OF_USER = "key_permissions_of_user";

    //市场.品种.周期
    //ALL.ALL.QKX120
    private static final String ALL_LITERAL = "ALL";

    private static boolean isFuncOfPermissionMatch(UserPermission userPermission, String market, String quoteId, String lineType) {
        String[] funcsOfPermission = userPermission.func.split("\\.");
        String marketOfPermission = funcsOfPermission[0];
        if (!marketOfPermission.equals(market) && !marketOfPermission.equals(ALL_LITERAL)) {
            return false;
        }
        String quoteIdOfPermission = funcsOfPermission[1];
        if (!quoteIdOfPermission.equals(quoteId) && !quoteIdOfPermission.equals(ALL_LITERAL)) {
            return false;
        }
        String lineTypeOfPermission = funcsOfPermission[2];
        if (lineTypeOfPermission.equals(lineType) || lineTypeOfPermission.equals(ALL_LITERAL)) {
            return true;
        }
        return false;
    }

    private static String getLineTypeOfPermissionFunc(String lineType) {
        String tjFunc = null;
        if (lineType.equals(LineType.avg.value)
                || lineType.equals(LineType.avg2d.value)) {
            tjFunc = lineType;
        } else if (lineType.equals(LineType.k5m.value)
                || lineType.equals(LineType.k15m.value)
                || lineType.equals(LineType.k30m.value)
                || lineType.equals(LineType.k60m.value)
                || lineType.equals(LineType.k120m.value)
                || lineType.equals(LineType.k180m.value)
                || lineType.equals(LineType.k240m.value)) {
            tjFunc = lineType.substring(0, lineType.length() - 1);
        } else if (lineType.equals(LineType.k1d.value)) {
            tjFunc = "1DAY";
        } else {
            tjFunc = lineType;
        }
        return tjFunc;
    }

    private static List<UserPermission> getPermissionsOfUser(Context context) {
        String json = getSharedPreferences(context).getString(KEY_PERMISSIONS_OF_USER, null);
        if (json == null) {
            return Collections.EMPTY_LIST;
        }
        List<UserPermission> userPermissions = new Gson().fromJson(json, new TypeToken<List<UserPermission>>() {
        }.getType());
        if (userPermissions == null) {
            return Collections.EMPTY_LIST;
        }
        return userPermissions;
    }


    public static void loadPermissionOfUser(Context context, Action0 observer) {
        UserHelper userHelper = UserHelper.getInstance(context);
        if(!userHelper.isLogin()) {
            observer.call();
            return;
        }
        String username = userHelper.getUser().getUsername();
        loadPermission(context, username, observer);
    }

    private static void loadPermission(Context context, String username, Action0 observer) {
        ApiFactory.getUserPermissionApi().getPermissionsOfUser(username, UserPermissionApi.GROUPS_OF_PERMISSION).subscribe(new Observer<ArrayList<UserPermission>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "fetch tj permission of user error", e);
                observer.call();
            }

            @Override
            public void onNext(ArrayList<UserPermission> userPermissions) {
                if (context != null) {
                    setPermissionOfUser(context, userPermissions);
                    observer.call();
                }
            }
        });
    }

    private static void setPermissionOfUser(Context context, ArrayList<UserPermission> userPermissions) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_PERMISSIONS_OF_USER, new Gson().toJson(userPermissions));
        editor.commit();
    }

    public static void clearPermission(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_PERMISSIONS_OF_USER);
        editor.commit();
    }

    public static int getUserIndexPermission(Context context, String sid, String lineType, String groupId) {
        List<UserPermission> userPermissions = getPermissionsOfUser(context);
        String market = sid.split("\\.")[0];
        String quoteId = sid.split("\\.")[1];
        String lineTypeOfGroup = getLineTypeOfGroup(lineType, groupId);

        for (UserPermission userPermission : userPermissions) {
            if (isFuncOfPermissionMatch(userPermission, market, quoteId, lineTypeOfGroup)) {
                return userPermission.permission;
            }
        }
        return 0;
    }

    private static String getLineTypeOfGroup(String lineType, String groupId) {
        String lineTypeOfFunc = getLineTypeOfPermissionFunc(lineType);
        return groupId + lineTypeOfFunc;
    }
}

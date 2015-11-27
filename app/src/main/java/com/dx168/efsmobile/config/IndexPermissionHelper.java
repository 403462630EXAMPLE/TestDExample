package com.dx168.efsmobile.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.dx168.chart.IndexPermission;
import com.baidao.data.e.Server;
import com.baidao.tools.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by hexi on 15/3/19.
 */
public class IndexPermissionHelper extends ConfigHelper {
    private static final String TAG = "IndexPermissionHelper";

    private static final String KEY_INDEX_PERMISSION_OF_LOCAL = "index_permission_of_local";

    //tt.indexPermission.TPME.PD
    private static final String KEY_INDEX_PERMISSION_PATTERN = "%s.indexPermission.%s";

    public static void loadIndexPermissionOfLocal(Context context) {
        String json = FileUtil.toStringFromAsset(context, "config/index_permission.json");
        if (!TextUtils.isEmpty(json)) {
            setIndexPermissionOfLocal(context, json);
        }
    }

    private static void setIndexPermissionOfLocal(Context context, String json) {
        Map<String, List<IndexPermission>> indexPermissions = new Gson().fromJson(json, new TypeToken<Map<String, ArrayList<IndexPermission>>>(){}.getType());
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_INDEX_PERMISSION_OF_LOCAL, new Gson().toJson(indexPermissions));
        editor.commit();
    }

    public static List<IndexPermission> getIndexPermissionsOfSid(Context context, String sid) {
        String indexPermissionKey = getIndexPermissionKey(context, sid);
        List<IndexPermission> indexPermissions = getIndexPermissionOnline(context, indexPermissionKey);
        if (indexPermissions == null) {
            indexPermissions = getIndexPermissionOfLocal(context, indexPermissionKey);
        }
        return indexPermissions;
    }

    private static String getIndexPermissionKey(Context context, String sid) {
        Server server = Server.from(com.baidao.tools.Util.getCompanyId(context));
        return String.format(KEY_INDEX_PERMISSION_PATTERN, server.name, sid);
    }

    private static List<IndexPermission> getIndexPermissionOnline(Context context, String indexPermissionKey) {
        String json = MobclickAgent.getConfigParams(context, indexPermissionKey);
        return new Gson().fromJson(json, new TypeToken<ArrayList<IndexPermission>>(){}.getType());
    }

    private static List<IndexPermission> getIndexPermissionOfLocal(Context context, String indexPermissionKey) {
        Map<String, List<IndexPermission>> indexPermissions = getIndexPermissionsOfLocal(context);
        List<IndexPermission> indexPermissionsOfSid = indexPermissions.get(indexPermissionKey);
        if (indexPermissionsOfSid == null) {
            return Collections.EMPTY_LIST;
        }
        return indexPermissionsOfSid;
    }

    private static Map<String, List<IndexPermission>> getIndexPermissionsOfLocal(Context context) {
        String json = getSharedPreferences(context).getString(KEY_INDEX_PERMISSION_OF_LOCAL, null);
        if (json == null) {
            return Collections.EMPTY_MAP;
        }
        return new Gson().fromJson(json, new TypeToken<Map<String, ArrayList<IndexPermission>>>(){}.getType());
    }
}

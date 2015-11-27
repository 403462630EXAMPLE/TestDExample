package com.dx168.efsmobile.config;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.baidao.chart.config.CustomIndexConfig;
import com.baidao.data.e.Server;
import com.baidao.tools.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by hexi on 15/1/30.
 */
public class IndexConfigHelper extends ConfigHelper{

    private static final String KEY_TJ_CONFIG_OF_LOCAL = "tj_config_of_local";
    //tt.indexConfig.TPME.XAGUSD
    private static final String KEY_TJ_CONFIG_PATTERN = "%s.indexConfig.%s";

    public static void loadIndexConfigOfLocal(Context context) {
        String json = FileUtil.toStringFromAsset(context, "config/index_config.json");
        if (!TextUtils.isEmpty(json)) {
            setIndexConfigOfLocal(context, json);
        }
    }

    private static void setIndexConfigOfLocal(Context context, String json) {
        Map<String, List<CustomIndexConfig>> indexConfigs = new Gson().fromJson(json, new TypeToken<Map<String, List<CustomIndexConfig>>>(){}.getType());
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_TJ_CONFIG_OF_LOCAL, new Gson().toJson(indexConfigs));
        editor.commit();
    }

    public static List<CustomIndexConfig> getIndexConfig(Context context, String sid) {
        String indexConfigKey = getTjConfigKey(context, sid);
        List<CustomIndexConfig> indexConfigs = getIndexConfigOnline(context, indexConfigKey);
        if (indexConfigs == null) {
            indexConfigs = getIndexConfigOfLocal(context, indexConfigKey);
        }
        return indexConfigs;
    }

    private static String getTjConfigKey(Context context, String sid) {
        Server server = Server.from(com.baidao.tools.Util.getCompanyId(context));
        return String.format(KEY_TJ_CONFIG_PATTERN, server.name, sid);
    }

    private static List<CustomIndexConfig> getIndexConfigOnline(Context context, String indexConfigKey) {
        String json = MobclickAgent.getConfigParams(context, indexConfigKey);
        return new Gson().fromJson(json, new TypeToken<List<CustomIndexConfig>>(){}.getType());
    }

    private static List<CustomIndexConfig> getIndexConfigOfLocal(Context context, String indexConfigKey) {
        Map<String, List<CustomIndexConfig>> indexConfigs = getIndexConfigsOfLocal(context);
        List<CustomIndexConfig> indexConfigsOfCategoryId = indexConfigs.get(indexConfigKey);
        if (indexConfigsOfCategoryId == null) {
            return Collections.EMPTY_LIST;
        }
        return indexConfigsOfCategoryId;
    }

    private static Map<String, List<CustomIndexConfig>> getIndexConfigsOfLocal(Context context) {
        String json = getSharedPreferences(context).getString(KEY_TJ_CONFIG_OF_LOCAL, null);
        if (json == null) {
            return Collections.EMPTY_MAP;
        }
        return new Gson().fromJson(json, new TypeToken<Map<String, List<CustomIndexConfig>>>(){}.getType());
    }
}

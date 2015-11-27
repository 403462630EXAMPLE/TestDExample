package com.baidao.quotation;

import android.content.Context;
import android.text.TextUtils;

import com.baidao.tools.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hexi on 14/12/9.
 */
public class CategoryConfig {
    private static final String TAG = "CategoryConfig";

    private static volatile Map<String, ProductInfo> productsInfo = new ConcurrentHashMap<String, ProductInfo>();

    public static boolean containsKey(Context context, String id) {
        if (productsInfo.isEmpty()) {
            loadConfig(context);
        }
        return productsInfo.containsKey(id);
    }

    public static ProductInfo get(Context context, String id) {
        if (productsInfo.isEmpty()) {
            loadConfig(context);
        }
        return productsInfo.get(id);
    }

    public static void add(Context context, ProductInfo productInfo) {
        if (productsInfo.isEmpty()) {
            loadConfig(context);
        }
        productsInfo.put(productInfo.id, productInfo);
    }

    public static boolean hasLoadConfig() {
        return !productsInfo.isEmpty();
    }

    public synchronized static void loadConfig(Context context) {
        String json = FileUtil.toStringFromAsset(context, "config/category_config.json");
        if (!TextUtils.isEmpty(json)) {
            CategoryConfig.productsInfo = new Gson().fromJson(json, new TypeToken<Map<String, ProductInfo>>(){}.getType());
        }
    }
}

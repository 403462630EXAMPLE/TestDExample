package com.baidao.quotation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.annotation.Nullable;

import com.baidao.data.LoginMessage;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hexi on 15/1/27.
 */
public class CategoryHelper {
    static private Map<String, String> quoteCache = new HashMap<>();

    private static final String FILE_NAME = "quote";
    public static final String KEY_CATEGORY = "categories";
    private static final String KEY_SERVER = "server";
    private static final String KEY_SNAPSHOT_PREFIX = "snapshot.";
    private static final String CATEGORY_RETRIEVE_TIME = "category_time";

    public static void setCategories(Context context, List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return;
        }
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_CATEGORY, new Gson().toJson(categories));
        editor.commit();

        initSnapshot(context, categories);
    }

    private static void initSnapshot(Context context, List<Category> categories) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        Editor editor = sharedPreferences.edit();
        for (Category category : categories) {
            String snapshotKey = getSnapshotKey(category);
            if (!sharedPreferences.contains(snapshotKey)) {
                editor.putString(snapshotKey, Quote.build(category).toString());
            }
        }
        editor.commit();
    }

    private static String getSnapshotKey(Category category) {
        return KEY_SNAPSHOT_PREFIX + category.id;
    }

    public static void updateSnapshot(Quote quote) {
        String snapshotKey = getSnapshotKey(quote);
        quoteCache.put(snapshotKey, quote.toString());
    }

    public static void clearQuoteCache(){
        quoteCache.clear();
    }

    public static Quote getSnapshotById(String categoryId) {
        String snapshotKey = KEY_SNAPSHOT_PREFIX + categoryId;
        return new Gson().fromJson(quoteCache.get(snapshotKey), Quote.class);
    }

    private static String getSnapshotKey(Quote quote) {
        return KEY_SNAPSHOT_PREFIX + quote.getSid();
    }

    private String getSnapshotKey(Snapshot snapshot) {
        return KEY_SNAPSHOT_PREFIX + snapshot.getSid();
    }

    public static List<Category> getCategories(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String categoriesJson = sharedPreferences.getString(KEY_CATEGORY, null);
        if (categoriesJson == null) {
            return Collections.emptyList();
        }
        return new Gson().fromJson(categoriesJson, new TypeToken<List<Category>>(){}.getType());
    }

    public static Category getCategoryById(Context context, String id) {
        List<Category> categories = getCategories(context);
        for (Category category : categories) {
            if (category.id.equals(id)) {
                return category;
            }
        }
        return null;
    }

    public static Category getCategoryByNickname(Context context, String nickname) {
        List<Category> categories = getCategories(context);
        for (Category category : categories) {
            if (category.nickName.equals(nickname)) {
                return category;
            }
        }
        return null;
    }

    public static List<Category> getCategoryByNicknames(Context context, Set<String> nicknames) {
        List<Category> categories = getCategories(context);
        List<Category> result = Lists.newArrayList();
        for (Category category : categories) {
            if (nicknames.contains(category.nickName)) {
                result.add(category);
            }
        }
        return result;
    }

    public static void setServer(Context context, LoginMessage server) {
        if (server == null) {
            return;
        }
        Editor editor = getSharedPreferences(context).edit();
        editor.putString(KEY_SERVER, server.toString());
        editor.commit();
    }

    @Nullable
    public static LoginMessage getServer(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String serverJson = sharedPreferences.getString(KEY_SERVER, null);


        return new Gson().fromJson(serverJson, LoginMessage.class);
    }

    public static void registerCategoryChangedListener(Context context, OnSharedPreferenceChangeListener onCategoryChangedListener) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(onCategoryChangedListener);
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void updateCategory(Context context, CategoryNotice categoryNotice) {
        List<Category> categories = getCategories(context);
        if (categories.isEmpty()) {
            return;
        }
        boolean updated = false;
        for (Category category : categories) {
            if (category.id.equals(categoryNotice.getSid())) {
                category.preSettlementPx = categoryNotice.preSettlementPx;
                category.prevClosedPx = categoryNotice.prevClosedPx;
                updated = true;
                break;
            }
        }
        if (updated) {
            Editor editor = getSharedPreferences(context).edit();
            editor.putString(KEY_CATEGORY, new Gson().toJson(categories));
            editor.commit();
        }
    }

    public static void setCategoryTimestamp(Context context, long timestamp) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(CATEGORY_RETRIEVE_TIME, timestamp);
        editor.commit();
    }

    public static long getCategoryTimestamp(Context context) {
        return getSharedPreferences(context).getLong(CATEGORY_RETRIEVE_TIME, 0);
    }
}

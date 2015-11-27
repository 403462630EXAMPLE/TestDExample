package com.dx168.efsmobile.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hexi on 15/3/19.
 */
public class ConfigHelper {

    private static final String FILE_NAME = "pre_load_config";

    protected static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}

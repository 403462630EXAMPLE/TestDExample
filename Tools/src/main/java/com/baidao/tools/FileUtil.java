package com.baidao.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.io.CharStreams;
import com.google.common.io.Closer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by chengxin on 3/2/15.
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();

    @Nullable
    public static String toStringFromAsset(Context context, String fileName) {
        Closer closer = Closer.create();
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            Reader reader = closer.register(new InputStreamReader(inputStream));
            return CharStreams.toString(reader);
        } catch (Exception e) {
            Log.d(TAG, "load configuration " + fileName + " error ", e);
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                Log.d(TAG, "load configuration " + fileName + " error ", e);
            }
        }
        return null;
    }
}

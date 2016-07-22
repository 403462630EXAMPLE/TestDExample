package cn.hdmoney.hdy.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.hdmoney.hdy.Entity.Region;

/**
 * Created by Administrator on 2016/6/17 0017.
 */
public class AreaDbUtils {
    private static final String DB_NAME = "chinaAera1_3.db";

    private static String getDbPathDir(Context context) {
        return "data/data/" + context.getPackageName() + "/databases";
    }

    private static String getDbPath(Context context) {
        return "data/data/" + context.getPackageName() + "/databases" + "/" + DB_NAME;
    }

    public static SQLiteDatabase openDatabase(Context context) {
        File dbPath = new File(getDbPath(context));
        if (dbPath.exists()) {
            return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
        } else {
            copy(context);
            return openDatabase(context);
        }
    }

    public static void copy(Context context) {
        try {
                File dirPath = new File(getDbPathDir(context));
                dirPath.mkdir();
                AssetManager am = context.getAssets();
                InputStream is = am.open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(getDbPath(context));
                byte[] buffer = new byte[1024];
                int count = 0;
                while((count = is.read(buffer)) > 0){
                    fos.write(buffer, 0, count);
                }
                fos.flush();
                fos.close();
                is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void asynCopy(final Context context) {
        File dbPath = new File(getDbPath(context));
        if (!dbPath.exists()) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    copy(context);
                    Log.i("", "------copy " + DB_NAME + " success");
                    return null;
                }
            };
            asyncTask.execute();
        }
    }

    public static List<Region> getRegions(SQLiteDatabase database, int pid0) {
        Cursor cursor = database.rawQuery("select id, pid, name from tab_area where pid = " + pid0, null);
        List<Region> regions = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            int pid = cursor.getInt(cursor.getColumnIndex("pid"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            Region region = new Region();
            region.id = id;
            region.pid = pid;
            region.name = name;
            regions.add(region);
        }
        cursor.close();
        return regions;
    }
}

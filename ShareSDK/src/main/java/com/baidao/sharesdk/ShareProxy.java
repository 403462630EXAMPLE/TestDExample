package com.baidao.sharesdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by hexi on 14/12/19.
 */
public class ShareProxy {
    private static final String TAG = "ShareProxy";

    public static void share(Context context, String title, String content, String imageUrl, String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字
        String appName = context.getString(R.string.app_name);
        oks.setNotification(R.drawable.ic_launcher, appName);
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (TextUtils.isEmpty(imageUrl)) {
            Log.d(TAG, "---------------------------imagePath: "+getShareIconPath(context));
            oks.setImagePath(getShareIconPath(context));
        } else {
            oks.setImageUrl(imageUrl);
        }
        // url仅在微信（包括好友和朋友圈）中使用
        if (!TextUtils.isEmpty(url)) {
            oks.setUrl(url);
        }

        //QZone
        oks.setTitleUrl(url);
        oks.setSite(appName);
        oks.setSiteUrl(url);
        // 启动分享GUI
        oks.show(context);
    }

    public static String getShareIconPath(Context context){
        String fileName = "ic_launcher.png";
        String path = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().exists()) {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;
            }
            else {
                path = context.getFilesDir().getAbsolutePath() + "/"+ fileName;
            }
            InputStream in = null;
            OutputStream out = null;

            try{
                in = context.getAssets().open(fileName);
                File outFile = new File(path);
                if (!outFile.exists()) {
                    out = new FileOutputStream(outFile);
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                }
            }
            catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            finally{
                in.close();
                in = null;
                if (out != null) {
                    out.close();
                    out = null;
                }
            }

            Log.d(TAG, "Copy file into sd card:" + path);
        } catch(Exception e) {
            Log.e(TAG, "Copy file into sd card fail", e);
        }
        return path;
    }
}

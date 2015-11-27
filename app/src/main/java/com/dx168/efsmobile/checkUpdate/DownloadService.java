package com.dx168.efsmobile.checkUpdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.baidao.efsmobile.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {
    private final static String TAG = DownloadService.class.getName();

    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;
    public final static String APP_URL = "app_url";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    private String appUrl = "http://downloads.jianshu.io/apps/haruki/JianShu-1.3.0.apk";
    private File saveDir = null;
    private File saveFile = null;

    private int downloadCount = 0;
    private int currentSize = 0;
    private long totalSize = 0;
    private int updateTotalSize = 0;
    private int notifyId = 102;
    private Thread downloadThread;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appUrl = intent.getStringExtra(APP_URL);
        init();
        initNotify();
        startDownloadNotify();
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            saveDir = new File(Environment.getExternalStorageDirectory(),
                    "/Download/");
            saveFile = new File(saveDir.getPath(), "银天下.apk");
        }
    }

    private PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, getInstallIntent(), flags);
        return pendingIntent;
    }

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (DOWNLOAD_COMPLETE == msg.what) {
                builder.setContentText("下载完成, 点击安装")
                        .setContentIntent(getDefalutIntent(0))
                        .setSmallIcon(R.drawable.ic_lancher)
                        .setProgress(0, 0, false)
                        .setAutoCancel(true);
                notificationManager.notify(notifyId, builder.build());
                installAPK();
            } else if (DOWNLOAD_FAIL == msg.what) {
                builder.setContentText("下载失败")
                        .setSmallIcon(R.drawable.ic_lancher)
                        .setProgress(0, 0, false);
                notificationManager.notify(notifyId, builder.build());
            }

            stopSelf();
        }
    };

    private void initNotify() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(android.R.drawable.stat_sys_download);
    }

    private void startDownloadNotify() {

        if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
        } else {
            downloadThread = new Thread(new updateRunnable());
            downloadThread.start();
        }
    }

    private void setNotify(int progress) {
        builder.setProgress(100, progress, false);
        notificationManager.notify(notifyId, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private long downloadUpdateFile(String downloadUrl, File saveFile)
            throws Exception {

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection
                    .setRequestProperty("User-Agent", "PacificHttpClient");

            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes="
                        + currentSize + "-");
            }

            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();

            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }

            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;

            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                if ((downloadCount == 0)
                        || (int) (totalSize * 100 / updateTotalSize) - 10 >= downloadCount) {
                    downloadCount += 10;
                    setNotify(downloadCount);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;

            try {
                long downloadSize = downloadUpdateFile(
                        appUrl,
                        saveFile);
                if (downloadSize > 0) {
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                updateHandler.sendMessage(message);
            }
        }
    }

    private Intent getInstallIntent() {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(saveFile),
                "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return installIntent;
    }

    private void installAPK() {
        startActivity(getInstallIntent());
    }

    private boolean isExist() {
        boolean temp = false;

        try {
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            } else {
                temp = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }
}
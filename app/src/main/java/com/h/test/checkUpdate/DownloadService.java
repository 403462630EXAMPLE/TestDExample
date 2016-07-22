package cn.hdmoney.hdy.checkUpdate;

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
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.repository.ApiFactory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadService extends Service {
    private final static String TAG = DownloadService.class.getName();

    private final static int DOWNLOAD_COMPLETE = 0;
    private final static int DOWNLOAD_FAIL = 1;
    public final static String APP_URL = "app_url";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    private String appUrl;
    private File saveDir = null;
    private File saveFile = null;

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
            saveFile = new File(saveDir.getPath(), "hdy.apk");
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
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setProgress(0, 0, false)
                        .setAutoCancel(true);
                notificationManager.notify(notifyId, builder.build());
                installAPK();
            } else if (DOWNLOAD_FAIL == msg.what) {
                builder.setContentText("下载失败")
                        .setSmallIcon(R.mipmap.ic_launcher)
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

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            Call<ResponseBody> call = ApiFactory.getHdyApi().downloadBigFile(appUrl);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    writeResponseBodyToDisk(response.body(), saveFile);
                    message.what = DOWNLOAD_COMPLETE;
                    updateHandler.sendMessage(message);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    message.what = DOWNLOAD_FAIL;
                    updateHandler.sendMessage(message);
                }
            });
        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File saveFile) {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(saveFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    setNotify((int) (fileSizeDownloaded / fileSize * 100));
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
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
//        startActivity(getInstallIntent());
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
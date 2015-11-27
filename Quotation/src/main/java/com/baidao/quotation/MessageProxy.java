package com.baidao.quotation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.baidao.data.LoginMessage;
import com.baidao.data.e.Server;
import com.baidao.tools.ByteUtil;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.ytx.library.provider.ApiFactory;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by burizado on 14-11-18.
 */
public class MessageProxy {
    class LoginUser {
        private final String username;
        private final String password;
        private final int serverId;

        public LoginUser(String username, String password, int serverId) {
            this.username = username != null ? username : "";
            this.password = password != null ? password : "";
            this.serverId = serverId;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public int getServerId() {
            return serverId;
        }

        public int isVisitor() {
            return TextUtils.isEmpty(this.getUsername()) ? 1 : 0;
        }
    }
    private static final String TAG = "MessageProxy";

    private static MessageProxy instance;
    private OnQuoteListener listener;
    private List<Category> subscribedCategoryList;
    private Context context;
    private Object timerLock = new Object();

    public static interface OnQuoteListener {
        void onNewQuote(Quote quote);
        void onQiankun(Qiankun qiankun);
    }

    private Socket keepAliveSocket;

    static {
        System.loadLibrary("QuotationProtocol");
    }

    private native void initMapping();

    private native byte[] getRequestBuffer(String name, String pwd, String token);

    private native byte[] getCodeTable(String appVersion,int clientType);

    private native Category parseCodeTableMessage(byte[] buffer, int length);

    private native Snapshot parseSnapshot(byte[] buffer, int length);

    private native byte[] getSubscription(String tableId);

    private native byte[] getUnsubscription(String tableId);

    private native byte[] getHeartbeatBuffer();

    private native boolean isHeartbeat(byte[] buffer, int length);

    private native boolean isQiankun(byte[] buffer, int length);

    private native Qiankun parseQiankun(byte[] buffer, int length);

    private native  boolean isCategoryNotice(byte[] buffer, int length);

    private native CategoryNotice parseCategoryNotice(byte[] buffer, int length);

    public static MessageProxy getInstance() {
        if (instance == null) {
            instance = new MessageProxy();
        }
        return instance;
    }

    private String version;

    public void init(Context context, String version) {
        initMapping();
        this.version = version;
        this.context = context;
        startWriteThread();
        startReadThread();
        startHeartbeat();
    }

    private static final ImmutableMap<Integer, Integer> CLIENT_TYPE_OF_SERVER_ID = ImmutableMap.<Integer, Integer>builder()
            .put(Server.TT.serverId, 2120000001)
            .put(Server.TD.serverId, 2120000002)
            .put(Server.YG.serverId, 2120000003)
            .put(Server.SSY.serverId, 2120000004)
            .put(Server.BSY.serverId, 2120000005)
            .build();

    @Nullable
    private LoginMessage getQuotationServer(Context context) {
        LoginUser loginUser = new LoginUser(UserHelper.getInstance(context).getUser().getUsername(), UserHelper.getInstance(context).getPassword(), Util.getCompanyId(context));
        LoginMessage ret = null;
        try {
            ret = ApiFactory.getQuotationLoginApi().getLoginServer(loginUser.getUsername(),
                    loginUser.getPassword(),
                    CLIENT_TYPE_OF_SERVER_ID.get(loginUser.getServerId()),
                    version,
                    loginUser.getServerId(),
                    loginUser.isVisitor());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public boolean loginToQuotationServer(String server, int port, String key) {
        byte[] buffer = getRequestBuffer("", "", key);
        boolean result = false;
        try {
            Socket socket = new Socket(server, port);
            socket.setSoTimeout(1000);
            byte[] quotation = ByteUtil.retrieveData(socket, buffer);
            socket.close();
            result = quotation != null && quotation.length > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Category> getMarketList(Context context, String server, int port) {
        int clientType = CLIENT_TYPE_OF_SERVER_ID.get(Util.getCompanyId(context));
        byte[] codeTableBuffer = getCodeTable(version, clientType);
        ArrayList<Category> msgs = new ArrayList<>();
        Socket socket = null;
        try {
            socket = new Socket(server, port);
            socket.setSoTimeout(1000);

            byte[] os = ByteUtil.retrieveData(socket, codeTableBuffer);
            if (os.length == 0) return msgs;
            int size = (os[2] & 0xff) + (os[3] & 0xff) * 256;
            int index = 4;
            while (index < size) {
                int i1 = os[index] & 0xff;
                int i2 = os[index + 1] & 0xff;
                int msgLength = i1 + i2 * 256 + 2;
                byte[] msg = new byte[msgLength];
                if(os.length < index + msgLength){
                    Log.d("shit", "~~~~~~wrong market list data, clean , refetch");
                    msgs.clear();
                    return msgs;
                }
                System.arraycopy(os, index, msg, 0, msgLength);
                Category category = parseCodeTableMessage(msg, msgLength);
                msgs.add(category);
                index += msgLength;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    if (!socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        mergeConfig(context, msgs);
        return msgs;
    }

    private void mergeConfig(Context context, ArrayList<Category> msgs) {
        for (Category category : msgs) {
            if (CategoryConfig.containsKey(context, category.id)) {
                if (category.bondCategory == null) {
                    category.bondCategory = CategoryConfig.get(context, category.id).bondCategory;
                }
            } else {
                CategoryConfig.add(context, new ProductInfo(category.id, category.bondCategory, category.decimalDigits));
            }
        }
    }

    private Timer timer;
    private boolean heartBeatReceived = true;

    private void startHeartbeat() {
        synchronized (timerLock) {
            if(timer != null) return;
            timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!heartBeatReceived){
                        Log.d("tag", ">>>>>timeout, resubscribe");
                        resubscribe();
                        heartBeatReceived = true;
                        return;
                    }
                    if (keepAliveSocket == null) return;
                    synchronized (writeQueue){
                        writeQueue.add(getHeartbeatBuffer());
                        writeQueue.notifyAll();
                        Log.d("tag", ">>>>>add time buffer to queue");
                    }
                    heartBeatReceived = false;

                }
            }, 5000, 5000);
        }
    }

    private void resubscribe(){
        if(keepAliveSocket != null){
            try {
                keepAliveSocket.close();
                Log.d("tag", ">>>>>resubscribe, close keep alive socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (subscribedCategoryList == null || subscribedCategoryList.size() == 0) {
            subscribe(listener);
        } else {
            subscribe(subscribedCategoryList, listener);
        }
    }

    public void stopHeartbeat() {
        synchronized (timerLock) {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }
    }

    public void unsubscribe() {
        if(keepAliveSocket != null
                && !keepAliveSocket.isClosed()
                && subscribedCategoryList != null
                && subscribedCategoryList.size() > 0) {
            synchronized (writeQueue) {
                for (Category msg : subscribedCategoryList) {
                    writeQueue.add(getUnsubscription(msg.id));
                }
                writeQueue.notifyAll();
                Log.d("tag", ">>>>>>>>> unsubscribe, notify writequeue");
            }
        }
    }

    public void subscribe(final OnQuoteListener onQuoteListener) {
        listener = onQuoteListener;
        List<Category> fullCategories = CategoryHelper.getCategories(context);
        if (fullCategories.isEmpty()) {
            LoginMessage server = getServer();
            fullCategories = getCategoryList(server);
        }

        subscribe(fullCategories, onQuoteListener);
    }

    public void subscribe(final List<Category> categoryList, final OnQuoteListener onQuoteListener) {
        subscribedCategoryList = categoryList;
        listener = onQuoteListener;
        writeQueue.clear();
        if(keepAliveSocket == null || keepAliveSocket.isClosed()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    LoginMessage server = CategoryHelper.getServer(context);
                    if (server == null) {
                        server = getServer();
                    }
                    try {
                        keepAliveSocket = new Socket(server.ip, server.port);
                        keepAliveSocket.setSoTimeout(5000);

                        synchronized (writeQueue){
                            for (Category msg : subscribedCategoryList ) {
                                writeQueue.add(getSubscription(msg.id));
                            }
                            writeQueue.notifyAll();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }else{
            synchronized (writeQueue) {
                for (Category msg : subscribedCategoryList) {
                    writeQueue.add(getSubscription(msg.id));
                }
                writeQueue.notifyAll();
                Log.d("tag", ">>>>>> socket alive, write queue, notify all");
            }
        }

    }

    private static final long SIX_HOURS = 3600_000 * 6;
    public void syncCategoriesByTimestamp(){
        if(context == null) return;
        long lastFetchTime = CategoryHelper.getCategoryTimestamp(context);
        long now = DateTime.now().getMillis();
        if(lastFetchTime < now - SIX_HOURS){
            MessageProxy.getInstance().syncCategories();
        }
    }

    public void syncCategories() {
        if(context == null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginMessage server = getServer();
                CategoryHelper.setServer(context, server);

                List<Category> categories = getCategoryList(server);
                CategoryHelper.setCategories(context, categories);
                CategoryHelper.setCategoryTimestamp(context, DateTime.now().getMillis());
            }
        }).start();
    }

    private LoginMessage getServer() {
        LoginMessage server = null;
        while (server == null) {
            server = getQuotationServer(context);

            if (server == null) {
                try {
                    Thread.sleep((long)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return server;
    }

    private List<Category> getCategoryList(LoginMessage server) {
        List<Category> categoryList = null;
        while (categoryList == null || categoryList.size() == 0) {
            categoryList = getMarketList(context, server.ip, server.port);
        }

        return categoryList;
    }

    private void handleSnapshot(Snapshot ss, OnQuoteListener onQuoteListener) {
        Category category = CategoryHelper.getCategoryById(context, ss.getSid());
        if (category == null) {
            return;
        }
        Quote quote = CategoryHelper.getSnapshotById(category.id);
        if (quote == null) {
            quote = Quote.build(category);
        }else{
            quote.update(category);
        }
        quote.update(ss);
        onQuoteListener.onNewQuote(quote);
        CategoryHelper.updateSnapshot(quote);
    }

    private void startWriteThread(){
        new Thread(write).start();
    }

    private void startReadThread(){
        new Thread(read).start();
    }

    public void stop(){
        isRunning = false;
        synchronized (writeQueue){
            writeQueue.notifyAll();
        }
    }

    private Queue<byte[]>writeQueue = new ArrayBlockingQueue<byte[]>(512, true);

    private Runnable write = new Runnable() {
        @Override
        public void run() {
            while(isRunning){
                if(keepAliveSocket == null || keepAliveSocket.isClosed()){
                    try {
                        synchronized (writeQueue) {
                            writeQueue.wait(5_000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                byte[] data;
                synchronized (writeQueue){
                    data = writeQueue.poll();
                }
                if(data != null && data.length > 0){
                    OutputStream out = null;
                    try {
                        out = keepAliveSocket.getOutputStream();
                        out.write(data);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    synchronized (writeQueue){
                        try {
                            writeQueue.wait(5_000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    };

    private boolean isRunning = true;

    private Runnable read = new Runnable() {
        @Override
        public void run() {
            while(isRunning){
                try {
                    if(keepAliveSocket == null || keepAliveSocket.isClosed()){
                        try {
                            synchronized (writeQueue) {
                                writeQueue.wait(5_000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    byte[] data = new byte[1024];
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();

                    int length = 0;
                    int count = 0;

                    while (!keepAliveSocket.isClosed()) {
                        InputStream in = keepAliveSocket.getInputStream();
                        length = in.read(data);
                        if (length == -1) {
                            try {
                                Thread.sleep(1000);
                                continue;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        outStream.write(data, 0, length);
                        if (count == 0) {
                            count = (data[2] & 0xff) + (data[3] & 0xff) * 256 + 4;
                        }
                        if (count > length) {
                            count -= length;
                        } else {
                            while (count <= length) {
                                int size = outStream.size() - 4 - (length - count);
                                byte[] subBuffer = new byte[size];
                                System.arraycopy(outStream.toByteArray(), 4, subBuffer, 0, size);
                                if (isHeartbeat(subBuffer, subBuffer.length)) {
                                    heartBeatReceived = true;
                                } else if (isQiankun(subBuffer, subBuffer.length)) {
                                    Qiankun qiankun = parseQiankun(subBuffer, subBuffer.length);
                                    if (!TextUtils.isEmpty(qiankun.sid)) {
                                        if (qiankun.sid.startsWith("QIANKUN.")) {
                                            qiankun.sid = qiankun.sid.substring("QIANKUN.".length());
                                        }
                                    }
                                    listener.onQiankun(qiankun);
                                } else if (isCategoryNotice(subBuffer, subBuffer.length)) {
                                    CategoryNotice categoryNotice = parseCategoryNotice(subBuffer, subBuffer.length);
                                    handleCategoryNotice(categoryNotice);
                                } else {
                                    Snapshot shot = parseSnapshot(subBuffer, subBuffer.length);
                                    if (shot != null) {
                                        handleSnapshot(shot, listener);
                                    }

                                }

                                ByteArrayOutputStream newStream = new ByteArrayOutputStream();
                                newStream.write(outStream.toByteArray(), size + 4, outStream.size() - size - 4);
                                outStream = newStream;
                                length -= count;
                                if (outStream.size() > 0) {
                                    byte[] dataLeft = outStream.toByteArray();
                                    if (dataLeft.length < 4) {
                                        resubscribe();
                                    } else {
                                        count = (dataLeft[2] & 0xff) + (dataLeft[3] & 0xff) * 256 + 4;
                                    }
                                }
                            }
                            if (length == 0) {
                                count = 0;
                            } else {
                                count -= length;
                            }

                        }
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void handleCategoryNotice(CategoryNotice categoryNotice) {
        if (categoryNotice == null) {
            return;
        }
        Log.d(TAG, "===handleCategoryNotice: " + new Gson().toJson(categoryNotice));
        CategoryHelper.updateCategory(context, categoryNotice);
    }
}

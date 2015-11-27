package com.dx168.efsmobile.webview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidao.data.ImportantEvent;
import com.baidao.data.TopMessage;
import com.baidao.data.User;
import com.baidao.data.e.TopMessageType;
import com.baidao.efsmobile.BuildConfig;
import com.baidao.efsmobile.R;
import com.dx168.efsmobile.application.BaseActivity;
import com.baidao.sharesdk.ShareProxy;
import com.baidao.tools.UserHelper;
import com.baidao.tracker.LogData;
import com.baidao.tracker.Tracker;
import com.baidao.ytxmobile.jsbridge.BridgeHandler;
import com.baidao.ytxmobile.jsbridge.BridgeWebView;
import com.baidao.ytxmobile.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ytx.library.provider.Domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "WebViewActivity";

    public static final String INTENT_DATA = "data";
    public static final String INTENT_DATA_TYPE = "type";
    public static final String KEY_TITLE = "title";
    public static final String KEY_CAN_SHARE = "can_share";
    public static final String KEY_URL = "url";

    private static final String ARTICLE_URL = "file:///android_asset/article/article.html?env=" + (BuildConfig.DEBUG ? "integration" : "production");
    private static final String VIDEO_URL = "file:///android_asset/video/video.html?env=" + (BuildConfig.DEBUG ? "integration" : "production");

    private static final String SHARE_ARTICLE_PAGE = "http://az.mobile-static-service.baidao.com/article/article.html";
    private static final String SHARE_VIDEO_PAGE = "http://az.mobile-static-service.baidao.com/video/video.html";
    private static final int TITLE_LENGTH = 10;

    public enum DataType {
        TOP_MESSAGE,
        IMPORTANT_EVENT,
        LOAD_FROM_URL,

        //下面的几种都是从web页面传来,类型都是JsonObject,一次intent里面用json存储
        ACTIVITY,//活动
        ARTICLE,//文章
        LOTTO,//大乐透
        OPEN_ACCOUNT,//开户
        CHAT
    }

    @InjectView(R.id.web_view)
    BridgeWebView webView;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    String url = null;
    String shareUrl = null;
    String content = null;
    String imageUrl = null;
    String title = null;
    String shareTitle = null;
    Object data = null;
    DataType dataType = null;
    boolean canShare = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();
        if (intent != null) {
            handleIntent(intent);
        }

        initTitle();
        bindHandlers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //加载远程地址才需要reload,开户需要特殊处理，开户页面resume的时候不需要重新reload
        if (TextUtils.isEmpty(webView.getUrl()) ||
                (url.contains("http") && notOpenAccount())
                ) {
            loadPage();
            injectData(data);
            //蛋疼的4.2.2weview不会重新render页面，需要手动resume一下
            webView.onResume();
        }
    }

    private boolean notOpenAccount() {
        return !TextUtils.isEmpty(Domain.get(Domain.DomainType.OPEN_ACCOUNT_PAGE)) && url.indexOf(Domain.get(Domain.DomainType.OPEN_ACCOUNT_PAGE)) == -1;
    }

    private void handleIntent(Intent intent) {
        dataType = (DataType) intent.getSerializableExtra(INTENT_DATA_TYPE);
        Log.d(TAG, "---------------------------dataType:" + dataType);
        switch (dataType) {
            case TOP_MESSAGE:
                TopMessage topMessage = (TopMessage) intent.getParcelableExtra(INTENT_DATA);
                this.data = topMessage;
                if (topMessage.getType() == TopMessageType.OPEN_ACCOUNT) {
                    this.title = "开户";
//                    rightActionView.setVisibility(View.INVISIBLE);
                    this.url = UrlUtil.newUrlWithTokenAgentEnv(this, Domain.get(Domain.DomainType.OPEN_ACCOUNT_PAGE));
                } else if (topMessage.getType() == TopMessageType.ACTIVITY) {
                    this.url = UrlUtil.newUrlWithTokenAgentEnv(this, topMessage.getDetail().getUrl());
                    this.title = "活动";//
                }
                this.shareTitle = topMessage.detail.title;
                this.content = this.shareTitle;
                this.shareUrl = UrlUtil.newUrlWithUsername(this, url);
                this.imageUrl = topMessage.getDetail().getImg();
                break;
            case IMPORTANT_EVENT:
                ImportantEvent importantEvent = (ImportantEvent) intent.getParcelableExtra(INTENT_DATA);
                this.data = importantEvent;
                setShareData(importantEvent);
                Tracker.getInstance(this).addLog(new LogData.Builder(this).pv("article_view").append("articleId", String.valueOf(importantEvent.id)));
                break;
            case LOAD_FROM_URL:
                this.title = intent.getStringExtra(KEY_TITLE);
                this.shareTitle = this.title;
                this.canShare = intent.getBooleanExtra(KEY_CAN_SHARE, true);
                this.url = UrlUtil.newUrlWithTokenAgentEnv(this, intent.getStringExtra(KEY_URL));
                this.shareUrl = url;
                break;
            default:
                //JSONObject都是从webview里面跳转过来，只有url
                String jsonStr = intent.getStringExtra(INTENT_DATA);
                Log.d(TAG, "----------------------data:" + jsonStr);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.data = jsonObject;
                try {
                    this.shareUrl = jsonObject.getString("url");
                    this.url = UrlUtil.newUrlWithTokenAgentEnv(this, this.shareUrl);
                    this.title = jsonObject.getString("title");
                    this.shareTitle = this.title;
                    this.content = this.shareTitle;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setShareData(ImportantEvent importantEvent) {
        this.title = importantEvent.category;
        this.shareTitle = importantEvent.title;
        this.content = shareTitle;
        this.imageUrl = TextUtils.isEmpty(importantEvent.shareImg) ? importantEvent.img : importantEvent.shareImg;

        switch (importantEvent.messageType) {
            case 今日看盘:
                this.shareUrl = importantEvent.shareUrl;
                this.url = VIDEO_URL;
                this.imageUrl = null;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                break;
            case 文章:
                this.shareUrl = UrlUtil.newUrlWithTokenAgent(this, SHARE_ARTICLE_PAGE)+"&id="+importantEvent.id;
                this.url = ARTICLE_URL;
                break;
            case 活动专题:
                //活动页面的env要加上
                this.url = UrlUtil.newUrlWithTokenAgentEnv(this, importantEvent.url) + "&model="+importantEvent.id;
                this.shareUrl = UrlUtil.newUrlWithUsername(this, importantEvent.url); //活动分享添加username
                this.content = importantEvent.desc;
                break;
        }
    }

    private void loadPage() {
        webView.loadUrl(UrlUtil.addTokenQueryString(this, url));
    }

    private void injectData(Object data) {
        if (data != null) {
            Log.d(TAG, "--------------------ytx:list:refresh: " + new Gson().toJson(data));
            webView.callHandler(new Gson().toJson(data), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            }, "ytx:list:refresh");
        }
    }

    private void bindHandlers() {
        webView.registerHandler("ytx:navigate", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Navigation navigation = Navigation.fromJson(data);
                Navigator.navigate(navigation, WebViewActivity.this);
            }
        });

        webView.registerHandler("ytx:phoneBound", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                UserHelper userHelper = UserHelper.getInstance(getApplicationContext());
                User user = userHelper.getUser();
                user.hasPhone = true;
                userHelper.saveUser(user);
            }
        });

        webView.registerHandler("ytx:analytics", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d(TAG, "===ytx-analytics: " + data);

                Map<String, String> extra = new Gson().fromJson(data, new TypeToken<Map<String, String>>() {
                }.getType());
                extra.put("sourceType", "me");
                String step = extra.get("step");
//                Tracker.getInstance(WebViewActivity.this).addLog(new LogData.Builder(WebViewActivity.this).event(EventIDS.OPEN_ACCOUNT_STEP + step).append(extra));
            }
        });
    }

    @Override
    protected void handleBack() {
        finish();
    }

    protected void initTitle() {
        setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        if (canShare) {
            ViewGroup viewGroup = (ViewGroup)toolbar.findViewById(R.id.toolbar_right_action_container);
            if (viewGroup.getChildCount() == 0) {
                TextView view = (TextView) LayoutInflater.from(this).inflate(R.layout.common_title_right, viewGroup, false);
                view.setText("分享");
                viewGroup.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(shareUrl)) {
                            Tracker.getInstance(v.getContext()).addLog(new LogData.Builder(v.getContext()).event("share")
                                    .append("target", shareUrl));
                        }
                        ShareProxy.share(WebViewActivity.this,
                                shareTitle,
                                content,
                                imageUrl,
                                UrlUtil.filterTokenFromUrl(shareUrl)
                        );
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //必须的，不然视频播放不会停止
        webView.onPause();
    }
}

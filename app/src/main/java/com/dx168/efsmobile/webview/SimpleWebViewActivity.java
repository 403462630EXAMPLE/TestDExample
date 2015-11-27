package com.dx168.efsmobile.webview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baidao.efsmobile.R;
import com.dx168.efsmobile.application.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chengxin on 3/3/15.
 */
public class SimpleWebViewActivity extends BaseActivity {
    private static final String TAG = SimpleWebViewActivity.class.getSimpleName();

    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";

    @InjectView(R.id.web_view)
    WebView webView;

    String url = null;
    String title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);
        ButterKnife.inject(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent intent = getIntent();

        if (intent != null) {
            this.url = intent.getStringExtra(KEY_URL);
            this.title = intent.getStringExtra(KEY_TITLE);
        }

        setTitle(title);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onInitToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(webView.getUrl())) {
            webView.loadUrl(url);
            webView.onResume();
        }
        if(!TextUtils.isEmpty(title) && title.contains("使用条款")){
//            Tracker.getInstance(this).addLog(new LogData.Builder(this).pv(EventIDS.APP_AGREEMENT));
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
        webView.onPause();
    }

}

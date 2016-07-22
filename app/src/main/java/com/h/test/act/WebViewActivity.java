package cn.hdmoney.hdy.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.Logs;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.utils.DialogUtils;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/2.
 */
public class WebViewActivity extends BaseActivity {

    private String url;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.webview)
    WebView webview;
    private WebSettings ws;
    public WebViewActivity() {
    }
    public WebViewActivity(String url) {
        this.url = url;
    }
    private ValueCallback mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private final static int FILECHOOSER_RESULTCODE_KITKAT = 2;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url = getIntent().getExtras().getString("url");
        ws = webview.getSettings();

        ws.setDomStorageEnabled(true);
        ws.setJavaScriptEnabled(true); // 打开JS
        webview.requestFocus();
        webview.loadUrl(this.url);

        webview.setWebChromeClient(new MyChrome()); // 显示JS对话框

        webview.setWebViewClient(new MyClient());
        webview.setWebViewClient(new WebViewClient() {});
        titleBar.setLeftTextAndAction(R.string.str_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_webview_layout;
    }

    @Override
    public void onAttachedUi() {

    }

    public void init() {
        if(Build.VERSION.SDK_INT >= 19) {
            webview.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webview.getSettings().setLoadsImagesAutomatically(false);
        }
    }
    public class MyClient extends WebViewClient {

        Dialog progressDialog = null;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

                DialogUtils.showLoading(WebViewActivity.this);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            view.setFocusable(true);
            if(!webview.getSettings().getLoadsImagesAutomatically()) {
                webview.getSettings().setLoadsImagesAutomatically(true);
            }
            DialogUtils.hideLoading();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            Logs.i("shouldOverrideUrlLoading = " + url);

//			if (url.startsWith("http")) {
//				return super.shouldOverrideUrlLoading(view, url);
//			} else {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            if (it.resolveActivity(getPackageManager()) != null) {
                startActivity(it);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }

//			}
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            // super.onReceivedError(view, errorCode, description, failingUrl);
            // Log.e(tag, "onReceivedError");
//			 webview.loadUrl("");
            // CommonUtil.makeToast(WebViewActivity.this, R.string.no_network);
        }

    }

    public class MyChrome extends WebChromeClient {
        private View myView = null;
        private CustomViewCallback myCallback = null;

        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "选择文件"),
                    FILECHOOSER_RESULTCODE);

        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "选择文件"),
                    FILECHOOSER_RESULTCODE);
        }

        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "选择文件"),
                    FILECHOOSER_RESULTCODE);
        }

        public boolean onShowFileChooser(WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("*/*");
            startActivityForResult(Intent.createChooser(i, "File Browser"),
                    FILECHOOSER_RESULTCODE);
            return true;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (myCallback != null) {
                myCallback.onCustomViewHidden();
                myCallback = null;
                return;
            }

            ViewGroup parent = (ViewGroup) webview.getParent();
            String s = parent.getClass().getName();
            parent.removeView(webview);
            parent.addView(view);
            myView = view;
            myCallback = callback;

        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            long id = Thread.currentThread().getId();

            if (myView != null) {
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                }

                ViewGroup parent = (ViewGroup) myView.getParent();
                parent.removeView(myView);
                parent.addView(webview);
                myView = null;
            }
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (webview.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {
            webview.goBack(); // goBack()表示返回webView的上一页面

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview.loadUrl("about:blank");
        webview.onPause();
        webview = null;
    }
}

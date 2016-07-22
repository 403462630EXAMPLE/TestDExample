package cn.hdmoney.hdy.fragment.financialDetail;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;

/**
 * Created by Administrator on 2016/6/23 0023.
 */
public class ProductDescriptionFragment extends BaseFragment {
    private static final String ARG_URL = "arg_url";
    @BindView(R.id.webview)
    WebView webView;

    public static ProductDescriptionFragment build(String url) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_product_description;
    }

    @Override
    protected void initViews() {
        super.initViews();
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        if (getArguments() != null) {
            webView.loadUrl(getArguments().getString(ARG_URL));
        } else {
            webView.loadUrl("http://www.baidu.com");
        }
    }
}

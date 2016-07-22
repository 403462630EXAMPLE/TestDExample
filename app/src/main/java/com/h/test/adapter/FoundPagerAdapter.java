package cn.hdmoney.hdy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.hdmoney.hdy.fragment.WebViewFragment;


/**
 * 主页面适配器。
 */
public class FoundPagerAdapter extends FragmentPagerAdapter {

    public static final int NUM = 2;


    private WebViewFragment webViewFragment;
    private WebViewFragment webViewFragment2;


    public FoundPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        switch (arg0) {
            case 0:
                if (webViewFragment == null) {
//                    webViewFragment =WebViewFragment.newInstance("https://www.baidu.com/");
                }
                return webViewFragment;
            case 1:
                if (webViewFragment2 == null) {
//                    webViewFragment2 =  WebViewFragment.newInstance("http://www.sina.com.cn/");
                }
                return webViewFragment2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM;
    }

}

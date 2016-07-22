package cn.hdmoney.hdy.act;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.hdmoney.hdy.Entity.MainMenuItem;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.MainMenuAdapter;
import cn.hdmoney.hdy.adapter.MainPagerAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.fragment.NewsFragment;
import cn.hdmoney.hdy.mvp.presenter.MainPresenter;
import cn.hdmoney.hdy.mvp.ui.MainUi;
import cn.hdmoney.hdy.mvp.ui.MainUiCallback;
import cn.hdmoney.hdy.mvp.ui.SlowViewPager;
import cn.hdmoney.hdy.utils.IntentUtils;


/**
 * Created by wzt on 2016/5/18.
 */
public class MainActivity extends BaseActivity implements MainUi {

    @BindView(R.id.viewpager_main)
    SlowViewPager viewpagerMain;
    @BindView(R.id.gv_menu1)
    GridView gvMenu1;
    @BindView(R.id.framlayout)
    FrameLayout framlayout;


    private String tag = "MainActivity";
    private MainMenuAdapter menuAdapter;
    private List<MainMenuItem> menuData = new ArrayList<>();
    private View v;
    private MainPagerAdapter adapter;
    private FragmentManager fragmentManager;

    @Override
    protected boolean isCustomStautsBar() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.act_main;
    }

    @Override
    public Presenter setPresenter() {
        return new MainPresenter(this, this);
    }

    @Override
    public void setUiCallback(MainUiCallback callback) {

    }

    @Override
    protected void initView() {
        menuAdapter = new MainMenuAdapter(getApplicationContext(), menuData);
        gvMenu1.setAdapter(menuAdapter);
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        viewpagerMain.setAdapter(adapter);
        viewpagerMain.setOffscreenPageLimit(0);
        viewpagerMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectedMenu(position);
                if (position == 0) {
                } else {
                    if (menuData != null && menuData.size() > position) {
                        setTitle(menuData.get(position).title);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();

    }

    @Override
    public void showMenu(List<MainMenuItem> list) {
        menuData.addAll(list);
        menuAdapter.notifyDataSetChanged();
    }


    @Override
    public void onAttachedUi() {

    }

    @OnItemClick(R.id.gv_menu1)
    public void onMenuItemClick(int position) {
        NewsFragment webViewFragment = (NewsFragment) fragmentManager.findFragmentByTag("HH");
        if (webViewFragment != null) {
            fragmentManager.beginTransaction().remove(webViewFragment).commit();
        }

//        if (position == 3 && "".equals(PreferencesUtils.getString(context, "user", "username"))) {
            if (position == 3) {
                IntentUtils.setIntent(context, LoginActivity.class);
            }
//        }
        selectedMenu(position);
        viewpagerMain.setCurrentItem(position, false);
    }

    private int lastPosition = 0;

    private void selectedMenu(int position) {
        if (menuData.size() > position) {
            menuData.get(lastPosition).selected = false;
            menuData.get(position).selected = true;
            lastPosition = position;
            menuAdapter.notifyDataSetChanged();

        } else {
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出蝴蝶银理财app", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

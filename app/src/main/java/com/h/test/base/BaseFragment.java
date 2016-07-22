package cn.hdmoney.hdy.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuguangqiang.android.mvp.BaseUi;
import com.liuguangqiang.android.mvp.Presenter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.utils.StatusBarTintManager;


/**
 * BaseFragment
 * <p/>
 * Created by Eric on 2014-4-26
 */
public class BaseFragment extends Fragment {

    private StatusBarTintManager tintManager;
    private Presenter presenter;
    private BaseUi baseUi;

    private boolean isRegsiterEvent = false;
    private Unbinder unBind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        unBind = ButterKnife.bind(this, view);

        if (initStatusBar(view) && tintManager.getRootView().getParent() == null) {
            return tintManager.getRootView();
        } else {
            return view;
        }
    }

    protected final void setStatusBarColor(int color) {
        if (tintManager != null && tintManager.isStatusBarAvailable()) {
            tintManager.setStatusBarTintColor(color);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(color);
            }
        }
    }

    protected boolean isCustomStautsBar() {
        return false;
    }

    private boolean initStatusBar(View view) {
        if (isCustomStautsBar()) {
            tintManager = onInitStatusBar(view);
            return tintManager != null && tintManager.isStatusBarAvailable();
        }
        return false;
    }

    protected StatusBarTintManager onInitStatusBar(View view) {
        StatusBarTintManager tintManager = new StatusBarTintManager(view);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.me_purple));
        return tintManager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    protected void initViews() {

    }

    protected int getContentView() {
        return R.layout.fragment_base;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = setPresenter();
    }

    public Presenter setPresenter() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null && !presenter.isAttachedUi()) {
            presenter.attach();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if(isRegsiterEvent) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if(isRegsiterEvent) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    /**
     * 此方法需要在onCreate()中声明，以免无法注册事件
     */
    public void requestRegisterEvent() {
        isRegsiterEvent = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBind.unbind();
    }

}

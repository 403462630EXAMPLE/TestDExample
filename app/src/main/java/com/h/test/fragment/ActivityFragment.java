package cn.hdmoney.hdy.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Activity;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.WebViewActivity;
import cn.hdmoney.hdy.adapter.base.BaseAdapterHelper;
import cn.hdmoney.hdy.adapter.base.QuickAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.mvp.presenter.ActivityPresenter;
import cn.hdmoney.hdy.mvp.ui.ActivityUi;
import cn.hdmoney.hdy.mvp.ui.ActivityUiCallback;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class ActivityFragment extends BaseFragment implements ActivityUi{
    @BindView(R.id.pullrefreshlistview)
    PullToRefreshListView pullrefreshlistview;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    private ListView listView;
    private ActivityUiCallback callback;
    private QuickAdapter<Activity> mAdapter;

    @Override
    public Presenter setPresenter() {
        return new ActivityPresenter(getContext(),this);
    }

    @Override
    protected int getContentView() {
        return R.layout.frg_activity_layout;
    }

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();
        listView = pullrefreshlistview.getRefreshableView();
        pullrefreshlistview.setMode(PullToRefreshBase.Mode.BOTH);
    }


    @Override
    public void showList(List<Activity> activityList) {
        listView.setAdapter(mAdapter = new QuickAdapter<Activity>(getContext(), R.layout.fra_activity_item, activityList) {

            @Override
            protected void convert(BaseAdapterHelper helper, Activity item) {
//                helper.setText(R.id.tv_babyplan, item.proname);
////                                helper.setImageUrl(R.id.iv_babyplan, item.proimg);
//                helper.setImageResource(R.id.iv_babyplan, item.proimg);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.setIntent(getActivity(), WebViewActivity.class,"url","http://www.baidu.com");
            }
        });
    }

    @Override
    public void setUiCallback(ActivityUiCallback callback) {
        this.callback = callback;
    }
}

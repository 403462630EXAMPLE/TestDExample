package cn.hdmoney.hdy.fragment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Program;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.base.BaseAdapterHelper;
import cn.hdmoney.hdy.adapter.base.QuickAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.guide.banner.SimpleImageBanner;
import cn.hdmoney.hdy.guide.entity.BannerItem;
import cn.hdmoney.hdy.guide.utils.T;
import cn.hdmoney.hdy.mvp.presenter.InvestPresenter;
import cn.hdmoney.hdy.mvp.ui.InvestUi;
import cn.hdmoney.hdy.mvp.ui.InvestUiCallback;
import cn.hdmoney.hdy.utils.Logs;
import cn.hdmoney.hdy.utils.StatusBarTintManager;
import cn.hdmoney.hdy.view.HdyProgressView;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/5/25.
 */
public class HomeFragment extends BaseFragment implements InvestUi {


    @BindView(R.id.pullrefreshlistview)
    PullToRefreshListView pullrefreshlistview;
    SimpleImageBanner bannerInvest;
    GridView gvHomeProgram;
    private InvestUiCallback callback;
    private List<BannerItem> list;
    private QuickAdapter<Program> mAdapter;
    private QuickAdapter<Bid> mAdapter2;
    private static final int REFRESH_COMPLETE_TAG = 0;
    private ListView listView;
    private View headview;
    private TitleBar titlebar;
    private float imageHeight;

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected StatusBarTintManager onInitStatusBar(View view) {
        StatusBarTintManager tintManager = new StatusBarTintManager(view.findViewById(R.id.title_bar));
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(android.R.color.transparent));
        return tintManager;
    }

    @Override
    protected int getContentView() {
        return R.layout.fra_main;
    }

    @Override
    public Presenter setPresenter() {
        return new InvestPresenter(getActivity(), this);
    }

    @Override
    protected void initViews() {
        super.initViews();
        imageHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 198 - 70, getResources().getDisplayMetrics());
        titlebar = (TitleBar) getView().findViewById(R.id.title_bar);
        headview = LayoutInflater.from(getActivity()).inflate(
                R.layout.fra_invest_headview, null);
        bannerInvest = (SimpleImageBanner) headview.findViewById(R.id.banner_invest);
        gvHomeProgram = (GridView) headview.findViewById(R.id.gv_home_program);
        pullrefreshlistview.setOnRefreshListener(onRefreshListener);
        listView = pullrefreshlistview.getRefreshableView();
        listView.setHeaderDividersEnabled(true);
        listView.addHeaderView(headview, null, false);
        pullrefreshlistview.setMode(PullToRefreshBase.Mode.BOTH);
        pullrefreshlistview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem < 2 && visibleItemCount > 0) {
                    int y = view.getChildAt(0).getTop();
                    Log.i("TAG", "" + y);
                    if (y >= 0) {
                        setStatusBarColor(Color.argb((int) 0, 148, 110, 187));
                        titlebar.setBackgroundColor(Color.argb((int) 0, 148, 110, 187));//AGB由相关工具获得，或者美工提供
                        titlebar.setTitleColor(Color.argb((int) 0, 255, 255, 255));
                    } else if (y < 0 && Math.abs(y) <= imageHeight) {
                        float scale = (float) Math.abs(y) / imageHeight;
                        float alpha = (255 * scale);
                        // 只是layout背景透明(仿知乎滑动效果)
                        Logs.i(alpha);
                        setStatusBarColor(Color.argb((int) alpha, 148, 110, 187));
                        titlebar.setBackgroundColor(Color.argb((int) alpha, 148, 110, 187));
                        titlebar.setTitleColor(Color.argb((int) alpha, 255, 255, 255));
                    } else {
                        setStatusBarColor(Color.argb((int) 255, 148, 110, 187));
                        titlebar.setBackgroundColor(Color.argb((int) 255, 148, 110, 187));
                        titlebar.setTitleColor(Color.argb((int) 255, 255, 255, 255));
                    }
                }
            }
        });
    }

    private int page = 1;
    PullToRefreshBase.OnRefreshListener2 onRefreshListener = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            callback.pulltoloadMore(1);
            refreshHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE_TAG, 500);

        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            refreshHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE_TAG, 500);
            page++;
            callback.pulltoloadMore(page);
        }
    };
    Handler refreshHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE_TAG:
                    pullrefreshlistview.onRefreshComplete();
                    break;
            }

        }
    };

    @Override
    public void showBanner(final List<BannerItem> list) {
        this.list = list;
        bannerInvest.setSource(list).startScroll();
        bannerInvest.setOnItemClickL(new BaseBanner.OnItemClickL() {
            @Override
            public void onItemClick(int position) {
                callback.onBannerItemClick(list.get(position));
            }
        });
    }

    @Override
    public void showPrograme(final List<Program> list) {
        gvHomeProgram.setAdapter(mAdapter = new QuickAdapter<Program>(getContext(), R.layout.fra_home_program, list) {

            @Override
            protected void convert(BaseAdapterHelper helper, Program item) {
                helper.setText(R.id.tv_babyplan, item.proname);
//                                helper.setImageUrl(R.id.iv_babyplan, item.proimg);
                helper.setImageResource(R.id.iv_babyplan, item.proimg);
            }
        });
        gvHomeProgram.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onProgramItemClick(list.get(position));
            }
        });
    }

    @Override
    public void showProduct(final List<Bid> list) {
        listView.setAdapter(mAdapter2 = new QuickAdapter<Bid>(getContext(), R.layout.frg_home_item, list) {

            @Override
            protected void convert(BaseAdapterHelper helper, Bid item) {

                helper.setText(R.id.tv_left_text, item.name);
                helper.setText(R.id.tv_rate, String.valueOf(item.apr));
//                if (!StringUtils.isEmptyOrNull(item.addInterest+"") && item.addInterest > 0) {
//                    helper.setVisible(R.id.tv_plus, true);
//                    helper.setVisible(R.id.tv_addrate, true);
//                    helper.setText(R.id.tv_addrate, item.addInterest+"");
//                }
//                helper.setText(R.id.tv_day, item.period);
//                helper.setText(R.id.tv_number, item.residueShare+"");
//                helper.setImageUrl(R.id.iv_babyplan, item.proimg);
                ((HdyProgressView)helper.getView().findViewById(R.id.progressBar)).setProgress(50);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onProduckItemClick(list.get(position - 2));

            }
        });
    }


    @Override
    public void setUiCallback(InvestUiCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != bannerInvest) {
            bannerInvest.pauseScroll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (null != bannerInvest && null != list && list.size() > 0) {
            bannerInvest.startScroll();
        }
    }


}

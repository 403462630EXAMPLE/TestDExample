package cn.hdmoney.hdy.fragment.myRecommend;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.hdy.loadmorerecycleview.LoadMoreRecycleView;
import com.hdy.loadmorerecycleview.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Award;
import cn.hdmoney.hdy.Entity.Recommend;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.MyRecommendRecycleAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import ru.vang.progressswitcher.ProgressWidget;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MyRecommendListFragment extends BaseFragment implements OnLoadMoreListener{
    private static final String ARG_TYPE = "arg_type";
    @BindView(R.id.recycle_view)
    LoadMoreRecycleView recycleView;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;

    MyRecommendRecycleAdapter adapter;

    private boolean isFirstVisible = true;
    private int type = Constants.MY_RECOMMEND_TYPE_RECOMMEND;

    public static MyRecommendListFragment build(int type) {
        MyRecommendListFragment fragment = new MyRecommendListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_my_recommend_list;
    }

    @Override
    protected void initViews() {
        super.initViews();
        adapter = new MyRecommendRecycleAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setOnLoadMoreListener(this);

        if (getArguments() != null && getArguments().containsKey(ARG_TYPE)) {
            type = getArguments().getInt(ARG_TYPE);
        }
        getView().post(new Runnable() {
            @Override
            public void run() {
                progressWidget.showProgress(false);
                if (getUserVisibleHint() && isFirstVisible) {
                    loadData(1);
                    isFirstVisible = false;
                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && progressWidget != null && isFirstVisible) {
            loadData(1);
            isFirstVisible = false;
        }
    }

    public void showDatas(List recommends) {
        progressWidget.showContent();
        adapter.setDatas(recommends);
        recycleView.notifyNormal();
    }

    public void showMoreDatas(List recommends) {
        adapter.addDatas(recommends);
        recycleView.notifyNormal();
    }

    private void loadData(final int page) {
        ApiFactory.getHdyApi().getMyRecommendList(1000056, page, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.RecommendResult>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        if (page > 1) {
                            showMoreDatas(getTempDatas(type));
                        } else {
                            showDatas(getTempDatas(type));
                        }
                    }

                    @Override
                    public void onNext(Result<Result.RecommendResult> recommendResultResult) {
                        if (page > 1) {
                            showMoreDatas(getTempDatas(type));
                        } else {
                            showDatas(getTempDatas(type));
                        }
                    }
                });
    }

    private List getTempDatas(int type) {
        List list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            if (type == Constants.MY_RECOMMEND_TYPE_AWARD) {
                Award award = new Award();
                award.date = "2015.12.03";
                award.award = "+0.5%";
                award.typeName = "加息卷";
                list.add(award);
            }
            if (type == Constants.MY_RECOMMEND_TYPE_RECOMMEND) {
                Recommend recommend = new Recommend();
                recommend.date = "2015.12.03";
                recommend.name = "未认证";
                recommend.mobile = "186****3344";
                list.add(recommend);
            }
        }
        return list;
    }

    @Override
    public void onLoadMore() {
        loadData(adapter.getNextPage());
    }
}

package cn.hdmoney.hdy.act;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hdy.loadmorerecycleview.LoadMoreRecycleView;
import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.AwardRecord;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.RewardRecordRecycleAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.view.TitleBar;
import ru.vang.progressswitcher.ProgressWidget;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class RewardRecordActivity extends BaseActivity implements OnLoadMoreListener {

    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycle_view)
    LoadMoreRecycleView recycleView;

    RewardRecordRecycleAdapter adapter;

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
        adapter = new RewardRecordRecycleAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(adapter);
        recycleView.setOnLoadMoreListener(this);
    }

    @Override
    public void initData() {
        loadData(1);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_reward_record;
    }

    @Override
    public void onAttachedUi() {
    }

    private void loadData(final int page) {
        ApiFactory.getHdyApi().getAwardList(1000056, page, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Result.ListResult<AwardRecord>>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        Toast.makeText(RewardRecordActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        progressWidget.showContent();
                        recycleView.notifyNormal();
                        if (page == 1) {
                            adapter.setDatas(getTempDatas());
                        } else {
                            adapter.addDatas(getTempDatas());
                        }
                    }

                    @Override
                    public void onNext(Result<Result.ListResult<AwardRecord>> listResultResult) {
//                        if (listResultResult.isSuccess()) {
//                            adapter.setData(listResultResult.result.awardList);
//                        } else {
//                            Toast.makeText(RewardRecordActivity.this, listResultResult.resultDesc, Toast.LENGTH_SHORT).show();
//                        }
                        recycleView.notifyNormal();
                        progressWidget.showContent();
                        if (page == 1) {
                            adapter.setDatas(getTempDatas());
                        } else {
                            adapter.addDatas(getTempDatas());
                        }
                    }
                });
    }

    private List<AwardRecord> getTempDatas() {
        List<AwardRecord> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AwardRecord awardRecord = new AwardRecord();
            awardRecord.id = 100001;
            awardRecord.name = "20现金";
            awardRecord.money = 20;
            awardRecord.way = "大转盘抽100%中奖";
            awardRecord.date = "2016-06-09";
            list.add(awardRecord);
        }
        return list;
    }

    @Override
    public void onLoadMore() {
        loadData(adapter.getNextPage());
    }
}

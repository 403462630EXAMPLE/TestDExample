package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.Entity.Bank;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.base.BaseAdapterHelper;
import cn.hdmoney.hdy.adapter.base.QuickAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class BankListActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.pullrefreshlistview)
    PullToRefreshListView pullrefreshlistview;
    private ListView listView;
    private QuickAdapter<Bank> mAdapter2;
    private List<Bank> list;

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
//        pullrefreshlistview.setOnRefreshListener(onRefreshListener);
        listView = pullrefreshlistview.getRefreshableView();
        listView.setHeaderDividersEnabled(true);
        pullrefreshlistview.setMode(PullToRefreshBase.Mode.BOTH);

    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Bank bank = new Bank();
            bank.name = "中国银行";
            list.add(bank);
            
        }
        listView.setAdapter(mAdapter2 = new QuickAdapter<Bank>(context, R.layout.act_banklist_item, list) {

            @Override
            protected void convert(BaseAdapterHelper helper, Bank item) {

                helper.setText(R.id.tv_bankname, item.name);
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.act_banklist_layout;
    }

    @Override
    public void onAttachedUi() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

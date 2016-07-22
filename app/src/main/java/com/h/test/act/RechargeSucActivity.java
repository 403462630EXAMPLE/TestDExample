package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class RechargeSucActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.ll_success_hint_ocntainer)
    LinearLayout llSuccessHintOcntainer;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.banknum)
    TextView banknum;
    @BindView(R.id.fee)
    TextView fee;

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

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_rechargesuc_layout;
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

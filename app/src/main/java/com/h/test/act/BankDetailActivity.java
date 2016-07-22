package cn.hdmoney.hdy.act;

import android.view.View;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BankDetailActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.idcard)
    TextView idcard;

    @BindView(R.id.tv_bankname)
    TextView tvBankname;
    @BindView(R.id.cardtype)
    TextView cardtype;
    @BindView(R.id.tv_cardnum)
    TextView tvCardnum;

    @BindView(R.id.chengnuo)
    TextView chengnuo;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titleBar.setLeftAction(new View.OnClickListener(){

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
        return R.layout.act_bankdetail_layout;
    }

    @Override
    public void onAttachedUi() {

    }


}

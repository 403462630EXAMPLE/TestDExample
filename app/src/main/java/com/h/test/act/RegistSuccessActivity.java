package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class RegistSuccessActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;

    @BindView(R.id.tv_name_hint)
    TextView tvNameHint;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titleBar.setLeftTextAndAction(R.string.str_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistSuccessActivity.this,BankDetailActivity.class));
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_regist_success;
    }

    @Override
    public void onAttachedUi() {
    }


}

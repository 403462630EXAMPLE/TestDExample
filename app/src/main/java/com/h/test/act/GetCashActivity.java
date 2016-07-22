package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/5/30.
 */
public class GetCashActivity extends BaseActivity {


    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.idnum)
    TextView idnum;
    @BindView(R.id.cardnum)
    TextView cardnum;
    @BindView(R.id.et_chargenum)
    EditText etChargenum;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

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
//        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
//        scrollView.setFocusable(true);
//        scrollView.setFocusableInTouchMode(true);
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.requestFocusFromTouch();
//                return false;
//            }
//        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_getcash_layout;
    }

    @Override
    public void onAttachedUi() {

    }
    @OnClick(R.id.btn_submit)
    public void onClick() {
        IntentUtils.setIntent(GetCashActivity.this, GetCashSucActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

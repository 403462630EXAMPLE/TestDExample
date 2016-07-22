package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;

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
public class RechargeActivity extends BaseActivity {
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.idnum)
    TextView idnum;
    @BindView(R.id.cardnum)
    TextView cardnum;
    @BindView(R.id.et_chargenum)
    EditText etChargenum;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    private CountDownTimer countDownTimer;

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
        return R.layout.act_recharge_layout;
    }

    @Override
    public void onAttachedUi() {

    }


    @OnClick({R.id.btn_submit, R.id.tv_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String numStr = etChargenum.getText().toString().trim();
                int num = Integer.parseInt(numStr);
                if (num > 0) {
                    IntentUtils.setIntent(RechargeActivity.this, RechargeSucActivity.class);
                } else {
                    ToastUtils.show(this, "金额不能为0");
                }
                break;
            case R.id.tv_get_code:
                start();
                break;
        }
    }
    private void start() {
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(60_000, 1_000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tvGetCode.setText(millisUntilFinished / 1000 + "秒后重新获取");
                }

                @Override
                public void onFinish() {
                    tvGetCode.setEnabled(true);
                    tvGetCode.setText("立即获取");
                }
            };
        }
        tvGetCode.setEnabled(false);
        countDownTimer.start();
    }
}

package cn.hdmoney.hdy.fragment.tradePasswordSetting;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.framework.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.TradePasswordOperationActivity;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.params.TradeSettingParam;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class TradePasswordModifyStepOneFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_confirm)
    TextView confirmView;
    private CountDownTimer countDownTimer;

    Subscription getCodeSubscription;
    private EditTextWatcherHandler textWatcherHandler;
    @Override
    protected int getContentView() {
        return R.layout.fragment_trade_password_modify_step_one;
    }

    @Override
    protected void initViews() {
        super.initViews();
        tvPhone.setText("186****9169");
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etCode);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }

    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etCode.getText()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (getCodeSubscription != null) {
            getCodeSubscription.unsubscribe();
        }
    }

    @OnClick({R.id.tv_get_code, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                start();
                break;
            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            etCode.setError("验证码错误");
            return false;
        }
        return true;
    }

    private void confirm() {
        if (check()) {
            EventBus.getDefault().post(new TradePasswordOperationActivity.TradeSettingStepEvent(TradePasswordOperationActivity.FLAG_OPERATION_MODIFY, 2));
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

        if (getCodeSubscription != null) {
            getCodeSubscription.unsubscribe();
        }
        getCodeSubscription = ApiFactory.getHdyApi().getVerifyCode("1435816755986", 1, "13823242942", "1").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<VerifyCode>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        ToastUtils.show(getActivity(), "短信验证码获取失败");
                        getView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                countDownTimer.cancel();
                                tvGetCode.setEnabled(true);
                                tvGetCode.setText("立即获取");
                            }
                        }, 1000);
                    }

                    @Override
                    public void onNext(Result<VerifyCode> verifyCodeResult) {
                        if (!verifyCodeResult.isSuccess()) {
                            ToastUtils.show(getActivity(), verifyCodeResult.resultDesc);
                        }
                    }
                });
    }
}

package cn.hdmoney.hdy.fragment.tradePasswordSetting;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.framework.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.params.TradeSettingParam;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.utils.RegexUtils;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class TradePasswordResetFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_bank_id)
    EditText etBankId;
    @BindView(R.id.tv_confirm)
    TextView confirmView;
    private CountDownTimer countDownTimer;

    Subscription getCodeSubscription;
    Subscription subscription;
    private ProgressDialog progressDialog;
    private EditTextWatcherHandler textWatcherHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_trade_password_reset;
    }

    @Override
    protected void initViews() {
        super.initViews();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在重置交易密码, 请稍等...");
        progressDialog.setCancelable(false);

        tvPhone.setText("186****9169");
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etCode, etUsername, etIdCard, etBankId);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }
    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etUsername.getText()) && !TextUtils.isEmpty(etIdCard.getText()) && !TextUtils.isEmpty(etBankId.getText()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (getCodeSubscription != null) {
            getCodeSubscription.unsubscribe();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
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

        String username = etUsername.getText().toString();
        if (TextUtils.isEmpty(username)) {
            etUsername.setError("姓名不能为空");
            return false;
        }

        String idCard = etIdCard.getText().toString();
        if (TextUtils.isEmpty(idCard)) {
            etIdCard.setError("身份证不能为空");
            return false;
        }
        if (!RegexUtils.isIdCard(idCard)) {
            etIdCard.setError("身份证格式不正确");
            return false;
        }

        String bankId = etBankId.getText().toString();
        if (TextUtils.isEmpty(bankId)) {
            etBankId.setError("银行卡号不能为空");
            return false;
        }
        if (!RegexUtils.isBankId(bankId)) {
            etBankId.setError("银行卡号格式不正确");
            return false;
        }

        return true;
    }

    private void confirm() {
        if (check()) {
            modifyTradePassword();
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

    private void modifyTradePassword() {
        TradeSettingParam param = new TradeSettingParam();
        param.type = Constants.TRADE_PASSWORD_TYPE_RESET;
        param.token = "f33239446565d76c0e204f09b778caf2";
        param.uid = 1000089;
        param.mobile = "13823242942";
        param.verifyCode = etCode.getText().toString().trim();
        param.name = etUsername.getText().toString();
        param.idNumber = etIdCard.getText().toString();
        param.bankCardNo = etBankId.getText().toString();

        progressDialog.show();
        subscription = ApiFactory.getHdyApi().modifyTradePassword(param).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
//                        ToastUtils.show(getActivity(), "网络异常");
                        progressDialog.dismiss();
                        getActivity().finish();
                    }

                    @Override
                    public void onNext(Result result) {
                        progressDialog.dismiss();
//                        if (result.isSuccess()) {
                        ToastUtils.show(getActivity(), "交易密码重置成功");
                        getActivity().finish();
//                        } else {
//                            ToastUtils.show(getActivity(), result.resultDesc);
//                        }
                    }
                });
    }
}

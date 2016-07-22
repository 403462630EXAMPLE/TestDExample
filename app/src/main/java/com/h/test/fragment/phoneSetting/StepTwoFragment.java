package cn.hdmoney.hdy.fragment.phoneSetting;

import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liuguangqiang.framework.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.PhoneActivity;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.utils.RegexUtils;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class StepTwoFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_new_phone)
    EditText etNewPhone;
    @BindView(R.id.tv_goto_next)
    TextView nextView;
    private CountDownTimer countDownTimer;

    Subscription getCodeSubscription;
    Subscription subscription;
    private ProgressDialog progressDialog;
    private EditTextWatcherHandler textWatcherHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_phone_step_two;
    }

    @Override
    protected void initViews() {
        super.initViews();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在提交, 请稍等...");
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        tvPhone.setText("186****9196");

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etCode, etNewPhone);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setNextViewEnabled();
            }
        });
        setNextViewEnabled();
    }

    private void setNextViewEnabled() {
        nextView.setEnabled(!TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etNewPhone.getText()));
    }

    @OnClick({R.id.tv_get_code, R.id.tv_goto_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                start();
                break;
            case R.id.tv_goto_next:
                gotoNext();
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

    private boolean check() {
        if (TextUtils.isEmpty(etCode.getText().toString())) {
            etCode.setError("验证码不能为空");
            return false;
        }
        if (!RegexUtils.isPhone(etNewPhone.getText().toString())) {
            etNewPhone.setError("请输入正确的手机号码");
            return false;
        }
        return true;
    }

    private void gotoNext() {
        if (check()) {
            changeMobile();
        }
    }

    private void changeMobile() {
        progressDialog.show();
        subscription = ApiFactory.getHdyApi().changeMobile(10000, 1, "13028729112", "18828729113", etCode.getText().toString().trim(), "f33239446565d76c0e204f09b778caf2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        progressDialog.dismiss();
//                        ToastUtils.show(getActivity(), "网络异常");
                        EventBus.getDefault().post(new PhoneActivity.PhoneSettingStepEvent(3));
                    }

                    @Override
                    public void onNext(Result result) {
                        progressDialog.dismiss();
//                        if (result.isSuccess()) {
                        EventBus.getDefault().post(new PhoneActivity.PhoneSettingStepEvent(3));
//                        } else{
//                            ToastUtils.show(getActivity(), result.resultDesc);
//                        }
                    }
                });
    }
}

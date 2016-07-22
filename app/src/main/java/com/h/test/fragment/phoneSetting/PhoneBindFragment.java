package cn.hdmoney.hdy.fragment.phoneSetting;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

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
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/7/4 0004.
 */
public class PhoneBindFragment extends BaseFragment {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_new_phone)
    EditText etNewPhone;
    @BindView(R.id.tv_confirm)
    TextView confirmView;
    private CountDownTimer countDownTimer;

    private EditTextWatcherHandler textWatcherHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_phone_bind;
    }

    @Override
    protected void initViews() {
        super.initViews();
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etCode, etNewPhone);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }

    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etCode.getText()) && !TextUtils.isEmpty(etNewPhone.getText()));
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

        ApiFactory.getHdyApi().getVerifyCode("1435816755986", 1, "13823242942", "1").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<VerifyCode>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {}

                    @Override
                    public void onNext(Result<VerifyCode> verifyCodeResult) {
                        Log.i("", new Gson().toJson(verifyCodeResult));
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private boolean check() {
        if (!RegexUtils.isPhone(etNewPhone.getText().toString())) {
            etNewPhone.setError("请输入正确的手机号码");
            return false;
        }

        if (TextUtils.isEmpty(etCode.getText().toString())) {
            etCode.setError("验证码错误");
            return false;
        }
        return true;
    }

    private void confirm() {
        if (check()) {
            EventBus.getDefault().post(new PhoneActivity.PhoneSettingStepEvent(4));
        }
    }
}

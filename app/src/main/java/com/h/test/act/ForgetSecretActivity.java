package cn.hdmoney.hdy.act;

import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.PreferencesUtils;
import com.liuguangqiang.framework.utils.StringUtils;
import com.liuguangqiang.framework.utils.encrypt.Md5;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.utils.Logs;
import cn.hdmoney.hdy.utils.MyCommontUtils;
import cn.hdmoney.hdy.view.TitleBar;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/5/27.
 */
public class ForgetSecretActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.et_check)
    EditText etCheck;
    @BindView(R.id.getcode)
    Button getcode;

    @BindView(R.id.et_loginsecret)
    EditText etLoginsecret;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.eye)
    ImageView eye;
    private String phone;
    private String loginsecret;
    private String checkcode;
    private boolean isVisible = true;

    public void savaDraft() {

        phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }
        checkcode = etCheck.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(checkcode)) {
            MyCommontUtils.makeToast(context, R.string.nocheck);
        }
        loginsecret = etLoginsecret.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(loginsecret)) {
            MyCommontUtils.makeToast(context, R.string.nosecret);
        }

        PreferencesUtils.putString(context, "forget", "phone", phone);
        PreferencesUtils.putString(context, "forget", "loginsecret", loginsecret);

    }

    public void initDraft() {
        etPhone.setText(PreferencesUtils.getString(context, "forget", "phone"));
        etLoginsecret.setText(PreferencesUtils.getString(context, "forget", "loginsecret"));
    }

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {

        titlebar.setTitle("忘记密码");
        initDraft();


    }


    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_forgetsecret_layout;
    }

    @Override
    public void onAttachedUi() {

    }

    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            getcode.setClickable(false);
            getcode.setText(millisUntilFinished / 1000 + "秒后重新获取");
            getcode.setBackgroundResource(R.drawable.getcode_press);
        }

        @Override
        public void onFinish() {
            getcode.setText("立即获取");
            getcode.setClickable(true);
            getcode.setBackgroundResource(R.drawable.getcode_normal);
        }
    };

    @OnClick({R.id.getcode, R.id.login, R.id.et_phone, R.id.et_check, R.id.et_loginsecret, R.id.eye, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getcode:
                countDownTimer.start();
                getCheckCode();

                break;
            case R.id.login:
                savaDraft();
//                找回密码请求
                modifysecret();
                break;
            case R.id.et_phone:
                delete.setVisibility(View.VISIBLE);
                break;

            case R.id.eye:
                if (isVisible) {
                    eye.setImageResource(R.mipmap.login_invisible);
                    etLoginsecret.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisible = false;
                } else {
                    eye.setImageResource(R.mipmap.login_visible);
                    etLoginsecret.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisible = true;
                }
                break;
            case R.id.delete:
                etPhone.setText("");
                break;

        }
    }

    //修改密码
    private void modifysecret() {
        loginsecret = Md5.encode(loginsecret).toUpperCase();
        ApiFactory.getHdyApi().resetPassword(phone, checkcode, loginsecret).observeOn(AndroidSchedulers.mainThread()).subscribe(new HdyObserver<Result>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onThrowable(Throwable e) {

            }

            @Override
            public void onNext(Result result) {
                if (result.isSuccess()) {
                    MyCommontUtils.makeToast(context, result.resultDesc);
                    finish();
                } else {
                    MyCommontUtils.makeToast(context, result.resultDesc);
                }
            }
        });
    }

    //获取修改密码的验证码
    private void getCheckCode() {

        String imsi = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
        if (!TextUtils.isEmpty(imsi)) {
            Logs.i(imsi);
        }
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }
        ApiFactory.getHdyApi().getVerifyCode(imsi, 1, phone, "2").observeOn(AndroidSchedulers.mainThread()).subscribe(new HdyObserver<Result<VerifyCode>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onThrowable(Throwable e) {

            }

            @Override
            public void onNext(Result<VerifyCode> verifyCodeResult) {
                if (verifyCodeResult.isSuccess()) {
                    Logs.i(verifyCodeResult.resultDesc);
                }
            }
        });

    }


}

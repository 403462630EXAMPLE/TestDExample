package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.PreferencesUtils;
import com.liuguangqiang.framework.utils.StringUtils;
import com.liuguangqiang.framework.utils.encrypt.Md5;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.CheckResponse;
import cn.hdmoney.hdy.Entity.Regist;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.callback.baseCallback.BeanCallback;
import cn.hdmoney.hdy.params.CheckParam;
import cn.hdmoney.hdy.params.RegistParam;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.utils.ApiUtils;
import cn.hdmoney.hdy.utils.JsonUtils;
import cn.hdmoney.hdy.utils.Logs;
import cn.hdmoney.hdy.utils.MyCommontUtils;
import cn.hdmoney.hdy.utils.OkUtils;
import cn.hdmoney.hdy.view.MyURLSpan;
import cn.hdmoney.hdy.view.TitleBar;
import okhttp3.Call;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/5/27.
 */
public class RegistActivity extends BaseActivity {

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

    @BindView(R.id.et_invite)
    EditText etInvite;

    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.eye)
    ImageView eye;
    @BindView(R.id.delete2)
    ImageView delete2;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.loginphone)
    ImageView loginphone;
    @BindView(R.id.check_box)
    CheckBox checkBox;
    private String secret;
    private String phone;
    private String loginsecret;
    private String invitecode;
    private boolean isVisible = true;
    private int curtime = 60;
    private long startTime;

    public void savaDraft() {

        phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }
        String checkcode = etCheck.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(checkcode)) {
            MyCommontUtils.makeToast(context, R.string.nocheck);
        }
        loginsecret = etLoginsecret.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(loginsecret)) {
            MyCommontUtils.makeToast(context, R.string.nosecret);
        }
        invitecode = etInvite.getText().toString().trim();

        if (checkBox.isSelected()) {
            int isAgree = 0;
        } else {
            MyCommontUtils.makeToast(context, R.string.noagreement);
        }

        PreferencesUtils.putString(context, "regist", "phone", phone);
        PreferencesUtils.putString(context, "regist", "loginsecret", loginsecret);
        PreferencesUtils.putString(context, "regist", "etinvite", invitecode);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }

        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;

            public PasswordCharSequence(CharSequence source) {
                mSource = source; // Store char sequence
            }

            public char charAt(int index) {
                return '*'; // This is the important part
            }

            public int length() {
                return mSource.length(); // Return default
            }

            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end); // Return default
            }
        }
    }

    ;

    public void initDraft() {
        etPhone.setText(PreferencesUtils.getString(context, "regist", "phone"));
        etLoginsecret.setText(PreferencesUtils.getString(context, "regist", "loginsecret"));
        etInvite.setText(PreferencesUtils.getString(context, "regist", "etinvite"));
    }

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titlebar.setTitle("注册");
        SpannableString ss = new SpannableString("我已阅读并同意《蝴蝶银平台会员服务协议》");
        //用颜色标记
        ss.setSpan(new MyURLSpan(RegistActivity.this, "http://blog.csdn.net/lan410812571/article/details/9083023"), 8, 19,
                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAgreement.setText(ss);
        //让URLSpan可以点击
        tvAgreement.setMovementMethod(new LinkMovementMethod());

        initDraft();

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_regist_layout;
    }

    @Override
    public void onAttachedUi() {

    }

    @OnClick({R.id.getcode, R.id.tv_agreement, R.id.login, R.id.delete, R.id.logincheck, R.id.eye, R.id.delete2, R.id.et_phone, R.id.et_check, R.id.et_loginsecret, R.id.et_invite})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.getcode:
                countDownTimer.start();
                isRegist();
//                getCode();
                break;
            case R.id.tv_agreement:
//                Bundle b = new Bundle();
//                b.putString("url","https://www.baidu.com/");
//                IntentUtils.setIntent(context, WebViewActivity.class,b);
                break;
            case R.id.login:
                savaDraft();
//               注册请求
                regist();
                break;
            case R.id.delete:
                etPhone.setText(null);
                break;
            case R.id.eye:
                if (isVisible) {
                    eye.setImageResource(R.mipmap.login_invisible);
//                    etLoginsecret.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etLoginsecret.setTransformationMethod(new AsteriskPasswordTransformationMethod());
                    isVisible = false;
                } else {
                    eye.setImageResource(R.mipmap.login_visible);
                    etLoginsecret.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisible = true;
                }

                break;
            case R.id.delete2:
                etInvite.setText("");
                break;

            case R.id.et_loginsecret:

                break;

        }
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

    private void isRegist() {
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }
        OkUtils.post(ApiUtils.URL_ISREGIST, JsonUtils.getGson(new CheckParam(phone)), new BeanCallback<CheckResponse>() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(CheckResponse response) {
                if (StringUtils.isOk(response.getResultCode())) {
                    MyCommontUtils.makeToast(context, response.getResultDesc());
                    getCode();
                } else {
                    MyCommontUtils.makeToast(context, response.getResultDesc());
                }
            }
        });
    }
//    private void isRegist() {
//        ApiFactory.getHdyApi().checkIsRegister(phone).flatMap(new Func1<Result<Result.CheckRegisterResult>, Observable<Result<VerifyCode>>>() {
//            @Override
//            public Observable<Result<VerifyCode>> call(Result<Result.CheckRegisterResult> checkRegisterResultResult) {
//                if (checkRegisterResultResult.isSuccess()) {
//                    String imsi =  ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
//                    return ApiFactory.getHdyApi().getVerifyCode(imsi,1,phone,"1");
//                }
//
//                return Observable.error(new RetrofitException(checkRegisterResultResult.resultCode, checkRegisterResultResult.resultDesc));
//            }
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Result<VerifyCode>>() {
//                    @Override
//                    public void onCompleted() {}
//
//                    @Override
//                    public void onError(Throwable e) {
//                        if (e instanceof RetrofitException) {
//                            ToastUtils.show(getApplicationContext(), ((RetrofitException)e).message);
//                        }
//
//                    }
//
//                    @Override
//                    public void onNext(Result<VerifyCode> verifyCodeResult) {
//                        if (verifyCodeResult.isSuccess()) {
//                            MyCommontUtils.makeToast(context, verifyCodeResult.resultDesc);
//                        } else {
//                            MyCommontUtils.makeToast(context, verifyCodeResult.resultDesc);
//                        }
//                    }
//                });
//        一个链接后发出两个请求，统一处理结果，返回数据对象存入list管理
//        ApiFactory.getHdyApi().checkIsRegister(phone)
//                .flatMap(new Func1<Result<Result.CheckRegisterResult>, Observable<List<Result<Result.RegisterResult>>>>() {
//                    @Override
//                    public Observable<List<Result<Result.RegisterResult>>> call(Result<Result.CheckRegisterResult> checkRegisterResultResult) {
//                        if (checkRegisterResultResult.isSuccess()) {
//                            if (checkRegisterResultResult.result.exist == 0) {
//                                return Observable.zip(ApiFactory.getHdyApi().register("", 1, "", ""), ApiFactory.getHdyApi().register("", 1, "", ""), new Func2<Result<Result.RegisterResult>, Result<Result.RegisterResult>, List<Result<Result.RegisterResult>>>() {
//                                    @Override
//                                    public List<Result<Result.RegisterResult>> call(Result<Result.RegisterResult> registerResultResult, Result<Result.RegisterResult> registerResultResult2) {
//                                        List list = new ArrayList();
//                                        list.add(registerResultResult);
//                                        list.add(registerResultResult2);
//                                        return list;
//                                    }
//                                });
//                            }
//                        }
//                        return Observable.error(new Throwable());
//                    }
//                }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<Result<Result.RegisterResult>>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<Result<Result.RegisterResult>> registerResultResult) {
//
//                    }
//                });
//    }

    private void regist() {
        OkUtils.post(ApiUtils.URL_REGIST, JsonUtils.getGson(new RegistParam(phone, "1", Md5.encode(loginsecret).toUpperCase(), invitecode)), new BeanCallback<Regist>() {


            @Override
            public void onError(Call call, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Regist response) {
                if (StringUtils.isOk(response.getResultCode())) {
                    Logs.i(response.getResultDesc());
                    finish();
                } else {
                    Logs.i(response.getResultDesc());
                }

            }
        });
    }

    private void getCode() {
        String imsi = ((TelephonyManager) context.getSystemService(TELEPHONY_SERVICE)).getSubscriberId();
        Logs.i(imsi);
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }
        ApiFactory.getHdyApi().getVerifyCode(imsi, 1, phone, "1").observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Result<VerifyCode>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Result<VerifyCode> verifyCodeResult) {
                if (verifyCodeResult.isSuccess()) {
                    Logs.i(verifyCodeResult.resultDesc);
                    MyCommontUtils.makeToast(context, verifyCodeResult.resultDesc);
                }
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}

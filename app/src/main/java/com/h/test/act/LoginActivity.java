package cn.hdmoney.hdy.act;

import android.text.Editable;
import android.text.TextWatcher;
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
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.utils.Logs;
import cn.hdmoney.hdy.utils.MyCommontUtils;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/5/27.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.tv_forgetSecret)
    TextView tvForgetSecret;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.delete2)
    ImageView delete2;
    @BindView(R.id.divider2)
    View divider2;
    @BindView(R.id.loginlogo)
    ImageView loginlogo;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.loginphone)
    ImageView loginphone;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.loginsecret)
    ImageView loginsecret;
    @BindView(R.id.et_secret)
    EditText etSecret;
    private String username;
    private String secret;
    private String tag = "Login";

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        initActionBar();
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                delete.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                delete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(etUsername.getText().toString().trim())) {
                    delete.setVisibility(View.INVISIBLE);
                }

            }
        });
        etSecret.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                delete2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                delete2.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(etSecret.getText().toString().trim())) {
                    delete2.setVisibility(View.INVISIBLE);
                }

            }
        });


    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_login_layout;
    }

    private void initActionBar() {
        titlebar.showRight(TitleBar.FLAG_TEXT);
        titlebar.setRightTextAndAction(R.string.regist, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.setIntent(context, RegistActivity.class);
            }
        });
        titlebar.setTitle(R.string.logintitle);
    }


    @Override
    public void onAttachedUi() {

    }

    @OnClick({R.id.checkBox, R.id.login, R.id.tv_forgetSecret, R.id.delete, R.id.delete2, R.id.et_username, R.id.et_secret})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBox:
                username = etUsername.getText().toString().trim();
                secret = etSecret.getText().toString().trim();
                if (StringUtils.isEmptyOrNull(username) || StringUtils.isEmptyOrNull(secret)) {
                    MyCommontUtils.makeToast(context, "用户名和密码不能为空");
                } else {
                    PreferencesUtils.putString(context, "user", "username", username);
                    PreferencesUtils.putString(context, "user", "secret", secret);
                }
                break;
            case R.id.tv_forgetSecret:
                IntentUtils.setIntent(context, ForgetSecretActivity.class);
                break;
            case R.id.login:

                username = etUsername.getText().toString().trim();
                secret = etSecret.getText().toString().trim();
                if (StringUtils.isEmptyOrNull(username) || StringUtils.isEmptyOrNull(secret)) {
                    MyCommontUtils.makeToast(context, "用户名和密码不能为空");
                } else {
                    PreferencesUtils.putString(context, "user", "username", username);
                    PreferencesUtils.putString(context, "user", "secret", secret);
//                   登陆请求
                    finish();
                }
                login();

                break;
            case R.id.delete:
                etUsername.setText("");
                break;
            case R.id.delete2:
                etSecret.setText("");
                break;

        }
    }

    private void login() {
        secret = Md5.encode(secret).toUpperCase();
        ApiFactory.getHdyApi().login(username, 1, secret, "3476762851138309000").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Result<Result.LoginResult>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Result<Result.LoginResult> loginResultResult) {
                        if (StringUtils.isOk(loginResultResult.resultCode)) {

                            Logs.i(tag, loginResultResult.resultDesc);
                            //记住密码后先显示
                            if (checkBox.isChecked()) {
                                PreferencesUtils.putString(context, "username", "uid", loginResultResult.result.token);
                            }

                        } else {
                            Logs.i(tag, loginResultResult.resultDesc);
                        }
                    }
                });
    }


}

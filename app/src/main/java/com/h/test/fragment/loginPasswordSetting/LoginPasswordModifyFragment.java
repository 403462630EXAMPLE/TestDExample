package cn.hdmoney.hdy.fragment.loginPasswordSetting;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.framework.utils.ToastUtils;
import com.liuguangqiang.framework.utils.encrypt.Md5;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
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
 * Created by Administrator on 2016/6/2 0002.
 */
public class LoginPasswordModifyFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.tv_confirm)
    TextView confirmView;

    Subscription subscription;
    private ProgressDialog progressDialog;
    private EditTextWatcherHandler textWatcherHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_login_password_modify;
    }

    @Override
    protected void initViews() {
        super.initViews();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在修改登陆密码, 请稍等...");
        progressDialog.setCancelable(false);

        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etOldPassword, etNewPassword, etPasswordAgain);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }

    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etOldPassword.getText()) && !TextUtils.isEmpty(etNewPassword.getText()) && !TextUtils.isEmpty(etPasswordAgain.getText()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @OnClick(R.id.tv_confirm)
    public void onClick() {
        if (check()) {
            modifyLoginPassword();
        }
    }

    private boolean check() {
        String oldPassword = etOldPassword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            etOldPassword.setError("旧交易密码不能为空");
            return false;
        }
//        if (!RegexUtils.isLoginPassword(oldPassword)) {
//            etOldPassword.setError("非法字符");
//            return false;
//        }

        String newPassword = etNewPassword.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("密码不能为空");
            return false;
        }
        if (!RegexUtils.isLoginPassword(newPassword)) {
            etNewPassword.setError("格式不正确");
            return false;
        }

        String passwordAgain = etPasswordAgain.getText().toString();
        if (!passwordAgain.equals(newPassword)) {
            etPasswordAgain.setError("密码不一致");
            return false;
        }
        return true;
    }

    private void modifyLoginPassword() {
        progressDialog.show();
        subscription = ApiFactory.getHdyApi().modifyPassword(10000, Md5.encode(etOldPassword.getText().toString()).toUpperCase(), Md5.encode(etNewPassword.getText().toString()).toUpperCase(), "f33239446565d76c0e204f09b778caf2")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        progressDialog.dismiss();
//                        ToastUtils.show(getActivity(), "网络异常");
                        getActivity().finish();
                    }

                    @Override
                    public void onNext(Result result) {
                        progressDialog.dismiss();
//                        if (result.isSuccess()) {
                            ToastUtils.show(getActivity(), "修改成功");
                            getActivity().finish();
//                        } else {
//                            ToastUtils.show(getActivity(), result.resultDesc);
//                        }
                    }
                });
    }
}

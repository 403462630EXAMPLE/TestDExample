package cn.hdmoney.hdy.fragment.tradePasswordSetting;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.dialog.ConfirmDialog;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.utils.RegexUtils;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/2 0002.
 */
public class TradePasswordModifyStepTwoFragment extends BaseFragment {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_old_trade_password)
    EditText etOldTradePassword;
    @BindView(R.id.et_new_trade_password)
    EditText etNewTradePassword;
    @BindView(R.id.et_trade_password_again)
    EditText etTradePasswordAgain;
    @BindView(R.id.tv_confirm)
    TextView confirmView;
    ConfirmDialog confirmDialog;
    private EditTextWatcherHandler textWatcherHandler;

    @Override
    protected int getContentView() {
        return R.layout.fragment_trade_password_modify_step_two;
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
        textWatcherHandler.setEditTexts(etOldTradePassword, etNewTradePassword, etTradePasswordAgain);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }

    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etOldTradePassword.getText()) && !TextUtils.isEmpty(etNewTradePassword.getText()) && !TextUtils.isEmpty(etTradePasswordAgain.getText()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (confirmDialog != null) {
            confirmDialog.dismiss();
        }
    }

    @OnClick({ R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    private boolean check() {


        String oldPassword = etOldTradePassword.getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            etOldTradePassword.setError("旧交易密码不能为空");
            return false;
        }
        if (!RegexUtils.isTradePassword(oldPassword)) {
            etOldTradePassword.setError("非法字符");
            return false;
        }

        String newPassword = etNewTradePassword.getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            etNewTradePassword.setError("交易密码不能为空");
            return false;
        }
        if (!RegexUtils.isTradePassword(newPassword)) {
            etNewTradePassword.setError("非法字符");
            return false;
        }

        String passwordAgain = etTradePasswordAgain.getText().toString();
        if (!passwordAgain.equals(newPassword)) {
            etTradePasswordAgain.setError("密码不一致");
            return false;
        }
        return true;
    }

    private void confirm() {
        if (check()) {
            if (confirmDialog == null) {
                confirmDialog = new ConfirmDialog(getActivity(), ConfirmDialog.FLAG_RESET_TRADE_PASSWORD);
                confirmDialog.setDialogActionListener(new ConfirmDialog.DialogActionListener() {
                    @Override
                    public void onAction(int flag) {
                        getActivity().finish();
                    }
                });
            }
            confirmDialog.show();
        }
    }


}

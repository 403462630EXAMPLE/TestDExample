package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.utils.RegexUtils;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class UsernameActivity extends BaseActivity {
    private static final String INTENT_USER_NAME = "intent_user_name";
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_modify_username)
    TextView confirmView;

    private EditTextWatcherHandler textWatcherHandler;

    public static Intent buildIntent(Context context, String username) {
        Intent intent = new Intent(context, UsernameActivity.class);
        intent.putExtra(INTENT_USER_NAME, username);
        return intent;
    }

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

        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(etUsername);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setConfirmViewEnabled();
            }
        });
        setConfirmViewEnabled();
    }

    private void setConfirmViewEnabled() {
        confirmView.setEnabled(!TextUtils.isEmpty(etUsername.getText()));
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            tvUsername.setText(intent.getStringExtra(INTENT_USER_NAME));
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_username;
    }

    @Override
    public void onAttachedUi() {

    }

    @OnClick(R.id.tv_modify_username)
    public void onClick(View view) {
        String username = etUsername.getText().toString();
        if (RegexUtils.isUsername(username)) {
            ToastUtils.show(this, username);
            finish();
        } else {
            etUsername.setError("用户名格式错误");
        }
    }
}

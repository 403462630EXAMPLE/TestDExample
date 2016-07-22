package cn.hdmoney.hdy.dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class ConfirmDialog extends BaseDialog {
    public static final int FLAG_DEFAULT = 0;
    public static final int FLAG_RESET_TRADE_PASSWORD = 1;
    public static final int FLAG_FEEDBACK = 2;

    public DialogActionListener dialogActionListener;

    public void setDialogActionListener(DialogActionListener dialogActionListener) {
        this.dialogActionListener = dialogActionListener;
    }

    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_action)
    TextView tvAction;

    private int flag = FLAG_DEFAULT;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ConfirmDialog(Context context) {
        this(context, FLAG_DEFAULT);
    }

    public ConfirmDialog(Context context, int flag) {
        super(context);
        this.flag = flag;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        render();
    }

    private void render() {
        switch (flag) {
            case FLAG_RESET_TRADE_PASSWORD:
                ivTitle.setImageResource(R.mipmap.ic_bug_success);
                tvTitle.setText("你的交易密码已重置。");
                tvContent.setText("系统将以短信的方式向你的绑定手机号码发送默认交易密码。");
                tvContent.setTextColor(Color.parseColor("#999999"));
                break;
            case FLAG_FEEDBACK:
                ivTitle.setVisibility(View.GONE);
                tvTitle.setText("提交成功");
                tvContent.setText("将会在第一时间处理你的反馈内容");
                tvContent.setTextColor(getContext().getResources().getColor(R.color.me_black));
                break;
        }
    }

    @OnClick(R.id.tv_action)
    public void onClick(View view) {
        if (dialogActionListener != null) {
            dialogActionListener.onAction(flag);
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.dialog_confirm;
    }

    public static interface DialogActionListener{
        public void onAction(int flag);
    }
}

package cn.hdmoney.hdy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class AppDialog extends BaseDialog {

    public static final int FLAG_DEFAULT = 0;
    public static final int FLAG_MAKE_CALL = 1;
    public static final int FLAG_CHECK_UPDATE = 2;

    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left_action)
    TextView tvLeftAction;
    @BindView(R.id.tv_right_action)
    TextView tvRightAction;

    private String tempContent;
    private String tempTitle;
    private String tempLeftActionText;
    private String tempRightActionText;

    private DialogActionListener dialogActionListener;
    private int flag = FLAG_DEFAULT;

    public void setDialogActionListener(DialogActionListener dialogActionListener) {
        this.dialogActionListener = dialogActionListener;
    }

    public AppDialog(Context context) {
        this(context, FLAG_DEFAULT);
    }

    public AppDialog(Context context, int flag) {
        super(context);
        this.flag = flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setContent(String content) {
        if (tvContent == null) {
            tempContent = content;
        } else {
            tvContent.setText(content);
        }
    }

    public void setTitle(String title) {
        if (tvTitle == null) {
            tempTitle = title;
        } else {
            tvTitle.setText(title);
        }
    }

    public void setLeftActionText(String text) {
        if (tvLeftAction == null) {
            tempLeftActionText = text;
        } else {
            tvLeftAction.setText(text);
        }
    }

    public void setRightActionText(String text) {
        if (tvRightAction == null) {
            tempRightActionText = text;
        } else {
            tvRightAction.setText(text);
        }
    }

    public int getLayoutResource() {
        return R.layout.dialog_app;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
//        if (TextUtils.isEmpty(tempTitle)) {
//            llTitleContainer.setVisibility(View.GONE);
//        } else {
//            llTitleContainer.setVisibility(View.VISIBLE);
//        }
        tvTitle.setText(tempTitle);
        tvContent.setText(tempContent);
        tvLeftAction.setText(tempLeftActionText);
        tvRightAction.setText(tempRightActionText);
    }

    @Override
    public void show() {
        super.show();
        resetView();
    }

    private void resetView() {
        switch (flag) {
            case FLAG_MAKE_CALL:
                llTitleContainer.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
                tvTitle.setText("400-962-0400");
                tvLeftAction.setText("立即拨打");
                tvRightAction.setText("取消");
                break;
            case FLAG_CHECK_UPDATE:
                llTitleContainer.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
                tvTitle.setText("发现新版本，是否立即更新？");
                tvLeftAction.setText("暂不更新");
                tvRightAction.setText("立即更新");
                break;
        }
    }

    @OnClick(R.id.tv_left_action)
    public void onLeftClick(View view) {
        if (dialogActionListener != null) {
            dialogActionListener.onLeftAction(flag);
        } else {
            dismiss();
        }
    }

    @OnClick(R.id.tv_right_action)
    public void onRightAction(View view) {
        if (dialogActionListener != null) {
            dialogActionListener.onRightAction(flag);
        } else {
            dismiss();
        }
    }

    public static interface DialogActionListener{
        public void onLeftAction(int flag);
        public void onRightAction(int flag);
    }
}

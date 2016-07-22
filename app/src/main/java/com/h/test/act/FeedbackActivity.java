package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;
import com.sw926.imagefileselector.ImageFileSelector;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.AttachmentRecycleAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.dialog.ConfirmDialog;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class FeedbackActivity extends BaseActivity implements AttachmentRecycleAdapter.OnAttachmentListener, TextWatcher {

    private final int REQUEST_CODE_ADD_FILE = 1;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.recycle_view)
    RecyclerView recycleView;
    @BindView(R.id.tv_content_limit)
    TextView contentLimitView;
    @BindView(R.id.tv_type_1)
    TextView type1;
    @BindView(R.id.tv_type_2)
    TextView type2;
    @BindView(R.id.tv_type_3)
    TextView type3;
    @BindView(R.id.tv_commit)
    TextView commitView;
    ImageFileSelector mImageFileSelector;

    AttachmentRecycleAdapter adapter;
    private ConfirmDialog confirmDialog;

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

        etContent.addTextChangedListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);
        adapter = new AttachmentRecycleAdapter();
        adapter.setOnAttachmentListener(this);
        recycleView.setAdapter(adapter);
        setTypeSelected(1);

        Point point = new Point();
        WindowManager windowManager = getWindowManager();
        windowManager.getDefaultDisplay().getSize(point);
        mImageFileSelector = new ImageFileSelector(this);
        mImageFileSelector.setOutPutImageSize(point.x, point.y);
        mImageFileSelector.setQuality(80);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(final String file) {
                // 选取图片成功
                adapter.add(Uri.parse(file));
            }

            @Override
            public void onError() {
                // 选取图片失败
            }
        });
        commitView.setEnabled(!TextUtils.isEmpty(etContent.getText()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mImageFileSelector.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mImageFileSelector.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mImageFileSelector.onRestoreInstanceState(savedInstanceState);
    }

    // Android 6.0的动态权限
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mImageFileSelector.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setTypeSelected(int position) {
        type1.setSelected(position == 1);
        type2.setSelected(position == 2);
        type3.setSelected(position == 3);
    }

    @OnClick({R.id.tv_type_1, R.id.tv_type_2, R.id.tv_type_3})
    public void onTypeClick(View view) {
        switch (view.getId()) {
            case R.id.tv_type_1:
                setTypeSelected(1);
                break;
            case R.id.tv_type_2:
                setTypeSelected(2);
                break;
            case R.id.tv_type_3:
                setTypeSelected(3);
                break;
        }
    }

    @Override
    public void initData() {
    }

    @Override
    public int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    public void onAttachedUi() {
    }

    @OnClick(R.id.tv_commit)
    public void onClick() {
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(this, "内容不能为空");
            return;
        }
        if (content.length() > 200) {
            ToastUtils.show(this, "内容长度不能超过200");
            return;
        }
        commitSuccess();
    }

    private void commitSuccess() {
        setTypeSelected(1);
        etContent.setText("");
        adapter.clear();
        showConfirmDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (confirmDialog != null) {
            confirmDialog.dismiss();
        }
    }

    private void showConfirmDialog() {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDialog(this, ConfirmDialog.FLAG_FEEDBACK);
            confirmDialog.setDialogActionListener(new ConfirmDialog.DialogActionListener() {
                @Override
                public void onAction(int flag) {
                    finish();
                }
            });
        }
        confirmDialog.show();
    }

    @Override
    public void addFile() {
        mImageFileSelector.selectImage(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.length();
        contentLimitView.setText(length + "/200");
        if (length > 200) {
            contentLimitView.setTextColor(getResources().getColor(R.color.me_red));
            commitView.setEnabled(false);
        } else {
            contentLimitView.setTextColor(getResources().getColor(R.color.me_disable));
            commitView.setEnabled(true);
        }
    }
}

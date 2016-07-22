package cn.hdmoney.hdy.act;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.User;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class DetailAddressActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    private ProgressDialog progressDialog;
    private User user;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在保存，请稍等...");

        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleBar.setRightTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo(etDetailAddress.getText().toString());
            }
        });
    }

    @Override
    public void initData() {
        if (user == null) {
            user = new User();
            user.uid = 10009;
            user.sex = "男";
            user.address = "深圳宝安40区安乐工业大厦B栋一楼";
            user.city = "广东 深圳 南山区";
        }
        etDetailAddress.setText(user.address);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_detail_address;
    }

    @Override
    public void onAttachedUi() {}

    private void updateUserInfo(final String detailAddress) {
        progressDialog.show();
        ApiFactory.getHdyApi().updateUserInfo(user.uid, user.headImgFileId, user.getSex(), user.city, detailAddress, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        Toast.makeText(DetailAddressActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(Result result) {
                        progressDialog.dismiss();
                        if (result.isSuccess()) {
                            Toast.makeText(DetailAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            user.address = detailAddress;
                        } else {
                            Toast.makeText(DetailAddressActivity.this, result.resultDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

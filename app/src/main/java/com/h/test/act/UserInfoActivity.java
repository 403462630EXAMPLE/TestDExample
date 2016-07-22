package cn.hdmoney.hdy.act;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.common.collect.ImmutableList;
import com.hdy.pickerview.LazyOptionsPickerView;
import com.hdy.pickerview.listener.OnDismissListener;
import com.liuguangqiang.android.mvp.Presenter;
import com.sw926.imagefileselector.ImageFileSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Region;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.User;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.repository.RetrofitException;
import cn.hdmoney.hdy.repository.UploadParam;
import cn.hdmoney.hdy.utils.AreaDbUtils;
import cn.hdmoney.hdy.utils.MultipartUtils;
import cn.hdmoney.hdy.view.ListPopWindow;
import cn.hdmoney.hdy.view.TitleBar;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class UserInfoActivity extends BaseActivity implements ListPopWindow.OnListPopWindowListener, LazyOptionsPickerView.OnOptionsSelectListener{
    private static final String TAG = "UserInfoActivity";
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.ll_container)
    LinearLayout container;

    ListPopWindow listPopWindow;
    private int typeFlag = -1;
    ImageFileSelector mImageFileSelector;
    private LazyOptionsPickerView pickerView;

    private ImmutableList<String> avatarList = ImmutableList.of("拍照", "相册选择");
    private ImmutableList<String> sexList = ImmutableList.of("男", "女");
    private SQLiteDatabase sqLiteDatabase;

    private User user;

    private ProgressDialog progressDialog;

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在保存，请稍等...");

        sqLiteDatabase = AreaDbUtils.openDatabase(this);

        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pickerView = new LazyOptionsPickerView(this);
        pickerView.setOnoptionsSelectListener(this);
        pickerView.setLazyOptionsAdapter(adapter);
        pickerView.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.title_bar));
                }
            }
        });

        listPopWindow = new ListPopWindow(this);
        listPopWindow.setAnimationStyle(R.style.listPopWindowStyle);
        listPopWindow.setOnListPopWindowListener(this);

        mImageFileSelector = new ImageFileSelector(this);
        mImageFileSelector.setOutPutImageSize(400, 300);
        mImageFileSelector.setQuality(80);
        mImageFileSelector.setCallback(new ImageFileSelector.Callback() {
            @Override
            public void onSuccess(final String file) {
                // 选取图片成功
//                ivAvatar.setImageURI(Uri.parse(file));
                updateUserInfo(file, user.getSex(), user.city, user.address);
                Log.i(TAG, file);
            }

            @Override
            public void onError() {
                // 选取图片失败
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteDatabase.close();
        progressDialog.dismiss();
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
        tvSex.setText(user.sex);
        tvAddress.setText(user.city);
        tvAddressDetail.setText(user.address);
        if (TextUtils.isEmpty(user.headImgUrl)) {
            ivAvatar.setImageResource(R.mipmap.ic_default_avator);
        } else {
            Glide.with(this).load(user.headImgUrl).into(ivAvatar);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_user_info;
    }

    @Override
    public void onAttachedUi() {

    }

    @OnClick({R.id.rl_avatar, R.id.rl_sex, R.id.rl_address, R.id.rl_address_detail, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_avatar:
                typeFlag = 1;
                listPopWindow.setDate(avatarList);
                listPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_sex:
                typeFlag = 2;
                listPopWindow.setDate(sexList);
                listPopWindow.showAtLocation(container, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.rl_address:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.alpha_title_bar));
                }
                pickerView.show();
                break;
            case R.id.rl_address_detail:
                Intent intent = new Intent(this, DetailAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_logout:
                logout();
                finish();
                break;
        }
    }

    private void updateUserInfo(final String filePath, final int sex, final String address, final String detailAddress) {
        progressDialog.show();
        Observable.just(null).compose(new Observable.Transformer<Object, Object>() {
            @Override
            public Observable<Object> call(Observable<Object> objectObservable) {
                if (!TextUtils.isEmpty(filePath)) {
                    UploadParam param = new UploadParam.UploadParamBuilder().put("type", 1).put("uid", user.uid).put("token", "f33239446565d76c0e204f09b778caf2").build();
                    return ApiFactory.getHdyApi().uploadFile(param, MultipartUtils.getPart("file", filePath)).flatMap(new Func1<Result<Result.UploadResult>, Observable<Object>>() {
                        @Override
                        public Observable<Object> call(Result<Result.UploadResult> uploadResultResult) {
                            if (uploadResultResult.isSuccess()) {
                                long avatarId = uploadResultResult.result.fileId;
                                return Observable.zip(ApiFactory.getHdyApi().updateUserInfo(user.uid, avatarId, sex, address, detailAddress, "f33239446565d76c0e204f09b778caf2"), Observable.just(uploadResultResult.result), new Func2<Result, Result.UploadResult, Object>() {
                                    @Override
                                    public Object call(Result result, Result.UploadResult o) {
                                        List list = new ArrayList();
                                        list.add(result);
                                        list.add(o);
                                        return list;
                                    }
                                });
                            } else {
                                return Observable.error(new RetrofitException(uploadResultResult.resultCode, uploadResultResult.resultDesc));
                            }
                        }
                    });
                } else {
                    return ApiFactory.getHdyApi().updateUserInfo(user.uid, user.headImgFileId, sex, address, detailAddress, "f33239446565d76c0e204f09b778caf2").map(new Func1<Result, Object>() {
                        @Override
                        public Object call(Result result) {
                            return result;
                        }
                    });
                }
            }
        }) .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Object>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        if (e instanceof RetrofitException) {
                            Toast.makeText(UserInfoActivity.this, ((RetrofitException)e).message, Toast.LENGTH_SHORT).show();
                        } else if (e instanceof TimeoutException){
                            Toast.makeText(UserInfoActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserInfoActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onNext(Object object) {
                        progressDialog.dismiss();
                        Result result;
                        Result.UploadResult uploadResult = null;
                        if (object instanceof Result) {
                            result = (Result) object;
                        } else {
                            result = (Result) ((List)object).get(0);
                            uploadResult = (Result.UploadResult) ((List)object).get(1);
                        }
                        if (result.isSuccess()) {
                            Toast.makeText(UserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            user.sex = sex == 1 ? "男" : (sex == 2 ? "女" : "未设置");
                            user.city = address;
                            user.address = detailAddress;
                            if (uploadResult != null) {
                                user.headImgUrl = uploadResult.fileUrl;
                                user.headImgFileId = uploadResult.fileId;
                            }
                            initData();
                        } else {
                            Toast.makeText(UserInfoActivity.this, result.resultDesc, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void logout() {
        ApiFactory.getHdyApi().logout(user.uid, "f33239446565d76c0e204f09b778caf2")
                .subscribe(new HdyObserver<Result>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {}

                    @Override
                    public void onNext(Result result) {}
                });
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

    public void operatorAvator(int position) {
        switch (position) {
            case 0:
                mImageFileSelector.takePhoto(this);
                break;
            case 1:
                mImageFileSelector.selectImage(this);
                break;
        }
        listPopWindow.dismiss();
    }

    public void operatorSex(int position) {
        switch (position) {
            case 0:
                updateUserInfo(null, 1, user.city, user.address);
                break;
            case 1:
                updateUserInfo(null, 2, user.city, user.address);
                break;
        }
        listPopWindow.dismiss();
    }

    @Override
    public void onItemClick(int position) {
        if (typeFlag == 1) {
            operatorAvator(position);
        } else if (typeFlag == 2) {
            operatorSex(position);
        }
    }

    @Override
    public void onDismiss() {}

    @Override
    public void onBackPressed() {
        if (pickerView.isShowing()) {
            pickerView.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        Region a1 = adapter.getOptions1Data(options1);
        Region a2 = adapter.getOptions2Data(option2);
        Region a3 = adapter.getOptions3Data(options3);
//        tvAddress.setText(a1.name + " " + a2.name + " " + (a3.name.equals("--") ? "" : a3.name));
        updateUserInfo(null, user.getSex(), a1.name + " " + a2.name + " " + (a3.name.equals("--") ? "" : a3.name), user.address);
    }

    private LazyOptionsPickerView.LazyOptionsAdapter<Region, Region, Region> adapter = new LazyOptionsPickerView.LazyOptionsAdapter<Region, Region, Region>() {
        @Override
        public ArrayList<Region> createOptions1Datas() {
            return (ArrayList<Region>) AreaDbUtils.getRegions(sqLiteDatabase, 1);
        }

        @Override
        public ArrayList<Region> createOptions2Datas(int position1, Region region) {
            return (ArrayList<Region>) AreaDbUtils.getRegions(sqLiteDatabase, region.id);
        }

        @Override
        public ArrayList<Region> createOptions3Datas(int position1, int position2, Region province, Region region) {
            return (ArrayList<Region>) AreaDbUtils.getRegions(sqLiteDatabase, region.id);
        }

        @Override
        public Region getEmpty3Data() {
            Region district = new Region();
            district.id = -1;
            district.name = "--";
            return district;
        }

        @Override
        public Region getEmpty2Data() {
            Region district = new Region();
            district.id = -1;
            district.name = "--";
            return district;
        }
    };
}

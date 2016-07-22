package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.liuguangqiang.android.mvp.Presenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;
import fc.com.zxing.core.encode.BarcodeEncoder;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class RecommendLinkActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_recommend_code)
    TextView tvRecommendCode;
    @BindView(R.id.tv_link)
    TextView tvLink;

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, RecommendLinkActivity.class);

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
        tvLink.setTextIsSelectable(true);
    }

    @Override
    public void initData() {
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        Map<EncodeHintType,Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 2);
        BarcodeEncoder twoEncoder = new BarcodeEncoder("http://www.hdmoney.cn/registerStep1?un=000191", BarcodeFormat.QR_CODE, size, size, hints);
        try {
            ivQrCode.setImageBitmap(twoEncoder.encodeAsBitmap());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_recommend_link;
    }

    @Override
    public void onAttachedUi() {
    }
}

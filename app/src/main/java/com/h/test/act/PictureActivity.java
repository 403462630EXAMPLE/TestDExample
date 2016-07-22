package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.PicturePageAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class PictureActivity extends BaseActivity {

    public static final String INTENT_PICTURES = "intent_pictures";
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    PicturePageAdapter adapter;

    public static Intent buildIntent(Context context, ArrayList<String> images) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putStringArrayListExtra(INTENT_PICTURES, images);
        return intent;
    }

    public static Intent buildIntent(Context context, String image) {
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add(image);
        return buildIntent(context, arrayList);
    }

    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        titleBar.setLeftTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new PicturePageAdapter(getSupportFragmentManager());
        ArrayList<String> images = getIntent().getStringArrayListExtra(INTENT_PICTURES);
        adapter.setImages(images);
        viewPage.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_picture;
    }

    @Override
    public void onAttachedUi() {

    }
}

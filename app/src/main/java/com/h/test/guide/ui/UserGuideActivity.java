package cn.hdmoney.hdy.guide.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.banner.anim.select.ZoomInEnter;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.MainActivity;
import cn.hdmoney.hdy.guide.banner.SimpleGuideBanner;
import cn.hdmoney.hdy.guide.utils.DataProvider;
import cn.hdmoney.hdy.guide.utils.ViewFindUtils;

public class UserGuideActivity extends Activity {
    private Context context = this;
    private View decorView;
    private boolean isFromBannerHome;
    private Class<? extends ViewPager.PageTransformer> transformerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        isFromBannerHome = getIntent().getBooleanExtra("isFromBannerHome", false);
        int position = getIntent().getIntExtra("position", -1);
        transformerClass = position != -1 ? DataProvider.transformers[position] : null;

        decorView = getWindow().getDecorView();
        sgb();
    }


    private void sgb() {
        SimpleGuideBanner sgb = ViewFindUtils.find(decorView, R.id.sgb);

        sgb
                .setIndicatorWidth(0)
                .setIndicatorHeight(0)
                .setIndicatorGap(12)
                .setIndicatorCornerRadius(3.5f)
                .setSelectAnimClass(ZoomInEnter.class)
                .setTransformerClass(transformerClass)
                .barPadding(0, 10, 0, 10)
                .setSource(DataProvider.geUsertGuides())
                .startScroll();

        sgb.setOnJumpClickL(new SimpleGuideBanner.OnJumpClickL() {
            @Override
            public void onJumpClick() {
                if (isFromBannerHome) {
                    finish();
                    return;
                }

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

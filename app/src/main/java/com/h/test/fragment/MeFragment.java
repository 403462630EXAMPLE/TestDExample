package cn.hdmoney.hdy.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.AccountSettingActivity;
import cn.hdmoney.hdy.act.GetCashActivity;
import cn.hdmoney.hdy.act.InvestmentRecordActivity;
import cn.hdmoney.hdy.act.MyCouponActivity;
import cn.hdmoney.hdy.act.MyRecommendActivity;
import cn.hdmoney.hdy.act.NewsActivity;
import cn.hdmoney.hdy.act.PaymentCalendarActivity;
import cn.hdmoney.hdy.act.PaymentRecordActivity;
import cn.hdmoney.hdy.act.RechargeActivity;
import cn.hdmoney.hdy.act.RewardRecordActivity;
import cn.hdmoney.hdy.act.TotalMoneyActivity;
import cn.hdmoney.hdy.act.UserInfoActivity;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.utils.Logs;
import cn.hdmoney.hdy.utils.StatusBarTintManager;
import cn.hdmoney.hdy.view.NavigationTabView;
import cn.hdmoney.hdy.view.ObservableScrollView;
import cn.hdmoney.hdy.view.TitleBar2;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Administrator on 2016/5/25.
 */
public class MeFragment extends BaseFragment implements ObservableScrollView.ScrollViewListener {
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_leftnum)
    TextView tvLeftnum;
    @BindView(R.id.tv_allprofits)
    TextView tvAllprofits;
    @BindView(R.id.tv_profitsnum)
    TextView tvProfitsnum;
    @BindView(R.id.recharge)
    TextView recharge;
    @BindView(R.id.getcash)
    TextView getcash;
    @BindView(R.id.titlebar)
    TitleBar2 titlebar;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    boolean isvisible = false;
    @BindView(R.id.visible)
    ImageView visible;
    @BindView(R.id.tv_incomerecord)
    NavigationTabView tvIncomerecord;
    @BindView(R.id.tv_investrecord)
    NavigationTabView tvInvestrecord;
    @BindView(R.id.mycoupon)
    NavigationTabView mycoupon;
    @BindView(R.id.myrecommend)
    NavigationTabView myrecommend;
    @BindView(R.id.calendar)
    NavigationTabView calendar;
    @BindView(R.id.winrecord)
    NavigationTabView winrecord;
    @BindView(R.id.ll_head)
    RelativeLayout llHead;
    @BindView(R.id.scroll_view)
    ObservableScrollView scrollView;
    @BindView(R.id.moneyicon)
    TextView moneyicon;
    @BindView(R.id.headview)
    CircleImageView headview;
    @BindView(R.id.ll_head_float)
    LinearLayout llHeadFloat;
    @BindView(R.id.headimg_bg)
    ImageView headimgBg;

    private int imageHeight;

    @Override
    protected int getContentView() {
        return R.layout.fra_me;
    }

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected StatusBarTintManager onInitStatusBar(View view) {
        StatusBarTintManager tintManager = new StatusBarTintManager(view.findViewById(R.id.titlebar));
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(android.R.color.transparent));
        return tintManager;
    }

    @Override
    protected void initViews() {
        super.initViews();

        titlebar.setRightIconAndAction(R.mipmap.no_message, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsActivity.class));
//                NewsFragment newsFragment = new NewsFragment();
//                getFragmentManager().beginTransaction().add(R.id.framlayout,newsFragment,"HH").commit();
            }
        });
        if (true) {
            titlebar.setRightIcon(R.mipmap.have_message);
        }
        headimgBg.setMaxHeight(llHeadFloat.getHeight());
//        headview.setBorderColor(Color.rgb(255, 255, 255));
//        llHead.setBackgroundResource(R.mipmap.default_headimg);

        llHeadFloat.setBackgroundResource(R.color.me_color_bg);
        llHeadFloat.getBackground().setAlpha(200);
        initListeners();

    }

    private void initListeners() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = llHead.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llHead.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = llHead.getHeight() - titlebar.getHeight() * 2;
                Logs.i(imageHeight);
                scrollView.setScrollViewListener(MeFragment.this);

            }
        });

    }

    @OnClick({R.id.headview, R.id.tv_total_money, R.id.recharge, R.id.getcash, R.id.countset, R.id.tv_incomerecord, R.id.tv_investrecord, R.id.mycoupon, R.id.myrecommend, R.id.calendar, R.id.winrecord, R.id.visible})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headview:
                IntentUtils.setIntent(getActivity(), UserInfoActivity.class);
                break;
            case R.id.recharge:
                IntentUtils.setIntent(getActivity(), RechargeActivity.class);
                break;
            case R.id.getcash:
                IntentUtils.setIntent(getActivity(), GetCashActivity.class);
                break;
            case R.id.countset:
                IntentUtils.setIntent(getActivity(), AccountSettingActivity.class);
                break;
            case R.id.tv_incomerecord:
                IntentUtils.setIntent(getActivity(), PaymentRecordActivity.class);
                break;
            case R.id.tv_investrecord:
                IntentUtils.setIntent(getActivity(), InvestmentRecordActivity.class);
                break;
            case R.id.mycoupon:
                IntentUtils.setIntent(getActivity(), MyCouponActivity.class);
                break;
            case R.id.myrecommend:
                IntentUtils.setIntent(getActivity(), MyRecommendActivity.class);
                break;
            case R.id.calendar:
                IntentUtils.setIntent(getActivity(), PaymentCalendarActivity.class);
                break;
            case R.id.winrecord:
                IntentUtils.setIntent(getActivity(), RewardRecordActivity.class);
                break;
            case R.id.tv_total_money:
                IntentUtils.setIntent(getActivity(), TotalMoneyActivity.class);
                break;
            case R.id.visible:
                if (isvisible) {
                    visible.setImageResource(R.mipmap.me_eye);
                    moneyicon.setVisibility(View.VISIBLE);
                    tvTotalMoney.setText("10000");
                    isvisible = false;
                } else {
                    visible.setImageResource(R.mipmap.me_no_eye);
                    moneyicon.setVisibility(View.INVISIBLE);
                    tvTotalMoney.setText("******");
                    tvTotalMoney.setTextSize(18);

                    isvisible = true;
                }

                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {
            setStatusBarColor(Color.argb((int) 0, 148, 110, 187));
            titlebar.setBackgroundColor(Color.argb((int) 0, 148, 110, 187));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            Logs.i(alpha);
            setStatusBarColor(Color.argb((int) alpha, 148, 110, 187));
            titlebar.setBackgroundColor(Color.argb((int) alpha, 148, 110, 187));
        } else {
            setStatusBarColor(Color.argb((int) 255, 148, 110, 187));
            titlebar.setBackgroundColor(Color.argb((int) 255, 148, 110, 187));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}

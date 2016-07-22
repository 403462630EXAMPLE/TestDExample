package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.InvestRecord;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.FinancialDetailPageAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.mvp.presenter.FinancialDetailPresenter;
import cn.hdmoney.hdy.mvp.ui.FinancialDetailUi;
import cn.hdmoney.hdy.mvp.ui.FinancialDetailUiCallBack;
import cn.hdmoney.hdy.utils.SpannableUtils;
import cn.hdmoney.hdy.view.HdyProgressView;
import cn.hdmoney.hdy.view.TitleBar;
import ru.vang.progressswitcher.ProgressWidget;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class FinancialDetailActivity extends BaseActivity implements FinancialDetailUi {
    private final int REQUEST_CODE_BUY = 1;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPager;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.ll_recycle_container)
    LinearLayout llRecycleContainer;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_year_rate)
    TextView tvYearRate;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.progress_bar)
    HdyProgressView progressBar;
    @BindView(R.id.tv_surplus_number)
    TextView tvSurplusNumber;
    @BindView(R.id.iv_sell_out)
    ImageView ivSellOut;
    @BindView(R.id.tv_plus)
    TextView tvPlus;
    @BindView(R.id.tv_addrate)
    TextView tvAddRate;

    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_end_date)
    TextView tvEndDate;
    @BindView(R.id.tv_repayment_day)
    TextView tvRepaymentDay;
    @BindView(R.id.tv_start_date)
    TextView tvStartDate;
    @BindView(R.id.tv_buy)
    TextView buyView;

    FinancialDetailPageAdapter adapter;
    private FinancialDetailUiCallBack callBack;
    private Bid bid;
    private List<InvestRecord> investRecords;

    @Override
    public Presenter setPresenter() {
        return new FinancialDetailPresenter(this, this);
    }

    @Override
    protected void initView() {
        adapter = new FinancialDetailPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout.setupWithViewPager(viewPager);
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = scrollView.getHeight();
                if (height != 0) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    llRecycleContainer.setLayoutParams(layoutParams);
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_BUY) {
                finish();
            }
        }
    }

    @OnClick(R.id.tv_buy)
    public void onBuyClick(View view) {
        callBack.immediateBuy(bid);
    }

    @Override
    public void initData() {
        if (bid == null) {
            return ;
        }
        tvName.setText(bid.name);
        tvYearRate.setText(String.valueOf(bid.apr));
        tvDay.setText(bid.period + " 天");
        tvUnit.setText(bid.baseAmount + " 元/份");
        progressBar.setProgress((int) bid.schedule);
        SpannableString span = new SpannableString("剩余 " + (int)bid.residueShare + " 份");
        SpannableUtils.setTextSize(span, 0, 2, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        SpannableUtils.setTextSize(span, 2, span.length() - 1, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getResources().getDisplayMetrics()));
        SpannableUtils.setTextSize(span, span.length() - 1, span.length(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        SpannableUtils.setTextForeground(span, 0, 2, getResources().getColor(R.color.financial_gray));
        SpannableUtils.setTextForeground(span, 2, span.length() - 1, getResources().getColor(R.color.financial_yellow));
        SpannableUtils.setTextForeground(span, span.length() - 1, span.length(), getResources().getColor(R.color.financial_gray));
        tvSurplusNumber.setText(span);
        if (bid.residueShare < 1) {
            buyView.setEnabled(false);
            buyView.setText("已售罄");
            ivSellOut.setVisibility(View.VISIBLE);
        } else {
            buyView.setEnabled(true);
            buyView.setText("立即购买");
            ivSellOut.setVisibility(View.GONE);
        }
        if (bid.addInterest != 0) {
            tvPlus.setVisibility(View.VISIBLE);
            tvAddRate.setVisibility(View.VISIBLE);
            tvAddRate.setText(String.valueOf(bid.addInterest));
        } else {
            tvPlus.setVisibility(View.GONE);
            tvAddRate.setVisibility(View.GONE);
        }

        tvBank.setText(bid.acceptBank);
        tvEndDate.setText(bid.repayDate);
        tvRepaymentDay.setText("2016/08/09");
        tvStartDate.setText(bid.interestDate);

        adapter.setBid(bid, investRecords, bid.descriptionUrl);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_financial_detail;
    }

    @Override
    public void onAttachedUi() {
        callBack.getBidDetailInfo(1);
    }

    @Override
    public void setUiCallback(FinancialDetailUiCallBack callback) {
        this.callBack = callback;
    }

    @Override
    public void showFinancialDetail(Bid bid, List<InvestRecord> investRecords) {
        this.bid = bid;
        this.investRecords = investRecords;
        progressWidget.showContent();
        initData();
    }

    @Override
    public void showError() {
        progressWidget.showContent();
//        tvBank.setText("--");
//        tvEndDate.setText("--");
//        tvRepaymentDay.setText("--");
//        tvStartDate.setText("--");
    }

    @Override
    public void gotoFinancialBuyUI() {
        Intent intent = FinancialBuyActivity.buildIntent(this, 10009);
        startActivityForResult(intent, REQUEST_CODE_BUY);
    }
}

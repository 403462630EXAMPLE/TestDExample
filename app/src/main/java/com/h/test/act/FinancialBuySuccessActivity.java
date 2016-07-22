package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.Entity.OrderSuccessResult;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.utils.DecimalFormatUtil;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class FinancialBuySuccessActivity extends BaseActivity {
    private static final String INTENT_ORDER_SUCCESS_RESULT = "intent_order_success_result";
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_red_packet)
    TextView redPacket;
    @BindView(R.id.tv_add_rate)
    TextView addRate;
    @BindView(R.id.ll_red_packet_container)
    LinearLayout redPacketContainer;
    @BindView(R.id.ll_add_rate_container)
    LinearLayout addRateContainer;

    private OrderSuccessResult result;

    public static Intent buildIntent(Context context, OrderSuccessResult result) {
        Intent intent = new Intent(context, FinancialBuySuccessActivity.class);
        intent.putExtra(INTENT_ORDER_SUCCESS_RESULT, result);
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
    }

    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }

    @Override
    public void initData() {
        result = getIntent().getParcelableExtra(INTENT_ORDER_SUCCESS_RESULT);
        tvName.setText(result.bidName);
        tvOrderMoney.setText(DecimalFormatUtil.format(result.amount, "#0.00") + "元");
        tvTime.setText(result.date);

        List<Coupon> coupons = result.couponList;
        double redPacetValue = 0;
        double addRateValue = 0;
        if (coupons != null && !coupons.isEmpty()) {
            for (Coupon coupon : coupons) {
                if (coupon.type == Constants.MY_COUPON_TYPE_RED_PACKET) {
                    redPacetValue += coupon.amount;
                } else if (coupon.type == Constants.MY_COUPON_TYPE_ADD_RATE) {
                    addRateValue += coupon.amount;
                }
            }
        }
        if (redPacetValue == 0) {
            redPacketContainer.setVisibility(View.GONE);
        } else {
            redPacket.setText("+" + DecimalFormatUtil.format(redPacetValue, "#0.00") + "元");
            redPacketContainer.setVisibility(View.VISIBLE);
        }
        if (addRateValue == 0) {
            addRateContainer.setVisibility(View.GONE);
        } else {
            addRate.setText("+" + addRateValue + "%");
            addRateContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_financial_buy_success;
    }

    @Override
    public void onAttachedUi() {}

    @OnClick({R.id.tv_goto_my_investment, R.id.tv_continue_investment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_goto_my_investment:
                gotoInvestmentRecordUI();
                break;
            case R.id.tv_continue_investment:
                finish();
                break;
        }
    }

    private void gotoInvestmentRecordUI() {
        Intent intent = new Intent(this, InvestmentRecordActivity.class);
        startActivity(intent);
        finish();
    }
}

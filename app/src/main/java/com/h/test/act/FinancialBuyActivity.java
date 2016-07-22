package cn.hdmoney.hdy.act;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.Entity.Account;
import cn.hdmoney.hdy.Entity.Bid;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.Entity.OrderSuccessResult;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.constant.Constants;
import cn.hdmoney.hdy.mvp.presenter.FinancialBuyPresenter;
import cn.hdmoney.hdy.mvp.ui.FinancialBuyUi;
import cn.hdmoney.hdy.mvp.ui.FinancialBuyUiCallBack;
import cn.hdmoney.hdy.utils.DecimalFormatUtil;
import cn.hdmoney.hdy.utils.EditTextWatcherHandler;
import cn.hdmoney.hdy.view.CouponPopWindow;
import cn.hdmoney.hdy.view.NumberSettingView;
import cn.hdmoney.hdy.view.TitleBar;
import ru.vang.progressswitcher.ProgressWidget;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class FinancialBuyActivity extends BaseActivity implements FinancialBuyUi, CouponPopWindow.OnCouponPopWindowListener, NumberSettingView.OnValueChangedListener{
    private static final String INTENT_BID_ID = "intent_bid_id";
    private FinancialBuyUiCallBack callBack;
    private final int REQUEST_CODE_CONFIRM_BUY = 2;
    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.number_setting)
    NumberSettingView numberSettingView;
    @BindView(R.id.et_trade_password)
    EditText tradePasswordView;
    @BindView(R.id.check_box)
    CheckBox checkBox;

    @BindView(R.id.tv_name)
    TextView nameView;
    @BindView(R.id.tv_surplus_number)
    TextView surplusNumberView;
    @BindView(R.id.tv_money)
    TextView moneyView;
    @BindView(R.id.tv_year_rate)
    TextView yearRateView;
    @BindView(R.id.tv_profit)
    TextView profitView;
    @BindView(R.id.tv_enable_money)
    TextView enableMoneyView;
    @BindView(R.id.tv_red_packet)
    TextView redPacketView;
    @BindView(R.id.tv_add_rate)
    TextView addRateView;
    @BindView(R.id.container)
    View view;
    @BindView(R.id.tv_buy)
    View buyView;

    private EditTextWatcherHandler textWatcherHandler;
    private CouponPopWindow couponPopWindow;
    private Coupon selectedRedPacket;
    private Coupon selectedAddRate;

    private Bid bid;
    private List<Coupon> redPackets;
    private List<Coupon> addRates;
    private Account account;

    public static Intent buildIntent(Context context, int bidId) {
        Intent intent = new Intent(context, FinancialBuyActivity.class);
        intent.putExtra(INTENT_BID_ID, bidId);
        return intent;
    }

    @OnClick(R.id.tv_pledge_protocol)
    public void onPledgeProtocolClick(View view) {
        ToastUtils.show(context, "onPledgeProtocolClick");
    }

    @OnClick(R.id.tv_delegate_protocol)
    public void onDelegateProtocolClick(View view) {
        ToastUtils.show(context, "onDelegateProtocolClick");
    }

    @OnClick(R.id.tv_buy)
    public void onConfirmClick(View view) {
        if (check()) {
            StringBuffer couponIdsBuffer = new StringBuffer("");
            if (selectedRedPacket != null) {
                couponIdsBuffer.append(selectedRedPacket.cid).append(";");
            }
            if (selectedAddRate != null) {
                couponIdsBuffer.append(selectedAddRate.cid).append(";");
            }
            String couponIds = couponIdsBuffer.toString();
            if (!TextUtils.isEmpty(couponIds)) {
                couponIds = couponIds.substring(0, couponIds.length() - 1);
            }
            callBack.confirmBuy(bid.id, numberSettingView.getIntValue() * bid.baseAmount, couponIds, tradePasswordView.getText().toString());
        }
    }

    @OnClick(R.id.tv_recharge)
    public void onRechargeClick(View view) {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rl_red_packet_container)
    public void onRedPacketClick(View view) {
        if (couponPopWindow == null) {
            couponPopWindow = new CouponPopWindow(this);
            couponPopWindow.setOnCouponPopWindowListener(this);
            couponPopWindow.setAnimationStyle(R.style.listPopWindowStyle);
        }
        couponPopWindow.setData(CouponPopWindow.FLAG_RED_PACKET, redPackets, selectedRedPacket);
        couponPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @OnClick(R.id.rl_add_rate_container)
    public void onAddRateClick(View view) {
        if (couponPopWindow == null) {
            couponPopWindow = new CouponPopWindow(this);
            couponPopWindow.setOnCouponPopWindowListener(this);
            couponPopWindow.setAnimationStyle(R.style.listPopWindowStyle);
        }
        couponPopWindow.setData(CouponPopWindow.FLAG_ADD_RATE, addRates, selectedAddRate);
        couponPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public Presenter setPresenter() {
        return new FinancialBuyPresenter(this, getIntent().getIntExtra(INTENT_BID_ID, 0));
    }

    @Override
    protected void initView() {
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        numberSettingView.setOnValueChangedListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setBuyViewEnabled();
            }
        });
        setBuyViewEnabled();
        textWatcherHandler = new EditTextWatcherHandler();
        textWatcherHandler.setEditTexts(numberSettingView.getEditText(), tradePasswordView);
        textWatcherHandler.setListener(new EditTextWatcherHandler.TextWatcherListener() {
            @Override
            public void onTextWatcher() {
                setBuyViewEnabled();
            }
        });
    }

    public void setBuyViewEnabled() {
        buyView.setEnabled(checkBox.isChecked() && !TextUtils.isEmpty(numberSettingView.getValue()) && !TextUtils.isEmpty(tradePasswordView.getText()));
    }

    public boolean check() {
        if (bid == null || account == null) {
            ToastUtils.show(this, "数据异常");
            return false;
        }
        if (numberSettingView.getDoubleValue() < 1) {
            ToastUtils.show(this, "投资份额不能空");
            return false;
        }
        if (TextUtils.isEmpty(tradePasswordView.getText())) {
            ToastUtils.show(this, "交易密码不能空");
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CONFIRM_BUY) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void initData() {
        if (bid != null) {
            nameView.setText(bid.name);
            surplusNumberView.setText("当前剩余份额 " + DecimalFormatUtil.format(bid.residueShare, "#0") + "份");
        }
        if (account != null) {
            enableMoneyView.setText("可用余额：" + DecimalFormatUtil.format(account.balance, "#0.00") + "元");
        }
        notifyNumberSettingLimitChanged();
        notifyUseMoneyChanged();
        notifyYearRateChanged();
        notifyProfitChanged();
        notifyRedPacket();
        notifyAddRate();
    }

    private void notifyNumberSettingLimitChanged() {
        if (bid != null && account != null) {
            if (selectedRedPacket != null) {
                numberSettingView.setRangeLimit(0, Math.min(bid.residueShare, (account.balance + selectedRedPacket.amount) / bid.baseAmount));
            } else {
                numberSettingView.setRangeLimit(0, Math.min(bid.residueShare, account.balance / bid.baseAmount));
            }
        }
    }

    private void notifyUseMoneyChanged() {
        if (bid == null) {
            moneyView.setText("0.00元");
            return ;
        }
        int number = numberSettingView.getIntValue();
        if (selectedRedPacket != null) {
            moneyView.setText(DecimalFormatUtil.format((number * bid.baseAmount - selectedRedPacket.amount), "#0.00") + "元+" + DecimalFormatUtil.format(selectedRedPacket.amount, "#0.00") + "元");
        } else {
            moneyView.setText(DecimalFormatUtil.format((number * bid.baseAmount), "#0.00") + "元");
        }
    }

    private void notifyYearRateChanged() {
        if (bid == null) {
            yearRateView.setText("--%");
            return ;
        }
        yearRateView.setText(bid.apr + "%");
        if (selectedAddRate != null) {
            yearRateView.append("+" + selectedAddRate.amount + "%");
        }
    }

    private void notifyProfitChanged() {
        if (bid == null) {
            profitView.setText("--元");
            return ;
        }
        int number = numberSettingView.getIntValue();
        profitView.setText(DecimalFormatUtil.format((number * bid.baseAmount), "#0.00") + "元");
    }

    private void notifyRedPacket() {
        if (selectedRedPacket == null) {
            redPacketView.setText("不使用");
        } else {
            redPacketView.setText(selectedRedPacket.amount + " 元");
        }
    }

    private void notifyAddRate() {
        if (selectedAddRate == null) {
            addRateView.setText("不使用");
        } else {
            addRateView.setText(selectedAddRate.amount + " %");
        }
    }

    @Override
    public int getContentView() {
        return R.layout.activity_financial_buy;
    }

    @Override
    public void onAttachedUi() {}

    @Override
    public void showFinancial(Bid bid, List<Coupon> coupons, Account account) {
        progressWidget.showContent();
        this.bid = bid;
        this.account = account;
        addRates = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Coupon coupon = new Coupon();
            coupon.cid = i;
            coupon.type = Constants.MY_COUPON_TYPE_ADD_RATE;
            coupon.amount = (i + 1) * 0.5;
            coupon.couponName = coupon.amount + "%加息卷";
            coupon.description = "单笔满 2000 可使用（活利银产品除外）";
            addRates.add(coupon);
        }

        redPackets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Coupon coupon = new Coupon();
            coupon.cid = i;
            coupon.type = Constants.MY_COUPON_TYPE_RED_PACKET;
            coupon.amount = (i + 1) * 10;
            coupon.couponName = coupon.amount + " 元红包";
            coupon.description = "单笔满 2000 可使用（活利银产品除外）";
            redPackets.add(coupon);
        }

        initData();
    }

    @Override
    public void showError(String message) {
        progressWidget.showContent();
        ToastUtils.show(this, message);
    }

    @Override
    public void gotoFinancialBuySuccessUI(OrderSuccessResult result) {
        OrderSuccessResult tempResult = new OrderSuccessResult();
        tempResult.id = bid.id;
        tempResult.amount = numberSettingView.getIntValue() * bid.baseAmount;
        tempResult.bidName = bid.name;
        tempResult.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<Coupon> coupons = new ArrayList<>();
        if (selectedAddRate != null) {
            coupons.add(selectedAddRate);
        }
        if (selectedRedPacket != null) {
            coupons.add(selectedRedPacket);
        }
        tempResult.couponList = coupons;
        Intent intent = FinancialBuySuccessActivity.buildIntent(this, tempResult);
        startActivityForResult(intent, REQUEST_CODE_CONFIRM_BUY);
    }

    @Override
    public void setUiCallback(FinancialBuyUiCallBack callback) {
        this.callBack = callback;
    }

    @Override
    public void onConfirm(Coupon coupon, int type) {
        if (type == CouponPopWindow.FLAG_RED_PACKET) {
            if (selectedRedPacket != coupon) {
                selectedRedPacket = coupon;
                notifyRedPacket();
                notifyUseMoneyChanged();
                notifyNumberSettingLimitChanged();
            }
        } else if (type == CouponPopWindow.FLAG_ADD_RATE) {
            if (selectedAddRate != coupon) {
                selectedAddRate = coupon;
                notifyAddRate();
                notifyYearRateChanged();
            }
        }
        couponPopWindow.dismiss();
    }

    @Override
    public void onDismiss() {}

    @Override
    public void onValueChanged(double value) {
        notifyUseMoneyChanged();
        notifyProfitChanged();
    }
}

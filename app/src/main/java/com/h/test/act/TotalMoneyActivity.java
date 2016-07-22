package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liuguangqiang.android.mvp.Presenter;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.Account;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.repository.ApiFactory;
import cn.hdmoney.hdy.repository.HdyObserver;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.utils.SpannableUtils;
import cn.hdmoney.hdy.view.TitleBar;
import cn.hdmoney.hdy.view.TotalMoneyPieView;
import ru.vang.progressswitcher.ProgressWidget;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Administrator on 2016/6/8 0008.
 */
public class TotalMoneyActivity extends BaseActivity {

    @BindView(R.id.progress_widget)
    ProgressWidget progressWidget;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_enable_money)
    TextView tvEnableMoney;
    @BindView(R.id.tv_frozen_money)
    TextView tvFrozenMoney;
    @BindView(R.id.tv_in_money)
    TextView tvInMoney;
    @BindView(R.id.pie)
    TotalMoneyPieView pieView;

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
        initProgressWidget();
        loadData();
    }

    @Override
    public void initData() {
    }

    private void showData(Account account) {
        if (account.getTotalMoney() == 0) {
            progressWidget.showEmpty();
        } else {
            progressWidget.showContent();
            SpannableString span = new SpannableString("￥" + String.valueOf(account.getTotalMoney()));
            SpannableUtils.setTextSize(span, 0, 1, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
            tvTotalMoney.setText(span);
            tvEnableMoney.setText(String.valueOf(account.balance));
            tvFrozenMoney.setText(String.valueOf(account.freezeAmount));
            tvInMoney.setText(String.valueOf(account.investing));
            pieView.setNum((float)account.balance, (float)account.freezeAmount, (float)account.investing);
        }
    }

    private void initProgressWidget() {
        progressWidget.findViewById(R.id.goto_recharge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TotalMoneyActivity.this, RechargeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_total_money;
    }

    @Override
    public void onAttachedUi() {}

    private void loadData() {
        ApiFactory.getHdyApi().getAccountInfo(12223, "f33239446565d76c0e204f09b778caf2").observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HdyObserver<Result<Account>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onThrowable(Throwable e) {
                        showData(getTempData());
                    }

                    @Override
                    public void onNext(Result<Account> accountResult) {
                        showData(getTempData());
                    }
                });
    }

    private Account getTempData() {
        Account account = new Account();
        account.uid = 12223;
        if (Math.random() > 0.5) {
            account.balance = 1000.20;
            account.freezeAmount = 0;
            account.investing = 2000;
            account.interestAmount = 102.56;
        }
        return account;
    }
}

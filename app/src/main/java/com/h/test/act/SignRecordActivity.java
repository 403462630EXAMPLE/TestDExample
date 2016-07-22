package cn.hdmoney.hdy.act;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;
import com.wt.signcalendar.SignCalendarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/13.
 */
public class SignRecordActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.sign_score)
    TextView signScore;
    @BindView(R.id.sign_strategy)
    TextView signStrategy;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.ll_sign)
    LinearLayout llSign;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.calendar_card_view)
    SignCalendarView calendarCardView;


    @Override
    public Presenter setPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_signrecord_layout;
    }

    @Override
    public void onAttachedUi() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

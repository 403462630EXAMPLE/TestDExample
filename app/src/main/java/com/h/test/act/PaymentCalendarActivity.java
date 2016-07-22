package cn.hdmoney.hdy.act;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liuguangqiang.android.mvp.Presenter;
import com.wt.calendarcard.CalendarCardView;
import com.wt.calendarcard.CardPagerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.adapter.PaymentCalendarRecycleAdapter;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class PaymentCalendarActivity extends BaseActivity implements CalendarCardView.OnDaySelectedListener {

    private static final String TAG = "PaymentCalendarActivity";
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    PaymentCalendarRecycleAdapter adapter;

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
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PaymentCalendarRecycleAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnDaySelectedListener(this);
    }

    @Override
    public void initData() {
        adapter.setDatas(getTempDatas());
    }

    @Override
    public int getContentView() {
        return R.layout.activity_payment_calendar;
    }

    @Override
    public void onAttachedUi() {

    }

    @Override
    public void onSelectedDay(CardPagerAdapter.CalendarDay calendarDay) {
        adapter.setDatas(getTempDatas());
        adapter.setSignDays(getTempSignDays(calendarDay));
    }

    private List getTempDatas() {
        List list = new ArrayList();
        if (Math.random() > 0.5) {
            for (int i = 0; i < Math.random() * 20; i++) {
                list.add(null);
            }
        }
        return list;
    }

    private List<CardPagerAdapter.CalendarDay> getTempSignDays(CardPagerAdapter.CalendarDay selectedCalendarDay) {
        List<CardPagerAdapter.CalendarDay> list = new ArrayList<>();
        for (int i = 0; i < Math.random() * 10; i++) {
            int day = (int) (Math.random() * 30);
            CardPagerAdapter.CalendarDay calendarDay = new CardPagerAdapter.CalendarDay(selectedCalendarDay.year, selectedCalendarDay.month, day);
            list.add(calendarDay);
        }
        return list;
    }
}

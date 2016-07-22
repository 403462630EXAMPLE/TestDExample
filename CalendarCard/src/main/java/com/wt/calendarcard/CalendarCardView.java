package com.wt.calendarcard;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/6/6 0006.
 */
public class CalendarCardView extends LinearLayout{
    private static final String TAG = "CalendarCardView";
    private CalendarCardPager calendarCardPager;
    private CardPagerAdapter cardPagerAdapter;
    private OnDaySelectedListener onDaySelectedListener;
    private ImageView preView;
    private ImageView nextView;
    private TextView titleView;

    public void setSignDays(List<CardPagerAdapter.CalendarDay> signDays) {
        cardPagerAdapter.setSignDays(signDays);
    }

    public void setSelectedCalendarDay(CardPagerAdapter.CalendarDay calendarDay) {
        cardPagerAdapter.setmSelectedDay(calendarDay);
    }

    public void setOnDaySelectedListener(OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
        if (cardPagerAdapter != null) {
            cardPagerAdapter.setOnDaySelectedListener(onDaySelectedListener);
        }
    }

    public CalendarCardView(Context context) {
        this(context, null);
    }

    public CalendarCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.calendar_card_view, this, true);
        preView = (ImageView) findViewById(R.id.iv_pre);
        nextView = (ImageView) findViewById(R.id.iv_next);
        titleView = (TextView) findViewById(R.id.tv_title);
        preView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarCardPager.setCurrentItem(calendarCardPager.getCurrentItem() - 1);
            }
        });
        nextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarCardPager.setCurrentItem(calendarCardPager.getCurrentItem() + 1);
            }
        });

        calendarCardPager = (CalendarCardPager) findViewById(R.id.calendar_card_pager);
        cardPagerAdapter = calendarCardPager.getCardPagerAdapter();
        setOnDaySelectedListener(onDaySelectedListener);

        calendarCardPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                try {
                    Calendar calendar = cardPagerAdapter.getCurrentCalendar(position);
                    titleView.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        final Calendar calendar = cardPagerAdapter.getCurrentCalendar(calendarCardPager.getCurrentItem());
        titleView.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
    }

    private int getPosition(TabLayout.Tab tab) {
        Calendar calendar = cardPagerAdapter.getCurrentCalendar(calendarCardPager.getCurrentItem());
        int year = calendar.get(Calendar.YEAR);
        return (year - 1970) * 12 + tab.getPosition();
    }

    public static interface OnDaySelectedListener{
        public void onSelectedDay(CardPagerAdapter.CalendarDay calendarDay);
    }
}

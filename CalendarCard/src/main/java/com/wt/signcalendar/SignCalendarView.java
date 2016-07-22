package com.wt.signcalendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wt.calendarcard.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/6/14.
 */
public class SignCalendarView extends LinearLayout {
    RadioGroup radioGroup;
    TextView monthView;
    TextView yearView;
    OnDaySelectListener onDaySelectListener;
    CalendarPagerAdapter cardPagerAdapter;
    CalendarCardPager2 calendarCardPager;
    private String tag ="Sign";

    public static interface OnDaySelectListener {
        public void onSelectedDay(CalendarPagerAdapter.CalendarDay2 calendarDay);
    }
    public void setOnDaySelectListener(OnDaySelectListener onDaySelectListener) {
        this.onDaySelectListener = onDaySelectListener;
        if (cardPagerAdapter != null) {
            cardPagerAdapter.setOnDaySelectedListener(onDaySelectListener);
        }
    }

    public SignCalendarView(Context context) {
        super(context);
    }

    public SignCalendarView(Context context, AttributeSet attributeSet) {
        this(context,attributeSet,0);
    }
    public SignCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.sign_calendar_view2,this, true);

        yearView = (TextView)findViewById(R.id.tv_year);
        monthView = (TextView)findViewById(R.id.tv_month);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        calendarCardPager = (CalendarCardPager2)findViewById(R.id.calendar_card_pager2);

        cardPagerAdapter = calendarCardPager.getCardPagerAdapter();
        setOnDaySelectListener(onDaySelectListener);

        Calendar calendar = cardPagerAdapter.getCurrentCalendar(1);

        yearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        monthView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));

        LayoutParams layoutParams = new LayoutParams(SignCalendarView.dip2px(
                context, 13), SignCalendarView.dip2px(context, 8));
        for (int i = 0; i < 3; i++) {
            RadioButton radio = (RadioButton) LayoutInflater.from(
                    context).inflate(R.layout.radiobutton, null);
            radio.setLayoutParams(layoutParams);
            radio.setId(i);
            if (Integer.valueOf(radio.getId())==1) {
                radio.setChecked(true);
            }
            radioGroup.addView(radio);
        }

        calendarCardPager.setCurrentItem(1);

        calendarCardPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(tag,position+"");
                radioGroup.clearCheck();
                radioGroup.check(position);
                Calendar calendar = cardPagerAdapter.getCurrentCalendar(position);
                yearView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
                monthView.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}

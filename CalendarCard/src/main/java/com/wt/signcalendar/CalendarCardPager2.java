package com.wt.signcalendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.wt.calendarcard.CardPagerAdapter;

public class CalendarCardPager2 extends ViewPager {

    private CalendarPagerAdapter mCardPagerAdapter;

    public CalendarCardPager2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context);
    }

    public CalendarCardPager2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarCardPager2(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mCardPagerAdapter = new CalendarPagerAdapter(context);
        setAdapter(mCardPagerAdapter);
    }

    public CalendarPagerAdapter getCardPagerAdapter() {
        return mCardPagerAdapter;
    }
}

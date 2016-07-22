package com.wt.calendarcard;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.fourmob.datetimepicker.Utils;

public class CardPagerAdapter extends PagerAdapter implements SimpleMonthView.OnDayClickListener{

    private Context mContext;
    private CalendarDay mSelectedDay;
    private CalendarCardView.OnDaySelectedListener onDaySelectedListener;
    private List<CalendarDay> signDays;

    public void setSignDays(List<CalendarDay> signDays) {
        this.signDays = signDays;
        notifyDataSetChanged();
    }

    public void setmSelectedDay(CalendarDay mSelectedDay) {
        if (!isSameDay(mSelectedDay)) {
            this.mSelectedDay = mSelectedDay;
            notifyDataSetChanged();
        }
    }

    public void setOnDaySelectedListener(CalendarCardView.OnDaySelectedListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public int getTodayPosition() {
        return todayPosition;
    }

    private int todayPosition = 0;

    public CardPagerAdapter(Context ctx) {
        mContext = ctx;
        mSelectedDay = new CalendarDay(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        todayPosition = (year - 1970) * 12 + month;
    }

    public Calendar getCurrentCalendar(int position) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, position - todayPosition);
        return cal;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        Calendar cal = getCurrentCalendar(position);

        View view = LayoutInflater.from(mContext).inflate(R.layout.simple_month_view, null);
        SimpleMonthView simpleMonthView = (SimpleMonthView) view.findViewById(R.id.simple_month_view);
        simpleMonthView.setClickable(true);
        simpleMonthView.setOnDayClickListener(this);
        simpleMonthView.setSignDays(signDays);
        HashMap<String, Integer> drawingParams = null;
        if (drawingParams == null) {
            drawingParams = new HashMap<String, Integer>();
        }
        drawingParams.clear();

        final int month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);

        int selectedDay = -1;
        if (isSelectedDayInMonth(year, month)) {
            selectedDay = mSelectedDay.day;
        }

        drawingParams.put(SimpleMonthView.VIEW_PARAMS_SELECTED_DAY, selectedDay);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_YEAR, year);
        drawingParams.put(SimpleMonthView.VIEW_PARAMS_MONTH, month);
//        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, cal.getFirstDayOfWeek());
        simpleMonthView.setMonthParams(drawingParams);
        ((ViewPager) collection).addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void finishUpdate(View arg0) {}
    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {}
    @Override
    public Parcelable saveState() { return null; }
    @Override
    public void startUpdate(View arg0) {}

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return (position + 1) + "月";//;super.getPageTitle(position);
//    }

    @Override
    public int getCount() {
        // TODO almoast ifinite ;-)
        return todayPosition * 2;//Integer.MAX_VALUE;
    }

    private boolean isSelectedDayInMonth(int year, int month) {
        return (mSelectedDay.year == year) && (mSelectedDay.month == month);
    }

    public boolean isSameDay(CalendarDay calendarDay) {
        if (mSelectedDay != null && calendarDay.year == mSelectedDay.year && mSelectedDay.month == calendarDay.month && mSelectedDay.day == calendarDay.day) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay.day < 1 || calendarDay.day > Utils.getDaysInMonth(calendarDay.month, calendarDay.year)) {
            return ;
        }
        setmSelectedDay(calendarDay);
        if (onDaySelectedListener != null) {
            onDaySelectedListener.onSelectedDay(calendarDay);
        }
//        Toast.makeText(mContext, "onDayClick: " + calendarDay.year + "-" + calendarDay.month + "-" + calendarDay.day, Toast.LENGTH_SHORT).show();
    }



    public static class CalendarDay {
        private Calendar calendar;

        public int day;
        public int month;
        public int year;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        private void setTime(long timeInMillis) {
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            calendar.setTimeInMillis(timeInMillis);
            month = this.calendar.get(Calendar.MONTH);
            year = this.calendar.get(Calendar.YEAR);
            day = this.calendar.get(Calendar.DAY_OF_MONTH);
        }

        public void set(CalendarDay calendarDay) {
            year = calendarDay.year;
            month = calendarDay.month;
            day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }
}

package com.wt.signcalendar;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.wt.calendarcard.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/14.
 */
public class CalendarPagerAdapter extends PagerAdapter implements SimpleMonthView2.OnDayClickListener {

    private Context mContext;
    private CalendarDay2 mSelectedDay;
    SignCalendarView.OnDaySelectListener onDaySelectedListener;
    ArrayList<String> list = new ArrayList<>();
    private String tag="calendar";

    public void setOnDaySelectedListener(SignCalendarView.OnDaySelectListener onDaySelectedListener) {
        this.onDaySelectedListener = onDaySelectedListener;
    }

    public int getTodayPosition() {
        return todayPosition;
    }

    private int todayPosition = 1;

    public CalendarPagerAdapter(Context ctx) {
        mContext = ctx;
        mSelectedDay = new CalendarDay2(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
    }

    public Calendar getCurrentCalendar(int position) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, position - todayPosition);
        return cal;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {
        Calendar cal = getCurrentCalendar(position);

        View view = LayoutInflater.from(mContext).inflate(R.layout.simple_month_view2, null);
        SimpleMonthView2 simpleMonthView = (SimpleMonthView2) view.findViewById(R.id.simple_month_view2);
        simpleMonthView.setClickable(true);
        simpleMonthView.setOnDayClickListener(this);
        HashMap<String, Integer> drawingParams = null;
        if (drawingParams == null) {
            drawingParams = new HashMap<String, Integer>();
        }
//        drawingParams.clear();

        final int month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);

        int selectedDay = -1;
        if (isSelectedDayInMonth(year, month)) {
            selectedDay = mSelectedDay.day;
        }

        drawingParams.put(SimpleMonthView2.VIEW_PARAMS_SELECTED_DAY, selectedDay);
        drawingParams.put(SimpleMonthView2.VIEW_PARAMS_YEAR, year);
        drawingParams.put(SimpleMonthView2.VIEW_PARAMS_MONTH, month);

//        drawingParams.put(SimpleMonthView.VIEW_PARAMS_WEEK_START, cal.getFirstDayOfWeek());
        //绘画日期
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
        return 3;//Integer.MAX_VALUE;
    }

    private boolean isSelectedDayInMonth(int year, int month) {
        return (mSelectedDay.year == year) && (mSelectedDay.month == month);
    }

    @Override
    public void onDayClick(SimpleMonthView2 simpleMonthView, CalendarDay2 calendarDay) {
        mSelectedDay = calendarDay;

        notifyDataSetChanged();
        if (onDaySelectedListener != null) {
            onDaySelectedListener.onSelectedDay(calendarDay);
        }
        String str = calendarDay.year + "-" + calendarDay.month + "-" + calendarDay.day;
        Log.i(tag, str);
        list.add(str);
        Log.i(tag, list.size() + "");
        simpleMonthView.addMarks(list,0);
//       从后台获取数据来标记
//        Toast.makeText(mContext, "onDayClick: " + calendarDay.year + "-" + calendarDay.month + "-" + calendarDay.day, Toast.LENGTH_SHORT).show();
    }

    public static class CalendarDay2 {
        private Calendar calendar;

        public int day;
        public int month;
        public int year;

        public CalendarDay2() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay2(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay2(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay2(Calendar calendar) {
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

        public void set(CalendarDay2 calendarDay) {
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

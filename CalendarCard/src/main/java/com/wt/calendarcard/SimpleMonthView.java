package com.wt.calendarcard;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.fourmob.datetimepicker.Utils;
import com.fourmob.datetimepicker.date.SimpleMonthAdapter;

import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SimpleMonthView extends View {

    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_YEAR = "year";
    public static final String VIEW_PARAMS_SELECTED_DAY = "selected_day";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";
    public static final String VIEW_PARAMS_NUM_DAYS = "num_days";
    public static final String VIEW_PARAMS_FOCUS_MONTH = "focus_month";
    public static final String VIEW_PARAMS_SHOW_WK_NUM = "show_wk_num";
    public static final String VIEW_PARAMS_SIGN_DAYS = "sign_days";

    private static final int SELECTED_CIRCLE_ALPHA = 60;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
	protected static int DAY_SELECTED_CIRCLE_SIZE;
	protected static int DAY_SEPARATOR_WIDTH = 0;
	protected static int MINI_DAY_NUMBER_TEXT_SIZE;
	protected static int MIN_HEIGHT = 10;
	protected static int MONTH_DAY_LABEL_TEXT_SIZE;
	protected static int MONTH_HEADER_SIZE;
	protected static int MONTH_LABEL_TEXT_SIZE;

	protected static float mScale = 0.0F;
    protected int mPadding = 0;

    private String mDayOfWeekTypeface;
    private String mMonthTitleTypeface;

    protected Paint spotPaint;
    protected Paint linePaint;
    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitleBGPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected int mDayTextColor;
    protected int mMonthTitleBGColor;
    protected int mMonthTitleColor;
    protected int mTodayNumberColor;
    protected int monthNumColor;
    protected int monthNumSelectedColor;
    protected int monthNumDisableColor;

    private final StringBuilder mStringBuilder;
    private final Formatter mFormatter;

    protected int mFirstJulianDay = -1;
    protected int mFirstMonth = -1;
    protected int mLastMonth = -1;
    protected boolean mHasToday = false;
    protected int mSelectedDay = -1;
    protected int mToday = -1;
    protected int mWeekStart = 1;
    protected int mNumDays = 7;
    protected int mNumCells = mNumDays;
    protected int preNumCells = 0;
    protected int nextNumCells = 0;
    protected int mSelectedLeft = -1;
    protected int mSelectedRight = -1;
    private int mDayOfWeekStart = 0;
    protected int mMonth;
    protected int mRowHeight = DEFAULT_HEIGHT;
    protected int mWidth;
    protected int mYear;

    private final Calendar todayCalendar;
	private final Calendar mCalendar;
	private final Calendar mDayLabelCalendar;

    private List<CardPagerAdapter.CalendarDay> signDays;

    private int mNumRows = DEFAULT_NUM_ROWS;

	private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();

    private OnDayClickListener mOnDayClickListener;

	public SimpleMonthView(Context context) {
		this(context, null);
	}

    public SimpleMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Resources resources = context.getResources();
        todayCalendar = Calendar.getInstance();
        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();

        mDayOfWeekTypeface = resources.getString(R.string.day_of_week_label_typeface);
        mMonthTitleTypeface = resources.getString(R.string.sans_serif_new);
        mDayTextColor = Color.parseColor("#888888");//resources.getColor(R.color.date_picker_text_normal);
        mTodayNumberColor = Color.parseColor("#eb6c6e");//resources.getColor(R.color.blue);
        monthNumColor = Color.parseColor("#666666");
        monthNumSelectedColor = Color.parseColor("#ffffff");
        monthNumDisableColor = Color.parseColor("#d5d5d5");
        mMonthTitleColor = resources.getColor(R.color.white);
        mMonthTitleBGColor = resources.getColor(R.color.circle_background);

        mStringBuilder = new StringBuilder(50);
        mFormatter = new Formatter(mStringBuilder, Locale.getDefault());

        MINI_DAY_NUMBER_TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics());//resources.getDimensionPixelSize(R.dimen.day_number_size);
        MONTH_LABEL_TEXT_SIZE = resources.getDimensionPixelSize(R.dimen.month_label_size);
        MONTH_DAY_LABEL_TEXT_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());; //resources.getDimensionPixelSize(R.dimen.month_day_label_text_size);
        MONTH_HEADER_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());//resources.getDimensionPixelOffset(R.dimen.month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());//resources.getDimensionPixelSize(R.dimen.day_number_select_circle_radius);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        mRowHeight = MONTH_HEADER_SIZE;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());//(resources.getDimensionPixelOffset(R.dimen.date_picker_view_animator_height) - MONTH_HEADER_SIZE) / 6);

        initView();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
	}

	private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE / 2 + (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(mDateFormatSymbols.getShortWeekdays()[mDayLabelCalendar.get(Calendar.DAY_OF_WEEK)].replace("周", "").toUpperCase(Locale.getDefault()), x, y, mMonthDayLabelPaint);
        }
	}

	private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (MONTH_HEADER_SIZE - MONTH_DAY_LABEL_TEXT_SIZE) / 2 + (MONTH_LABEL_TEXT_SIZE / 3);
        canvas.drawText(getMonthAndYearString(), x, y, mMonthTitlePaint);
	}

	private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
	}

	private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_NO_MONTH_DAY;
        mStringBuilder.setLength(0);
        long millis = mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, flags);
    }

	private void onDayClick(CardPagerAdapter.CalendarDay calendarDay) {
		if (mOnDayClickListener != null) {
			mOnDayClickListener.onDayClick(this, calendarDay);
        }
	}

	private boolean sameDay(int monthDay, Time time) {
		return (mYear == time.year) && (mMonth == time.month) && (monthDay == time.monthDay);
	}

    private int getRelDay(int day, int dayOffset) {
        int num = day - dayOffset;
        if (num <= 0) {
            return -(preNumCells + num);
        }
        if (num > mNumCells) {
            return -(num - mNumCells);
        }
        return num;
    }

	protected void drawMonthNums(Canvas canvas) {
//		int y = (mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2 - DAY_SEPARATOR_WIDTH + MONTH_HEADER_SIZE;
        int y = (int) (mRowHeight / 2 - DAY_SEPARATOR_WIDTH + MONTH_HEADER_SIZE - ((mMonthNumPaint.descent() + mMonthNumPaint.ascent()) / 2));
		int paddingDay = (mWidth - 2 * mPadding) / (2 * mNumDays);
		int dayOffset = findDayOffset();
		int day = 1;
        int lineStartY = mRowHeight - DAY_SEPARATOR_WIDTH;
        int offset = 0;

//		while (day <= mNumCells) {
        while (day <= 6 * 7) {
//			int x = paddingDay * (1 + dayOffset * 2) + mPadding;
            int x = paddingDay * (1 + offset * 2) + mPadding;
            int relDay = getRelDay(day, dayOffset);
            if (relDay < 0) {
                mMonthNumPaint.setColor(monthNumDisableColor);
            } else {
                if (mSelectedDay == relDay) {
                    canvas.drawCircle(x, y - MINI_DAY_NUMBER_TEXT_SIZE / 3, DAY_SELECTED_CIRCLE_SIZE, mSelectedCirclePaint);
                    mMonthNumPaint.setColor(monthNumSelectedColor);
                } else if (mHasToday && (mToday == relDay)) {
                    mMonthNumPaint.setColor(mTodayNumberColor);
                } else {
                    mMonthNumPaint.setColor(mDayTextColor);
                }
            }

			canvas.drawText(String.format("%d", Math.abs(relDay)), x, y, mMonthNumPaint);
            if (hasSign(relDay)) {
                mCalendar.set(Calendar.DAY_OF_MONTH, relDay);
                if (todayCalendar.before(mCalendar)) {
                    spotPaint.setColor(mTodayNumberColor);
                } else {
                    spotPaint.setColor(monthNumDisableColor);
                }
                canvas.drawCircle(x, y + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.5f, getResources().getDisplayMetrics()), TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, getResources().getDisplayMetrics()), spotPaint);
            }
//			dayOffset++;
            offset++;
			if (offset == mNumDays) {
                offset = 0;
                y += mRowHeight;
                lineStartY += mRowHeight;
                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                canvas.drawLine(padding, lineStartY, getWidth() - padding, lineStartY, linePaint);
            }
			day++;
		}
	}

	public CardPagerAdapter.CalendarDay getDayFromLocation(float x, float y) {
		int padding = mPadding;
		if ((x < padding) || (x > mWidth - mPadding)) {
			return null;
		}

		int yDay = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
		int day = 1 + ((int) ((x - padding) * mNumDays / (mWidth - padding - mPadding)) - findDayOffset()) + yDay * mNumDays;

		return new CardPagerAdapter.CalendarDay(mYear, mMonth, day);
	}

	protected void initView() {
        spotPaint = new Paint();
        spotPaint.setAntiAlias(true);
        spotPaint.setStyle(Style.FILL);
        spotPaint.setTextAlign(Align.CENTER);
        spotPaint.setColor(monthNumDisableColor);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics()));
        linePaint.setColor(Color.parseColor("#e3e3e3"));
        linePaint.setStyle(Style.FILL);

        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_LABEL_TEXT_SIZE);
        mMonthTitlePaint.setTypeface(Typeface.create(mMonthTitleTypeface, Typeface.BOLD));
        mMonthTitlePaint.setColor(mDayTextColor);
        mMonthTitlePaint.setTextAlign(Align.CENTER);
        mMonthTitlePaint.setStyle(Style.FILL);

        mMonthTitleBGPaint = new Paint();
        mMonthTitleBGPaint.setFakeBoldText(true);
        mMonthTitleBGPaint.setAntiAlias(true);
        mMonthTitleBGPaint.setColor(mMonthTitleBGColor);
        mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        mMonthTitleBGPaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(mTodayNumberColor);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);
//        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setTypeface(Typeface.create(mDayOfWeekTypeface, Typeface.NORMAL));
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(true);
	}

	protected void onDraw(Canvas canvas) {
//		drawMonthTitle(canvas);
		drawMonthDayLabels(canvas);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        canvas.drawLine(padding, mRowHeight, getWidth() - padding, mRowHeight, linePaint);
		drawMonthNums(canvas);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows + MONTH_HEADER_SIZE);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
	}

    @Override
	public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            CardPagerAdapter.CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
	}

	public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
		requestLayout();
	}

    private boolean hasSign(int day) {
        if (signDays != null) {
            for (CardPagerAdapter.CalendarDay calendarDay : signDays) {
                if (calendarDay.year == mYear && calendarDay.month == mMonth && calendarDay.day == day) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setSignDays(List<CardPagerAdapter.CalendarDay> list) {
        this.signDays = list;
    }

	public void setMonthParams(HashMap<String, Integer> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify month and year for this view");
        }
		setTag(params);

        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_DAY)) {
            mSelectedDay = params.get(VIEW_PARAMS_SELECTED_DAY);
        }

        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        mHasToday = false;
        mToday = -1;

		mCalendar.set(Calendar.MONTH, mMonth);
		mCalendar.set(Calendar.YEAR, mYear);
		mCalendar.set(Calendar.DAY_OF_MONTH, 1);
		mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        if (mMonth == 0) {
            preNumCells = Utils.getDaysInMonth(11, mYear);
        } else {
            preNumCells = Utils.getDaysInMonth(mMonth - 1, mYear);
        }
        mNumCells = Utils.getDaysInMonth(mMonth, mYear);
        if (mMonth == 11) {
            nextNumCells = Utils.getDaysInMonth(0, mYear);
        } else {
            nextNumCells = Utils.getDaysInMonth(mMonth + 1, mYear);
        }
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }
        }

//        mNumRows = calculateNumRows();
	}

	public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
		mOnDayClickListener = onDayClickListener;
	}

	public static abstract interface OnDayClickListener {
		public abstract void onDayClick(SimpleMonthView simpleMonthView, CardPagerAdapter.CalendarDay calendarDay);
	}
}
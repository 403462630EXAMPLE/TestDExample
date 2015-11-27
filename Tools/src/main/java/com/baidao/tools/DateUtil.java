package com.baidao.tools;

import android.text.format.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rjhy on 15-1-6.
 */
public class DateUtil {
    private static final java.lang.String DATE_TIME_PATTERN = "yyyyMMdd HH:mm:ss";
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    /**
     *
     * @param value is milliseconds
     * @param format
     * @return
     */
    public static String format(long value, String format) {
        if (0 == value) {
            return "";
        }
        LocalDateTime date = new LocalDateTime(value);
        DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
        return date.toString(fmt);
    }

    public static String formatToGMT8(long dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sdf.format(new Date(dateTime));
    }

    public static DateTime parse(String str, String pattern) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(str, fmt);
    }

    public static String formatToGeneral(long value) {
        if (value == 0) {
            return "";
        }
        long currentValue = System.currentTimeMillis();
        if (currentValue < value) {
            return "刚刚";
        }
        if ((currentValue - value) >= 60 * 60 * 1000) {
            return format(value, "yyyy-MM-dd HH:mm");
        } else {
            int minute = (int) Math.ceil((currentValue - value)*1.0/(1000 * 60));
            if (minute == 1) {
                return "刚刚";
            } else {
                return minute + "分钟前";
            }
        }
    }
}

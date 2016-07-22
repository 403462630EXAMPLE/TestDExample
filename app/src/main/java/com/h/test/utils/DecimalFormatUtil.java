package cn.hdmoney.hdy.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    public static String format(double value, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }
}
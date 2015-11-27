package com.baidao.tools;

import java.text.DecimalFormat;

/**
 * Created by rjhy on 15-4-1.
 */
public class DecimalFormatUtil {

    public static String format(double value, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(value);
    }
}

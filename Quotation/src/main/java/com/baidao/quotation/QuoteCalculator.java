package com.baidao.quotation;

import com.baidao.tools.BigDecimalUtil;

import java.text.DecimalFormat;

/**
 * Created by BurizaDo on 4/28/15.
 */
public class QuoteCalculator {
    private static final String QUOTE_DEFAULT = "--";

    public static double computeUpdrop(Quote quote) {
        if (quote.now == 0) {
            return 0;
        }
        return BigDecimalUtil.scale(BigDecimalUtil.sub(quote.now, quote.preClose), quote.decimalDigits);
    }

    public static String computeUpdropPercent(Quote quote) {
        double preClose = quote.preClose;
        if (quote.now == 0 || preClose == 0) {
            return QUOTE_DEFAULT;
        }
        double updrop = computeUpdrop(quote);
        double percent = BigDecimalUtil.div(BigDecimalUtil.mul(updrop, 100), preClose, 2);
        String percentString = new DecimalFormat("0.00").format(percent);
        return percent > 0 ? "+" + percentString + "%" : percentString + "%";
    }

    public static String computeAmplitude(Quote quote) {
        if (quote.preClose == 0) {
            return QUOTE_DEFAULT;
        }
        double amplitude = BigDecimalUtil.div(BigDecimalUtil.mul(BigDecimalUtil.sub(quote.high, quote.low), 100), quote.preClose, 2);
        return amplitude + "%";
    }

}

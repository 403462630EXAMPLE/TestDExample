package cn.hdmoney.hdy.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FluentBigDecimal {

    private BigDecimal result;
    private int scale = 2;
    private RoundingMode roundingMode = RoundingMode.HALF_UP;

    public FluentBigDecimal(double init) {
        this.result = new BigDecimal(String.valueOf(init));
    }

    public FluentBigDecimal with(int scale, RoundingMode roundingMode) {
        this.scale = scale;
        this.roundingMode = roundingMode;
        return this;
    }

    public FluentBigDecimal div(double value) {
        result = result.divide(new BigDecimal(String.valueOf(value)), scale, roundingMode);
        return this;
    }

    public FluentBigDecimal minus(double value) {
        result = result.subtract(new BigDecimal(String.valueOf(value)));
        return this;
    }

    public FluentBigDecimal add(double value) {
        result = result.add(new BigDecimal(String.valueOf(value)));
        return this;
    }

    public double value() {
        return this.result.doubleValue();
    }

    public FluentBigDecimal mul(double value) {
        result = result.multiply(new BigDecimal(String.valueOf(value)));
        return this;
    }

    public FluentBigDecimal scale(int scale) {
        result = result.setScale(scale, roundingMode);
        return this;
    }
}
package cn.hdmoney.hdy.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.math.BigDecimal;

import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.utils.FluentBigDecimal;
import cn.hdmoney.hdy.utils.SpannableUtils;

/**
 * Created by Administrator on 2016/5/31 0031.
 */
public class NumberSettingView extends LinearLayout implements View.OnClickListener, View.OnFocusChangeListener {
    private final static String STATE_SCALE = "state_scale";
    private final static String STATE_PARCELABLE = "state_parcelable";
    private final static String STATE_MAXVALUE = "state_maxValue";
    private final static String STATE_MINVALUE = "state_minValue";
    private final static String STATE_UNIT = "state_unit";
    private final static String STATE_VALUE = "state_value";
    private final int DEFAULT_SCALE = 2;
    private ImageView addView;
    private ImageView minusView;
    private EditText editText;
    private double unit = 1;
    private double min = 0;
    private double max = Integer.MAX_VALUE;
    private int scale;

    private OnValueChangedListener onValueChangedListener;

    public EditText getEditText() {
        return editText;
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setRangeLimit(double min, double max) {
        double lMin = min;
        double lMax = max;
        if (min > max) {
            lMin = max;
            lMax = min;
        }
        this.min = lMin;
        this.max = lMax;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public NumberSettingView(Context context) {
        this(context, null);
    }

    public NumberSettingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_number_setting, this, true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberSettingView);
        double unitValue = typedArray.getFloat(R.styleable.NumberSettingView_unit, 1f);
        double minValue = typedArray.getFloat(R.styleable.NumberSettingView_number_min, 0f);
        double maxValue = typedArray.getFloat(R.styleable.NumberSettingView_number_max, Integer.MAX_VALUE);
        int inputType = typedArray.getInt(R.styleable.NumberSettingView_number_type, 0);
        typedArray.recycle();

        addView = (ImageView) findViewById(R.id.iv_add);
        minusView = (ImageView) findViewById(R.id.iv_minus);
        editText = (EditText) ((ViewGroup)getChildAt(0)).getChildAt(1);
        SpannableString span = new SpannableString(editText.getHint());
        SpannableUtils.setTextSize(span, 0, span.length(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        editText.setHint(span);
        setMinusViewEnabled();
        setUnit(unitValue);
        setRangeLimit(minValue, maxValue);
        if (inputType == 0) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            setScale(0);
        } else {
            setScale(DEFAULT_SCALE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }

        editText.setOnFocusChangeListener(this);
        addView.setOnClickListener(this);
        minusView.setOnClickListener(this);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    double value = Double.valueOf(s.toString());
                    boolean hasChanged = false;
                    if (value > max) {
                        hasChanged = true;
                        value = max;
                    }
                    if (value < min) {
                        hasChanged = true;
                        value = min;
                    }
                    if (hasChanged) {
                        String valueStr = format(value);
                        editText.setText(valueStr);
                        editText.setSelection(valueStr.length());
                    } else {
                        setMinusViewEnabled();
                        if (onValueChangedListener != null) {
                            onValueChangedListener.onValueChanged(value);
                        }
                    }
                } catch (NumberFormatException e) {}
            }
        });
    }

    private void setMinusViewEnabled() {
        if (getDoubleValue() <= min) {
            minusView.setEnabled(false);
        } else {
            minusView.setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_add) {
            String value = format(add());
            editText.setText(value);
            editText.setSelection(value.length());
        } else if (v.getId() == R.id.iv_minus) {
            String value = format(minus());
            editText.setText(value);
            editText.setSelection(value.length());
        }
    }

    public String getValue() {
        return editText.getText().toString();
    }

    public boolean isValid() {
        double value = getDoubleValue();
        if (value < min) {
            return false;
        }
        if (value > max) {
            return false;
        }
        return true;
    }

    private String format(double value) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    public int getIntValue() {
        String value = editText.getText().toString();
        if (TextUtils.isEmpty(value)) {
            return 0;
        } else {
            return Integer.valueOf(value);
        }
    }

    public double getDoubleValue() {
        String value = editText.getText().toString();
        if (TextUtils.isEmpty(value)) {
            return 0;
        } else {
            return Double.valueOf(value);
        }
    }

    private double add() {
        FluentBigDecimal value = new FluentBigDecimal(getDoubleValue()).add(unit);
        if (value.value() > max) {
            return max;
        }
        if (value.value() < min) {
            return min;
        }
        return value.value();
    }

    private double minus() {
        FluentBigDecimal value = new FluentBigDecimal(getDoubleValue()).minus(unit);
        if (value.value() > max) {
            return max;
        }
        if (value.value() < min) {
            return min;
        }
        return value.value();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            double value = getDoubleValue();
            boolean hasChanged = false;
            if (value > max) {
                value = max;
                hasChanged = true;
            }
            if (value < min) {
                value = min;
                hasChanged = true;
            }
            if (hasChanged) {
                String valueStr = format(value);
                editText.setText(valueStr);
                editText.setSelection(valueStr.length());
            }
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        Parcelable parcelable = super.onSaveInstanceState();
        bundle.putParcelable(STATE_PARCELABLE, parcelable);
        bundle.putDouble(STATE_MAXVALUE, max);
        bundle.putDouble(STATE_MINVALUE, min);
        bundle.putDouble(STATE_UNIT, unit);
        bundle.putInt(STATE_SCALE, scale);
        bundle.putString(STATE_VALUE, getValue());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable(STATE_PARCELABLE));
        max = bundle.getDouble(STATE_MAXVALUE);
        min = bundle.getDouble(STATE_MINVALUE);
        unit = bundle.getDouble(STATE_UNIT);
        scale = bundle.getInt(STATE_SCALE);
        String value = bundle.getString(STATE_VALUE);
        if (!TextUtils.isEmpty(value)) {
            editText.setText(value);
        } else {
            editText.setText("");
        }
        setMinusViewEnabled();
    }

    public static interface OnValueChangedListener{
        public void onValueChanged(double value);
    }
}

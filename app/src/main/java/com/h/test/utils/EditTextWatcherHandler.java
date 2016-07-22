package cn.hdmoney.hdy.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20 0020.
 */
public class EditTextWatcherHandler {
    private List<EditText> list;
    private TextWatcherListener listener;

    public void setListener(TextWatcherListener listener) {
        this.listener = listener;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (listener != null) {
                if (list != null && !list.isEmpty()) {
                    listener.onTextWatcher();
                }
            }
        }
    };

    public void setEditTexts(EditText ...params) {
        List<EditText> tempList = new ArrayList();
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                tempList.add(params[i]);
            }
        }
        clearTextWatcher();
        this.list = tempList;
        addTextWatcher();
    }

    public EditTextWatcherHandler(List<EditText> list) {
        clearTextWatcher();
        this.list = list;
        addTextWatcher();
    }

    public EditTextWatcherHandler() {
        this(null);
    }

    private void addTextWatcher() {
        if (list != null) {
            for (EditText editText : list) {
                editText.removeTextChangedListener(textWatcher);
                editText.addTextChangedListener(textWatcher);
            }
        }
    }

    private void clearTextWatcher() {
        if (list != null) {
            for (EditText editText : list) {
                editText.removeTextChangedListener(textWatcher);
            }
        }
    }

    public static interface TextWatcherListener{
        public void onTextWatcher();
    }
}

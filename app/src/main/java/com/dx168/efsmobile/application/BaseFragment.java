package com.dx168.efsmobile.application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.baidao.efsmobile.R;


/**
 * Created by burizado on 14-12-16.
 */
public class BaseFragment extends Fragment {

    public void onStackTop(boolean isBack) {

    }

    public String getName() {
        return this.getClass().getName() + this.hashCode();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }

    protected void hideSoftKeyboard() {
        final View currentRoot = getView();
        final InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(currentRoot.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public boolean handleBack() {
        ((BaseActivity) getActivity()).popFragment();
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        if (getActivity() == null) return;

        super.startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public boolean handleDispatchKeyEvent(KeyEvent event) {
        return false;
    }
}

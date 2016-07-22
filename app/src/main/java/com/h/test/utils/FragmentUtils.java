package cn.hdmoney.hdy.utils;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class FragmentUtils {
    public static void pushFragment(FragmentManager fm, @IdRes int containerViewId, Fragment fragment, boolean addToBack) {
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.right_to_left_enter, R.anim.left_to_right_exit, R.anim.pop_left_to_right_enter, R.anim.pop_left_to_right_exit);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(containerViewId, fragment);
        if (addToBack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
        fm.executePendingTransactions();
    }

    public static void pushFragment(FragmentManager fm, @IdRes int containerViewId, Fragment fragment) {
        pushFragment(fm, containerViewId, fragment, false);
    }

    public static boolean popFragment(FragmentManager fm) {
        final int entryCount = fm.getBackStackEntryCount();
        FragmentTransaction ft = fm.beginTransaction();
        boolean popSucceed = true;
        if (entryCount <= 1) {
            fm.popBackStack();
        } else {
            popSucceed = fm.popBackStackImmediate();
        }
        ft.commit();
        return popSucceed;
    }

}

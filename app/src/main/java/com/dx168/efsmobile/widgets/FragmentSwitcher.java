package com.dx168.efsmobile.widgets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BurizaDo on 3/2/15.
 */
public class FragmentSwitcher {
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    private int containerResId;
    private int currentIndex = -1;

    public int getCurrentIndex() {
        return currentIndex;
    }

    public FragmentSwitcher(FragmentManager fragmentManager, int containerResId){
        this.fragmentManager = fragmentManager;
        this.containerResId = containerResId;
    }

    public void addFragment(Fragment fragment, String tag){
        fragments.add(fragment);

        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerResId, fragment, tag);
            transaction.hide(fragment);
            transaction.commit();
        }

    }

    public Fragment getFragment(int index){
        return index < fragments.size() ? fragments.get(index) : null;
    }

    public void switchToFragment(int index, boolean b) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (currentIndex != index || b) {
                fragmentTransaction.hide(fragments.get(i));
                fragments.get(i).setUserVisibleHint(false);
            }
        }
        fragmentTransaction.show(fragments.get(index));
        fragments.get(index).setUserVisibleHint(true);

        currentIndex = index;
        fragmentTransaction.commit();
    }

    public void switchToFragment(int index) {
        switchToFragment(index, false);
    }
}

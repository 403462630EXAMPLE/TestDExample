package com.dx168.efsmobile.home;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidao.efsmobile.R;
import com.dx168.efsmobile.application.BaseFragment;
import com.dx168.efsmobile.home.test.FragmentAdapter;
import com.dx168.efsmobile.home.test.ListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    @InjectView(R.id.appbar)
    AppBarLayout appBarLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_title)
    TextView titleView;
    @InjectView(R.id.vp_page)
    ViewPager viewPager1;
    @InjectView(R.id.viewpager)
    ViewPager viewPager2;
    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarStateChangeListener() {
        @Override
        public void onStateChanged(AppBarLayout appBarLayout, State state) {
            Log.i(TAG, "onStateChanged--" + state.name());
            switch (state) {
                case COLLAPSED:
                    titleView.setVisibility(View.VISIBLE);
                    break;
                case EXPANDED:
                    titleView.setVisibility(View.GONE);
                    break;
                case IDLE:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appBarLayout.removeOnOffsetChangedListener(onOffsetChangedListener);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
        viewPager1.setAdapter(pagerAdapter);
    }

    private void setupViewPager() {
        List<String> titles = new ArrayList<>();
        titles.add("Page One");
        titles.add("Page Two");
        titles.add("Page Three");
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        List<android.support.v4.app.Fragment> fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new ListFragment());
        fragments.add(new ListFragment());
        FragmentAdapter adapter =
                new FragmentAdapter(getChildFragmentManager(), fragments, titles);
        viewPager2.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager2);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.test_view_page_item, null);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };
}

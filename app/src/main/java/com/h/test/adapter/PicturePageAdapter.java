package cn.hdmoney.hdy.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hdmoney.hdy.fragment.PictureFragment;

/**
 * Created by Administrator on 2016/6/20 0020.
 */
public class PicturePageAdapter extends FragmentStatePagerAdapter {

    private List<String> list = new ArrayList<>();

    public void setImages(List<String> images) {
        this.list = images;
        notifyDataSetChanged();
    }

    public PicturePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        PictureFragment fragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image", list.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }
}

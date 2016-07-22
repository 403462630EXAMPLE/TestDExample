package com.common.widget.viewpagerheader;

import android.support.v4.app.Fragment;

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder, LoadingCallBack{

	protected ScrollTabHolder mScrollTabHolder;
	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}
}
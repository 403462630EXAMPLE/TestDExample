package com.common.widget.viewpagerheader;

public interface LoadingCallBack {

	boolean isLoading();
	
	void setOnLoadingCompletedListener(OnLoadingCompletedListener listener);
	
	void refreshData();
}

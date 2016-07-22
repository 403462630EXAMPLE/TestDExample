package com.hdy.loadmorerecycleview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.hdy.loadmorerecycleview.adapter.ItemFcAdapter;
import com.hdy.loadmorerecycleview.adapter.ItemNotifyAdapter;
import com.hdy.loadmorerecycleview.adapter.ItemScrollAdapter;
import com.hdy.loadmorerecycleview.adapter.LoadMoreCombinationFcAdapter;
import com.hdy.loadmorerecycleview.adapter.LoadMoreFcAdapter;


/**
 * Created by rjhy on 15-3-4.
 */
public class LoadMoreRecycleView extends RecyclerView {

    private LoadMoreCombinationFcAdapter fcAdapter;
    private ItemScrollAdapter itemScrollAdapter;
    private ItemNotifyAdapter itemNotifyAdapter;

    private OnScrollListener mOnScrollListener;

    private boolean flag;

    public LoadMoreRecycleView(Context context) {
        this(context, null);
    }

    public LoadMoreRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(onScrollListener);
    }

    private OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrolled(recyclerView, dx, dy);
            }
            if (flag) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                if (itemScrollAdapter != null) {
                    itemScrollAdapter.scroll(pastVisiblesItems, visibleItemCount, totalItemCount);
                }
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = true;
            }
        }, 1000);
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        if (this.onScrollListener == onScrollListener) {
            super.setOnScrollListener(onScrollListener);
        } else {
            mOnScrollListener = onScrollListener;
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof ItemFcAdapter) {
            super.setAdapter(adapter);
        } else {
            fcAdapter = new LoadMoreCombinationFcAdapter<>(getContext(), adapter);
            super.setAdapter(fcAdapter);
        }

        if (getAdapter() instanceof ItemScrollAdapter) {
            itemScrollAdapter = (ItemScrollAdapter) getAdapter();
        }

        if (getAdapter() instanceof ItemNotifyAdapter) {
            itemNotifyAdapter = (ItemNotifyAdapter) getAdapter();
        }
    }

    @Override
    public Adapter getAdapter() {
        if (fcAdapter != null) {
            return fcAdapter;
        } else {
            return super.getAdapter();
        }
    }


    public void notifyError() {
        if (itemNotifyAdapter != null) {
            itemNotifyAdapter.notifyError();
        }
    }


    public void notifyLoadding() {
        if (itemNotifyAdapter != null) {
            itemNotifyAdapter.notifyLoadding();
        }
    }


    public void notifyLoadedAll() {
        if (itemNotifyAdapter != null) {
            itemNotifyAdapter.notifyLoadedAll();
        }
    }


    public void notifyNormal() {
        if (itemNotifyAdapter != null) {
            itemNotifyAdapter.notifyNormal();
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        Adapter adapter = getAdapter();
        if (adapter != null) {
            if (adapter instanceof LoadMoreCombinationFcAdapter) {
                ((LoadMoreCombinationFcAdapter)adapter).setOnLoadMoreListener(listener);
            } else if (adapter instanceof LoadMoreFcAdapter) {
                ((LoadMoreFcAdapter)adapter).setOnLoadMoreListener(listener);
            }
        }
    }
}

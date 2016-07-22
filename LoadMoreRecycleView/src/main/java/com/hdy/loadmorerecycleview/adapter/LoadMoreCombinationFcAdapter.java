package com.hdy.loadmorerecycleview.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hdy.loadmorerecycleview.OnLoadMoreListener;
import com.hdy.loadmorerecycleview.R;


/**
 * Created by rjhy on 15-3-4.
 */
public class LoadMoreCombinationFcAdapter<T> extends BaseItemCombinationFcAdapter implements ItemScrollAdapter, ItemNotifyAdapter {
    private Context context;
    private OnLoadMoreListener onLoadMoreListener;
    private Handler handler = new Handler();
    private LoadRunable runnable = new LoadRunable();

    private class LoadRunable implements Runnable {
        private LoadItemType loadType;

        public void setLoadType(LoadItemType loadType) {
            this.loadType = loadType;
        }

        @Override
        public void run() {
            LoadMoreCombinationFcAdapter.this.loadType = loadType;
            notifyItemChanged(getItemFcPosition());
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    private void setLoadItemType(final LoadItemType loadType) {
        handler.removeCallbacks(runnable);
        if (loadType != LoadItemType.LOADING) {
            runnable.setLoadType(loadType);
            handler.postDelayed(runnable, 500);
        } else {
            LoadMoreCombinationFcAdapter.this.loadType = loadType;
            notifyItemChanged(getItemFcPosition());
        }
    }

    @Override
    public void notifyError() {
        setLoadItemType(LoadItemType.ERROR);
    }

    @Override
    public void notifyLoadding() {
        setLoadItemType(LoadItemType.LOADING);
    }

    @Override
    public void notifyLoadedAll() {
        setLoadItemType(LoadItemType.LOADED_ALL);
    }

    @Override
    public void notifyNormal() {
        setLoadItemType(LoadItemType.NO_LOADING);
    }

    private LoadItemType loadType = LoadItemType.NO_LOADING;

    public static enum LoadItemType{
        LOADING, NO_LOADING, LOADED_ALL, ERROR
    }

    public boolean isLoading() {
        return loadType == LoadItemType.LOADING;
    }

    public boolean isLoadedAll() {
        return loadType == LoadItemType.LOADED_ALL;
    }

    public boolean isError() {
        return loadType == LoadItemType.ERROR;
    }

    public LoadMoreCombinationFcAdapter(Context context, RecyclerView.Adapter adapter) {
        super(adapter);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemFcViewHolder(ViewGroup parent, int viewType) {
        return new LoadMoreViewHolder(LayoutInflater.from(context).inflate(R.layout.load_more, parent, false));
    }

    @Override
    public void onBindItemFcViewHolder(RecyclerView.ViewHolder holder, int position) {
        LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
        switch (loadType) {
            case LOADING:
                loadMoreViewHolder.progressBar.setVisibility(View.VISIBLE);
                loadMoreViewHolder.contentView.setVisibility(View.VISIBLE);
                loadMoreViewHolder.contentView.setText("正在加载");
                break;
            case LOADED_ALL:
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                loadMoreViewHolder.contentView.setVisibility(View.VISIBLE);
                loadMoreViewHolder.contentView.setText("没有更多内容了哦");
                break;
            case NO_LOADING:
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                loadMoreViewHolder.contentView.setVisibility(View.VISIBLE);
                loadMoreViewHolder.contentView.setText("点击加载更多");
                loadMoreViewHolder.loadMoreContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLoadMoreListener != null && !isLoading() && !isLoadedAll()) {
                            setLoadItemType(LoadItemType.LOADING);
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                });
                break;
            case ERROR:
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                loadMoreViewHolder.contentView.setVisibility(View.VISIBLE);
                loadMoreViewHolder.contentView.setText("加载失败,点击重新加载");
                loadMoreViewHolder.loadMoreContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLoadMoreListener != null && !isLoading() && !isLoadedAll()) {
                            setLoadItemType(LoadItemType.LOADING);
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                });
                break;
            default:
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                loadMoreViewHolder.contentView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemFcPosition() {
        return getCount();
    }

    @Override
    public void scroll(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (getItemFcPosition() >= firstVisibleItem && getItemFcPosition() <= (firstVisibleItem + visibleItemCount) - 1) {
            if (!isLoading() && !isLoadedAll() && onLoadMoreListener != null) {
                setLoadItemType(LoadItemType.LOADING);
                onLoadMoreListener.onLoadMore();
            }
        }
    }

    static class LoadMoreViewHolder extends RecyclerView.ViewHolder{

        LinearLayout loadMoreContainer;
        ProgressBar progressBar;
        TextView contentView;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            loadMoreContainer = (LinearLayout) itemView.findViewById(R.id.ll_load_more_container);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loadding);
            contentView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}

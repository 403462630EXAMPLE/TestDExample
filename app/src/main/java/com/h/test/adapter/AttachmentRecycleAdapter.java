package cn.hdmoney.hdy.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hdmoney.hdy.R;

public class AttachmentRecycleAdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE_DEFAULT = 1;
    private final int ITEM_TYPE_ADD = 2;
    private OnAttachmentListener onAttachmentListener;
    private List<Uri> data = new ArrayList<>();

    public void add(Uri uri) {
        data.add(uri);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    public void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void setOnAttachmentListener(OnAttachmentListener onAttachmentListener) {
        this.onAttachmentListener = onAttachmentListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_ADD;
        } else {
            return ITEM_TYPE_DEFAULT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ADD) {
            return new AttachmentAddRecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attachment_add, parent, false));
        }
        return new AttachmentRecycleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attachment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AttachmentRecycleViewHolder) {
            ((AttachmentRecycleViewHolder) holder).tvDelete.setTag(position);
            ((AttachmentRecycleViewHolder) holder).imageView.setImageURI(getItem(position));
        }
    }

    private Uri getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    class AttachmentRecycleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_delete)
        View tvDelete;
        @BindView(R.id.iv_image)
        ImageView imageView;

        public AttachmentRecycleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove((Integer) v.getTag());
                }
            });
        }
    }

    class AttachmentAddRecycleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_add)
        View tvAdd;

        public AttachmentAddRecycleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAttachmentListener != null) {
                        onAttachmentListener.addFile();
                    }
                }
            });
        }
    }

    public static interface OnAttachmentListener{
        public void addFile();
    }
}
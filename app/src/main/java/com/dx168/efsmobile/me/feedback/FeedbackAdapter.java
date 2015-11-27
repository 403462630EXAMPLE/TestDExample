package com.dx168.efsmobile.me.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidao.efsmobile.R;
import com.baidao.tools.DateUtil;
import com.umeng.fb.model.Reply;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Bruce on 12/5/14.
 */
public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "ChatAdapter";

    private List<Reply> replies = new ArrayList<>();
    private ImageView playingVoiceImageView;
    private Context context;
    private OnImageChatItemClickListener onImageChatItemClickListener;

    public interface OnImageChatItemClickListener {
        void onImageClick(String url);
    }

    public FeedbackAdapter(Context context) {
        this.context = context;
    }

    public void setOnImageChatItemClickListener(OnImageChatItemClickListener onImageChatItemClickListener) {
        this.onImageChatItemClickListener = onImageChatItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (replies.get(position).type.equals(Reply.TYPE_DEV_REPLY)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            return new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_list_left_item, viewGroup, false));
        } else {
            return new TextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedback_list_right_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Reply reply = replies.get(position);
        bindTextView(viewHolder, reply);
    }

    private void bindTextView(RecyclerView.ViewHolder viewHolder, Reply reply) {
        final TextViewHolder textViewHolder = (TextViewHolder) viewHolder;
        textViewHolder.bind(reply);
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public void append(Reply reply) {
        this.replies.add(reply);
        this.notifyItemInserted(replies.size());
    }

    public void append(List<Reply> replies) {
        List<Reply> newReplies = replies;
        this.replies.addAll(newReplies);
        this.notifyItemRangeInserted(this.replies.size() - newReplies.size(), newReplies.size());
    }

    public void reset(List<Reply> replies) {
        this.replies.clear();
        this.replies.addAll(replies);
        this.notifyDataSetChanged();
    }

    private void update(int position, Reply reply) {
        replies.set(position, reply);
        this.notifyItemChanged(position);
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {

        private static final String DATE_TIME_PATTERN = "YYYY-MM-dd HH:mm";

        @InjectView(R.id.tv_content)
        TextView content;
        @InjectView(R.id.timestamp)
        TextView timestamp;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(Reply reply) {
            content.setText(reply.content);
            timestamp.setText(DateUtil.format(reply.created_at, DATE_TIME_PATTERN));

        }
    }
}

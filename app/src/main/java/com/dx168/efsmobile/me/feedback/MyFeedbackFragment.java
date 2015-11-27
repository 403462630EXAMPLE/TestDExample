package com.dx168.efsmobile.me.feedback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.baidao.efsmobile.R;
import com.dx168.efsmobile.application.BaseFragment;
import com.dx168.efsmobile.widgets.KeyboardDetectorRelativeLayout;
import com.baidao.tools.NetworkUtil;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chengxin on 3/18/15.
 */
public class MyFeedbackFragment extends BaseFragment {

    @InjectView(R.id.et_message)
    EditText editor;
    @InjectView(R.id.reply_list)
    RecyclerView replyList;
    @InjectView(R.id.content_container)
    KeyboardDetectorRelativeLayout contentContainer;

    private FeedbackAgent feedbackAgent;
    private Conversation conversation;
    private FeedbackAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
            replyList.scrollToPosition(adapter.getItemCount() - 1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.inject(this, view);

        feedbackAgent = new FeedbackAgent(getActivity());
        feedbackAgent.openFeedbackPush();

        conversation = feedbackAgent.getDefaultConversation();
        adapter = new FeedbackAdapter(getActivity());
        adapter.reset(conversation.getReplyList());

        replyList.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        replyList.setAdapter(adapter);
        replyList.scrollToPosition(adapter.getItemCount() - 1);

        contentContainer.setOnSoftKeyboardListener(new KeyboardDetectorRelativeLayout.OnSoftKeyboardListener() {
            @Override
            public void onShown() {
                scrollToBottom();
            }

            @Override
            public void onHidden() {

            }

            @Override
            public void onMeasureFinished() {

            }
        });

        sync();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sync();
                handler.postDelayed(this, 5000);
            }
        };

        handler.postDelayed(runnable, 5000);

        return view;
    }

    private void scrollToBottom() {
        replyList.smoothScrollToPosition(adapter.getItemCount());
    }

    @OnClick(R.id.btn_send)
    public void onSendClick(View view) {
        String content = editor.getText().toString();
        editor.getEditableText().clear();

        if (!NetworkUtil.isNetworkConnected(getActivity())) {
            Toast.makeText(getActivity(), "无法连接网络,请检查当前网络设置", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.isEmpty(content)) {
            conversation.addUserReply(content);
            handler.sendMessage(Message.obtain());
            sync();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        updateUserInfo();
    }

    private void updateUserInfo() {
        UserInfo info = feedbackAgent.getUserInfo();
        if (info == null)
            info = new UserInfo();
        Map<String, String> contact = info.getContact();
        if (contact == null)
            contact = new HashMap<String, String>();

        info.setContact(contact);
        feedbackAgent.setUserInfo(info);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = feedbackAgent.updateUserInfo();
                Log.d("update user info ====>", "" + result);
            }
        }).start();
    }

    private void sync() {
        conversation.sync(new SyncListener() {
            @Override
            public void onReceiveDevReply(List<Reply> replies) {
                if (replies == null || replies.isEmpty()) {
                    return;
                }
                adapter.append(replies);
                handler.sendMessage(Message.obtain());
            }

            @Override
            public void onSendUserReply(List<Reply> replies) {
                if (replies == null || replies.isEmpty()) {
                    return;
                }
                adapter.append(replies);
                handler.sendMessage(Message.obtain());
            }
        });
    }

}

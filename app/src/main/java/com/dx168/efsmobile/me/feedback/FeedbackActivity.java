package com.dx168.efsmobile.me.feedback;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.baidao.efsmobile.R;
import com.dx168.efsmobile.application.BaseActivity;
import com.google.gson.Gson;

import butterknife.InjectView;

/**
 * Created by chengxin on 3/18/15.
 */
public class FeedbackActivity extends BaseActivity {
    private static final String TAG = "FeedbackActivity";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        pushFragment(new MyFeedbackFragment());

    }

    @Override
    protected void onNewIntent(android.content.Intent intent) {
        Log.d(TAG, "intent: " + new Gson().toJson(intent));
    }

    @Override
    protected void onInitToolBar(Toolbar toolbar) {
        setTitle("建议反馈");
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
    }
}

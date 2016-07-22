package cn.hdmoney.hdy.fragment.phoneSetting;

import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.PhoneActivity;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class StepOneFragment extends BaseFragment {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_update_phone)
    TextView tvUpdatePhone;

    @Override
    protected int getContentView() {
        return R.layout.fragment_phone_step_one;
    }

    @Override
    protected void initViews() {
        super.initViews();
        titleBar.setLeftAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        tvPhone.setText("186****9196");
    }

    @OnClick(R.id.tv_update_phone)
    public void onClick(View view) {
        EventBus.getDefault().post(new PhoneActivity.PhoneSettingStepEvent(2));
    }
}

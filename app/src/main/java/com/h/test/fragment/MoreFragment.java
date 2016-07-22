package cn.hdmoney.hdy.fragment;

import android.content.Intent;
import android.view.View;

import com.liuguangqiang.framework.utils.AppUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.FeedbackActivity;
import cn.hdmoney.hdy.act.PictureActivity;
import cn.hdmoney.hdy.act.UserInfoActivity;
import cn.hdmoney.hdy.act.WebViewActivity;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.checkUpdate.UpdateManager;
import cn.hdmoney.hdy.dialog.AppDialog;
import cn.hdmoney.hdy.dialog.AppDialog.DialogActionListener;
import cn.hdmoney.hdy.utils.IntentUtils;
import cn.hdmoney.hdy.view.NavigationTabView;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class MoreFragment extends BaseFragment implements DialogActionListener{
    @BindView(R.id.nt_telephone)
    NavigationTabView ntTelephone;
    @BindView(R.id.nt_check_update)
    NavigationTabView ntCheckUpdate;


    private AppDialog appDialog;

    @Override
    protected int getContentView() {
        return R.layout.fragment_more;
    }

    @Override
    protected boolean isCustomStautsBar() {
        return true;
    }

    @Override
    protected void initViews() {
        super.initViews();
        ntTelephone.setTvRight("400-962-0400");
        ntCheckUpdate.setTvRight("ver 1.0");
        appDialog = new AppDialog(getActivity());
        appDialog.setDialogActionListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (appDialog != null) {
            appDialog.dismiss();
        }
    }

    @OnClick({R.id.nt_company_introduce, R.id.nt_common_question, R.id.nt_disclaimer, R.id.nt_telephone, R.id.nt_feedback, R.id.nt_check_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nt_company_introduce:
//                gotoCompanyIntroduce();
                Intent intent1 = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent1);
                break;
            case R.id.nt_common_question:
                gotoCompanyIntroduce();
                break;
            case R.id.nt_disclaimer:
//                gotoCompanyIntroduce();
                Intent intent = new Intent(getActivity(), PictureActivity.class);
                startActivity(intent);
                break;
            case R.id.nt_telephone:
                appDialog.setFlag(AppDialog.FLAG_MAKE_CALL);
                appDialog.show();
                break;
            case R.id.nt_feedback:
                IntentUtils.setIntent(getActivity(), FeedbackActivity.class);
                break;
            case R.id.nt_check_update:
                new UpdateManager(getActivity()).checkUpdate();
                break;

        }
    }

    private void gotoCompanyIntroduce() {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("url", "http://www.baidu.com");
        startActivity(intent);
    }

    @Override
    public void onLeftAction(int flag) {
        if (appDialog != null) {
            com.liuguangqiang.framework.utils.IntentUtils.call(getActivity(), "400-962-0400");
            appDialog.dismiss();
        }
    }

    @Override
    public void onRightAction(int flag) {
        if (appDialog != null) {
            appDialog.dismiss();
        }
    }
}

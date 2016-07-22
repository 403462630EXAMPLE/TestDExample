package cn.hdmoney.hdy.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.liuguangqiang.android.mvp.Presenter;
import com.liuguangqiang.framework.utils.PreferencesUtils;
import com.liuguangqiang.framework.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.base.BaseActivity;
import cn.hdmoney.hdy.utils.MyCommontUtils;
import cn.hdmoney.hdy.view.AbstractSpinerAdapter;
import cn.hdmoney.hdy.view.CustemObject;
import cn.hdmoney.hdy.view.CustemSpinerAdapter;
import cn.hdmoney.hdy.view.SpinerPopWindow;
import cn.hdmoney.hdy.view.TitleBar;

/**
 * Created by Administrator on 2016/6/6.
 */
public class BankCardActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.et_bankcard)
    EditText etBankcard;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.down_arrow)
    ImageView downArrow;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.identify)
    Button identify;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    private List<CustemObject> nameList = new ArrayList<CustemObject>();
    private AbstractSpinerAdapter mAdapter;
    private SpinerPopWindow mSpinerPopWindow;

    @Override
    public Presenter setPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        titleBar.setLeftAction(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String[] names = getResources().getStringArray(R.array.main_menu_titles);
        for (int i = 0; i < names.length; i++) {
            CustemObject object = new CustemObject();
            object.data = names[i];
            nameList.add(object);
        }


        mAdapter = new CustemSpinerAdapter(this);
        mAdapter.refreshData(nameList, 0);

        mSpinerPopWindow = new SpinerPopWindow(this);
        mSpinerPopWindow.setAdatper(mAdapter);
        mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int pos) {
                setHero(pos);
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContentView() {
        return R.layout.act_bankcard_layout;
    }

    @Override
    public void onAttachedUi() {

    }


    @OnClick({R.id.down_arrow, R.id.identify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.down_arrow:
//                showSpinWindow();
                startActivity(new Intent(BankCardActivity.this,BankListActivity.class));
                break;
            case R.id.identify:
                savaDraft();
                startActivity(new Intent(BankCardActivity.this, BankDetailActivity.class));
                break;
        }
    }

    private void showSpinWindow() {
        mSpinerPopWindow.setWidth(etBank.getWidth());
        mSpinerPopWindow.showAsDropDown(etBank);
    }

    private void setHero(int pos) {
        if (pos >= 0 && pos <= nameList.size()) {
            CustemObject value = nameList.get(pos);

            etBank.setText(value.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        savaDraft();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initDraft();
    }

    public void savaDraft() {
        String name = etName.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(name)) {
            MyCommontUtils.makeToast(context, R.string.noname);
        }
        String idcard = etIdcard.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(idcard)) {
            MyCommontUtils.makeToast(context, R.string.noidcard);
        }

        String card = etBankcard.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(card)) {
            MyCommontUtils.makeToast(context, R.string.nocard);
        }
        String bank = etBank.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(card)) {
            MyCommontUtils.makeToast(context, R.string.nobank);
        }
        String phone = etPhone.getText().toString().trim();
        if (StringUtils.isEmptyOrNull(phone)) {
            MyCommontUtils.makeToast(context, R.string.nophone);
        }

        PreferencesUtils.putString(context, "bankcard", "name", name);
        PreferencesUtils.putString(context, "bankcard", "idcard", idcard);
        PreferencesUtils.putString(context, "bankcard", "card", card);
        PreferencesUtils.putString(context, "bankcard", "bank", bank);
        PreferencesUtils.putString(context, "bankcard", "phone", phone);
    }

    public void initDraft() {
        etName.setText(PreferencesUtils.getString(context, "bankcard", "name"));
        etIdcard.setText(PreferencesUtils.getString(context, "bankcard", "idcard"));
        etBankcard.setText(PreferencesUtils.getString(context, "bankcard", "card"));
        etBank.setText(PreferencesUtils.getString(context, "bankcard", "bank"));
        etPhone.setText(PreferencesUtils.getString(context, "bankcard", "phone"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

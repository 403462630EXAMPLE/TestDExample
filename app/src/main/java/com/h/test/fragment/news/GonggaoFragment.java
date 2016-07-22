package cn.hdmoney.hdy.fragment.news;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuguangqiang.android.mvp.Presenter;

import java.util.ArrayList;

import butterknife.BindView;
import cn.hdmoney.hdy.Entity.GongGao;
import cn.hdmoney.hdy.R;
import cn.hdmoney.hdy.act.WebViewActivity;
import cn.hdmoney.hdy.adapter.base.BaseAdapterHelper;
import cn.hdmoney.hdy.adapter.base.QuickAdapter;
import cn.hdmoney.hdy.base.BaseFragment;
import cn.hdmoney.hdy.utils.IntentUtils;

/**
 * Created by Administrator on 2016/6/17.
 */
public class GonggaoFragment extends BaseFragment {
    @BindView(R.id.listView)
    ListView listView;
    private QuickAdapter<GongGao> mAdapter;
    private PopupWindow popupWindow;

    @Override
    protected int getContentView() {
        return R.layout.frg_gonggao_layout;
    }

    @Override
    public Presenter setPresenter() {
        return super.setPresenter();
    }

    @Override
    protected void initViews() {

        final ArrayList<GongGao> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            GongGao gongGao = new GongGao();
            gongGao.title = "我是一条公告";
            gongGao.time = "2016-0512";
            gongGao.content = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容dlist.add";
            list.add(gongGao);
        }

        listView.setAdapter(mAdapter = new QuickAdapter<GongGao>(getContext(), R.layout.frg_gonggao_item, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, GongGao item) {
                helper.setText(R.id.title, item.title);
                helper.setText(R.id.time, item.time);
                helper.setText(R.id.content, item.content);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtils.setIntent(getActivity(), WebViewActivity.class, "url", "http://www.baidu.com");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                View popview = LayoutInflater.from(getContext()).inflate(R.layout.fra_popwindow_layout, null);
                popupWindow = new PopupWindow(popview, 182, 135);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                int xPos = windowManager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
                popupWindow.showAsDropDown(view, xPos, -640);

                TextView textView = (TextView) popview.findViewById(R.id.delete);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        list.remove(position);
                        mAdapter.remove(position);
                        mAdapter.notifyDataSetChanged();

                    }
                });
                return true;
            }
        });


    }


}

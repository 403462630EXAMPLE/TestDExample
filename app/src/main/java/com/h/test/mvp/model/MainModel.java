package cn.hdmoney.hdy.mvp.model;

/**
 * Created by Administrator on 2016/5/24.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.hdmoney.hdy.Entity.MainMenuItem;
import cn.hdmoney.hdy.R;

public class MainModel {
    private MainMenuItem item;

    @Inject
    public MainModel() {
    }

    public List<MainMenuItem> getMenu(Context context) {
//
//        OkUtils.post(ApiUtils.URL_BASE_HDY, JsonUtils.getGson(new Version("1435816755986", 1, "1")), new BeanCallback<Result<UpdateApp>>() {
//
//
//            @Override
//            public void onError(Call call, Exception e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Result<UpdateApp> response) {
////                Logs.i(response.getResult()+response.getResultCode()+response.getResultDesc());
//            }
//        });

//        OkHttpClientManager.getAsyn(WAPIUtil.BASE_URL+"/index.php?app=api&mod=BBMusic&act=getMusicAlbumList",
//                new OkHttpClientManager.ResultCallback<AlbumResponse>(){
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(AlbumResponse response) {
//                        albumResponse = response;
//                        listView.setAdapter(mAdapter = new QuickAdapter<AlbumResponse.DataBean>(context,R.layout.activity_stage_listitem, response.getData()) {
//
//                            @Override
//                            protected void convert(BaseAdapterHelper helper, AlbumResponse.DataBean item) {
//                                helper.setText(R.id.tv_stage, item.getTitle());
//                                helper.setImageUrl(R.id.iv_stage, item.getImage());
//                            }
//                        });
//
//                    }
//                });
        String[] tiles = context.getResources().getStringArray(R.array.main_menu_titles);
        TypedArray icons = context.getResources().obtainTypedArray(R.array.main_menu_icon_n);
        TypedArray iconsSeleted = context.getResources().obtainTypedArray(R.array.main_menu_icon_s);
        List<MainMenuItem> list = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            item = new MainMenuItem();
            item.title = tiles[i];
            item.icon = icons.getResourceId(i, 0);
            item.iconSelected = iconsSeleted.getResourceId(i, 0);
            list.add(item);
        }
        list.get(0).selected = true;
        return  list;

    }
//检查更新
    public void checkUpdate(final Activity context) {
//        OkHttpClientManager.getAsyn(ApiUtils.URL_BASE_HDY+"/index.php?app=api&mod=BBMusic&act=getMusicAlbumList",
//                new OkHttpClientManager.ResultCallback<UpdateResponse>(){
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onResponse(UpdateResponse response) {
//                        PreferencesUtils.putInt(context,"version","versioncode",response.code);
//                        int code = PreferencesUtils.getInt(context, "version", "versioncode", 0);
//                        if (response.code > code) {
//                            MyCommontUtils.makeToast(context,"更新");
////                            去更新
//                        }
//                    }
//                });

    }


}

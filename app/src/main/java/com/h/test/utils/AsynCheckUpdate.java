//package cn.hdmoney.hdy.utils;
//
//import android.app.Activity;
//import android.content.Context;
//
//import com.liuguangqiang.framework.utils.PreferencesUtils;
//import com.squareup.okhttp.Request;
//
//import cn.hdmoney.hdy.R;
//
///**
// * Created by Administrator on 2016/6/3.
// */
//public class AsynCheckUpdate {
//    private final Activity mAct;
//
//    public AsynCheckUpdate(Activity mAct) {
//        this.mAct = mAct;
//    }
//
//    private void check(final Context context) {
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
//                            showUpdateDialog(response);
//                        }
//                    }
//                });
//    }
//
//    private void showUpdateDialog(UpdateResponse response) {
//        String title = mAct.getString(R.string.update_title);
////        String message = String.format(mAct.getString(R.string.update_message), response.code);
//        String sure = mAct.getString(R.string.update_sure);
//        String cancel = mAct.getString(R.string.update_cancel);
////        if (logoutDialog == null) {
////            logoutDialog = new MyDialog(mAct, title);
////            logoutDialog.setMessage(message);
////            logoutDialog.setCancelable(false);
////            logoutDialog.setPositiveButton(sure, cancel, new OnPositiveClickListener() {
////                @Override
////                public void onClick() {
////                    downloadLatest(mAct.getApplicationContext(), result.data.downurl,
////                            result.data.vercode);
////                }
////            });
////        }
////        logoutDialog.show();
//    }
//}

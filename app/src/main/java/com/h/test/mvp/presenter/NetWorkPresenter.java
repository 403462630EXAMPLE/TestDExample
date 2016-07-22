package cn.hdmoney.hdy.mvp.presenter;

import android.content.Context;
import android.widget.Toast;

import cn.hdmoney.hdy.mvp.model.INetConnect;
import cn.hdmoney.hdy.mvp.model.ImplementNetConnect;
import cn.hdmoney.hdy.mvp.ui.WarnToast;

/**
 * Created by Administrator on 2016/5/18.
 */
public class NetWorkPresenter  {
    private final Context context;
    INetConnect iNetConnect;

    public NetWorkPresenter(Context context) {
        this.context = context;
        //判断网络链接的业务逻辑
        iNetConnect = new ImplementNetConnect();
    }


    public void judeNet() {
        if (!iNetConnect.isNetWorkOk()) {
            jumpNext();
        } else {
//            ui的实现
            new WarnToast() {
                @Override
                public void toast() {
                    Toast.makeText(context, "网络已断开", Toast.LENGTH_LONG).show();
                }
            };
        }
    }
    public void jumpNext(){
//    跳转到下一个界面
        Toast.makeText(context,"跳转",Toast.LENGTH_LONG).show();
    }
}

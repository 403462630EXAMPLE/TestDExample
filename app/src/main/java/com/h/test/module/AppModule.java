package cn.hdmoney.hdy.module;

import cn.hdmoney.hdy.app.DebugApplication;
import cn.hdmoney.hdy.app.MyApplication;
import dagger.Module;

/**
 * Created by Administrator on 2016/5/24.
 */
@Module(
        injects = {
                MyApplication.class,
                DebugApplication.class
        }
)
public class AppModule {

}

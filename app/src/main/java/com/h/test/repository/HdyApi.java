package cn.hdmoney.hdy.repository;

import android.support.annotation.NonNull;

import cn.hdmoney.hdy.Entity.Account;
import cn.hdmoney.hdy.Entity.AwardRecord;
import cn.hdmoney.hdy.Entity.Coupon;
import cn.hdmoney.hdy.Entity.InvestmentRecord;
import cn.hdmoney.hdy.Entity.OrderSuccessResult;
import cn.hdmoney.hdy.Entity.PaymentRecord;
import cn.hdmoney.hdy.Entity.Result;
import cn.hdmoney.hdy.Entity.UpdateApp;
import cn.hdmoney.hdy.Entity.VerifyCode;
import cn.hdmoney.hdy.params.BindBankCardParam;
import cn.hdmoney.hdy.params.TradeSettingParam;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public interface HdyApi {
    
}

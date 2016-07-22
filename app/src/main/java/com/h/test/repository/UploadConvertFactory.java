package cn.hdmoney.hdy.repository;

import android.util.Log;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import cn.hdmoney.hdy.utils.DESWrapper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class UploadConvertFactory extends Converter.Factory {

    public static UploadConvertFactory create() {
        return new UploadConvertFactory();
    }

    private UploadConvertFactory() {
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == UploadParam.class) {
            return new UploadRequestBodyConverter();
        }
        return null;
    }

    final static class UploadRequestBodyConverter implements Converter<UploadParam, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("multipart/form-data");

        UploadRequestBodyConverter() {}

        @Override public RequestBody convert(UploadParam value) throws IOException {
            String json = value.toJson();
            String content = DESWrapper.encryptDES(json, "12345678");
            Log.i("UploadConvertFactory", "content=" + content);
            return RequestBody.create(MEDIA_TYPE, content);
        }
    }

}

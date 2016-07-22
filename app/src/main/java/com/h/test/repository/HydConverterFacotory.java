package cn.hdmoney.hdy.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import cn.hdmoney.hdy.utils.DESWrapper;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/6/21 0021.
 */
public final class HydConverterFacotory extends Converter.Factory {
    private static final String TAG = "HydConverterFacotory";
    private Gson gson;

    public static HydConverterFacotory create() {
        return create(new Gson());
    }

    public static HydConverterFacotory create(Gson gson) {
        return new HydConverterFacotory(gson);
    }

    private HydConverterFacotory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter(gson, adapter);
    }

    final static class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override public T convert(ResponseBody value) throws IOException {
            String body = value.string();
            body = DESWrapper.decryptDES(body, "12345678");
            Log.i(TAG, body);
            JsonReader jsonReader = gson.newJsonReader(new StringReader(body));
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }
}

package com.ytx.library.provider.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedOutput;

/**
 * Created by hexi on 15/3/20.
 */
public class MyGsonConverter extends GsonConverter {
    private final Gson gson;
    private String charset;

    public MyGsonConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    public MyGsonConverter(Gson gson, String charset) {
        super(gson, charset);
        this.gson = gson;
        this.charset = charset;
    }

    @Override
    public TypedOutput toBody(Object object) {
        if (object instanceof String) {
            try {
                return new JsonTypedOutput(((String)object).getBytes(charset), charset);
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        } else {
            return super.toBody(object);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {
        private final byte[] jsonBytes;
        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override public String fileName() {
            return null;
        }

        @Override public String mimeType() {
            return mimeType;
        }

        @Override public long length() {
            return jsonBytes.length;
        }

        @Override public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}

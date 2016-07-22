package cn.hdmoney.hdy.utils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class MultipartUtils {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static MultipartBody.Part getPart(String partName, String filePath) {
        File file = new File(filePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }
}

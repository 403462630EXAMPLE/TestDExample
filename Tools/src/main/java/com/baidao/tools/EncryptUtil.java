package com.baidao.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by chengxin on 3/2/15.
 */
public class EncryptUtil {
    private static final String TAG = EncryptUtil.class.getSimpleName();

    private static final String ALGORITHM = "SHA-1";
    public static String sha1(String raw)
    {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance(ALGORITHM);
            crypt.reset();
            crypt.update(raw.getBytes("UTF-8"));
            sha1 = HexUtil.byteToHex(crypt.digest());
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }


    public static String md5Hex(String source) {
        try {
            MessageDigest crypt = MessageDigest.getInstance("MD5");
            crypt.reset();
            crypt.update(source.getBytes("UTF-8"));
            return HexUtil.byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

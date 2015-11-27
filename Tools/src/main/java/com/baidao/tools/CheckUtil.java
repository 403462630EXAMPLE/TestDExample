package com.baidao.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chengxin on 3/2/15.
 */
public class CheckUtil {
    private static final String TAG = CheckUtil.class.getSimpleName();

    private static final Pattern VALID_PHONE_NUMBER_PATTERN = Pattern.compile("^1[34578]\\d{9}");
    public static boolean isMobileNum(String mobiles) {
        Matcher m = VALID_PHONE_NUMBER_PATTERN.matcher(mobiles);
        return m.matches();
    }

    private static final Pattern VALID_EMAIL_PATTERN = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        Matcher m = VALID_EMAIL_PATTERN.matcher(email);
        return m.matches();
    }
}

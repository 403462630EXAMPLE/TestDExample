package cn.hdmoney.hdy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/1 0001.
 */
public class RegexUtils {

    public static boolean isUsername(String username) {
        Pattern p = Pattern.compile("^[a-zA-Z_]\\w{5,15}$");
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^1\\d{10}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isTradePassword(String password) {
        Pattern p = Pattern.compile("^\\w+$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isIdCard(String idCard) {
        Pattern p = Pattern.compile("^[1-9]\\d{13,16}[a-zA-Z0-9]{1}$");
        Matcher m = p.matcher(idCard);
        return m.matches();
    }

    public static boolean isBankId(String bankId) {
        Pattern p = Pattern.compile("^\\d{16,}$");
        Matcher m = p.matcher(bankId);
        return m.matches();
    }

    public static boolean isLoginPassword(String password) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_]{6,20}$");
        Matcher m = p.matcher(password);
        int flag = 0;
        if (m.matches()) {
            flag += Pattern.compile("[0-9]").matcher(password).find() ? 1 : 0;
            flag += Pattern.compile("[A-Z]").matcher(password).find() ? 1 : 0;
            flag += Pattern.compile("[a-z]").matcher(password).find() ? 1 : 0;
            flag += Pattern.compile("[_]").matcher(password).find() ? 1 : 0;
        }
        return flag >= 2;
    }
}

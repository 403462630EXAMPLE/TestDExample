/**
 * File Name：HexByteConvertUtil.java
 * File Describe：Technology Platform 进制/字节转换
 * Creation Date：2015年12月3日13:06:14
 * @author ZhuoLi
 * @version 2.0.0
 * Copyright (c) 2015 Changsha Lijiacheng Information Technology Co. Ltd.
 * Website： http://www.caocaopinche.com/
 */
package cn.hdmoney.hdy.utils;
/**
 * 提供十六进制转byte数组之间的相互转换
 *	@author ZhuoLi
 *	@version 2.0.0
 */
public class HexByteConvertUtil {
	
	/**
	 * 十六进制转byte数组
	 * @param strhex
	 * @return
	 */
    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int len = strhex.length();
        if (len % 2 == 1) {
            return null;
        }
        byte[] bytes = new byte[len / 2];
        for (int i = 0; i != len / 2; i++) {
        	bytes[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return bytes;
    }
    
    /**
     * byte[]转十六进制
     * @param b
     * @return
     */
    public static String byte2hex(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < bytes.length; n++) {
            stmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (stmp.length() == 1) {
                strBuf.append("0" + stmp);
            } else {
            	strBuf.append(stmp);
            }
        }
        return strBuf.toString().toUpperCase();
    }

}

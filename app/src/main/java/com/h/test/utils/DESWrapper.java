/**
 * File Name：DESWrapper.java
 * File Describe：DES对称加密/解密算法
 * Creation Date：2015年12月3日13:06:14
 * @author ZhuoLi
 * @version 2.0.0
 * Copyright (c) 2015 Changsha Lijiacheng Information Technology Co. Ltd.
 * Website： http://www.caocaopinche.com/
 */

package cn.hdmoney.hdy.utils;

import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 此类用于DES相关的加解密操作
 * 
 * @author ZhuoLi
 * @version 2.0.0
 */
public class DESWrapper {
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * DES加密
	 * 
	 * @param encryptString
	 *            加密内容，encryptKey 加密密码
	 * @return 加密后内容
	 */
	public static String encryptDES(String encryptString, String encryptKey) {
		try {
			Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("utf-8"), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptString.getBytes("utf-8"));
			return pattern.matcher(
					new String(Base64.encode(encryptedData, Base64.URL_SAFE)))
					.replaceAll("");
			// return new String(Base64.encode(encryptedData,Base64.URL_SAFE));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * DES解密
	 * 
	 * @param decryptString
	 *            解密内容，encryptKey 解密密码
	 * @return 解码后内容
	 */
	public static String decryptDES(String decryptString, String decryptKey) {
		try {
			byte[] byteMi = Base64.decode(decryptString, Base64.URL_SAFE);
			IvParameterSpec zeroIv = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("utf-8"), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			byte decryptedData[] = cipher.doFinal(byteMi);
			return new String(decryptedData,"utf-8");
		} catch (Exception e) {
			return "";
		}
	}

	public static void main(String[] args) {
		String a = "{\"result\":{\"updateDesc\":\"1.中奖记录列表显示修改.2投资记录修改,3日历修改\",\"updateUrl\":\"http://hdmoney.com/app/version/v0.0.2.apk\",\"versionName\":\"v0.0.2\",\"updateCode\":3,\"updateDate\":\"Tue Jun 14 14:09:57 CST 2016\"},\"resultCode\":\"000000\",\"resultDesc\":\"成功\"}";
		a = DESWrapper.encryptDES(a,"12345678");
		System.out.println(a);
		System.out.println(DESWrapper.decryptDES(a, "12345678"));
		System.out.println(DESWrapper.decryptDES("J5zL6kG1-GNclr7bGxsJ5Q1w9y2SSsIdX3Co2VDhsRnsX_l_SnDB37_1zZ6d2uCBvEXD0QewjQQDf5F7-6JCc6_P5dqb3o_GKAVeZCoHnCkk4S8xFZ6dUOHviTS_EAZKFivchgZtrZEfVGuqdX2MaYAT_B7UA1xoE8Jsu5Vh5vt2bbyOnyDHdBGQBmz-IEYUZfYWoKKe7ppowX53pf3ivnzsBMWvrTecFzsMj3Gh_9tpytIWv19hZ9vwTJIAx6t274oDy7h3-Q2pPkmds7FingAwryJDZuTO-AyIQ-eVHagPCI9MySX7Gq7kfX4H1SuJ2z-0idM-ZxN9ZNVDWK_wGg==", "12345678"));
	}
}

// end of the file
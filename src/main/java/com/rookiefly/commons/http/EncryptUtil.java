package com.rookiefly.commons.http;

import java.security.MessageDigest;

public class EncryptUtil {
	/**16进制字符数组*/
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
	/**
	 * 获取md5字符串串
	 * @param inputStr
	 * @return md5字符串
	 */
	public static String md5(String inputStr) {
		return encodeByMD5(inputStr);
	}
	/**
	 * 将字符串进行md5操作
	 * @param originString
	 * @return md5字符串
	 */
	private static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				byte[] results = md5.digest(originString.getBytes());
				String result = byteArrayToHexString(results);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 将byteArray转化为HexString
	 * @param b
	 * @return HexString
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}
	/**
	 * 将byte转化为HexString
	 * @param b
	 * @return HexString
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 *  将长网址转化为短链接
	 * @param url 长链接
	 * @return 短链接
	 */
	public static String shortUrl(String url) {
		return md5(url + System.currentTimeMillis()).substring(0, 8);
	}

}
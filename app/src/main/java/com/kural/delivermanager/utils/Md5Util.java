package com.kural.delivermanager.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

public class Md5Util {
	
	/***
	 * 路径 md5
	 * @param filePath 为空代表SDCard根目录
	 * 
	 */
	
	public static String getFilePathMd5(String filePath) {

		// 去掉'/'，数据库路径加密不带最开始的'/'
		if (filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}
		
		if (TextUtils.isEmpty(filePath)) {
			return "";
		}
				
		String[] s = filePath.split("/");
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length; i++) {
			if (!TextUtils.isEmpty(s[i])) {
				sb.append(getStringMd5(s[i].toLowerCase() + "ijinshan"));
			}
			if (i != s.length - 1)
				sb.append('+');
		}
		return sb.toString();
	}

	
	public static String getStringMd5(String plainText) {
			
		MessageDigest md = null;
		
		try {
			
			md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			
		}catch(Exception e){
			
			return null;
			
		}
		
		return encodeHex(md.digest());
		
	}

	public static String encodeHex(byte[] data) {

		if (data == null) {
			
			return null;
		}
		
		final String HEXES = "0123456789abcdef";
		int len = data.length;
		StringBuilder hex = new StringBuilder(len * 2);
		
		for(int i = 0; i < len; ++i) {
			
			hex.append(HEXES.charAt((data[i] & 0xF0) >>> 4));
			hex.append(HEXES.charAt((data[i] & 0x0F)));
		}
		
		return hex.toString();
	}
}


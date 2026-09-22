/*
 * 文件名：SecurityUtil.java
 * 版权：Copyright 2015-2015 Liebao Network Tech. Co. Ltd. All Rights Reserved. 
 * 描述： SecurityUtil.java
 * 修改人：yangxuebo
 * 修改时间：2015年7月28日
 * 修改内容：新增
 */
package com.ruoyi.system.outto.dto.yx7881.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 安全工具类。
 */
public class SecurityUtil {

	/**
	 *
	 * MD5加密。
	 *
	 * @param inStr
	 *            明文待加密串
	 * @return [加密后密文]
	 */
	public static  String encryptMD5(String inStr) {
		MessageDigest md5 = null;

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		byte[] md5Bytes = null;
		try {
			md5Bytes = md5.digest(inStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuffer hexValue = new StringBuffer(50);

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append('0');
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
}
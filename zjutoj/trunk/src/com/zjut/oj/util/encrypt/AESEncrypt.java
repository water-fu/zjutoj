/**
 * Tianque.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.zjut.oj.util.encrypt;

import com.zjut.oj.util.ApplicationException;

/**
 * 总体说明
 * 	URL参数加密
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: AESEncrypt.java,v 0.1 2015-3-30 下午3:41:54 Exp $
 */
public class AESEncrypt implements IEncrypt {

	/**
	 * 加密
	 */
	@Override
	public String encodePassword(String rawPass, Object salt) {
		try {
			return AES.enAes(rawPass, salt.toString());
		} catch (Exception e) {
			throw new ApplicationException("参数加密异常", e);
		}
	}

	/**
	 *
	 */
	@Deprecated
	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		return false;
	}

	/**
	 * 解密
	 */
	@Override
	public String decodePassword(String rawPass, Object salt) {
		try {
			return AES.deAes(rawPass, salt.toString());
		} catch (Exception e) {
			throw new ApplicationException("参数解密异常", e);
		}
	}


	public static void main(String[] args) {
		AESEncrypt aesEncrypt = new AESEncrypt();
		System.out.println(aesEncrypt.encodePassword("4", "zjutoj"));
		System.out.println(aesEncrypt.decodePassword("82DBE49892368943C2264B869BE0FEC7", "zjutoj"));
	}
}

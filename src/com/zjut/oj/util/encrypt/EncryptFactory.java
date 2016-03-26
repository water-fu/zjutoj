package com.zjut.oj.util.encrypt;

import com.zjut.oj.common.SystemConstantType;
import com.zjut.oj.util.ApplicationException;


/**
 * 加密工厂
 * @author
 *
 */
public class EncryptFactory {

	/**
	 * 根据摘要算法字符串返回加密类
	 * 支持MD5算法
	 * @param encryptName
	 * @return
	 * @throws
	 */
	public static IEncrypt getInstance(String encryptName) throws ApplicationException{
		IEncrypt iEncrypt = null;
		try {
			iEncrypt = (IEncrypt) Class.forName("com.zjut.oj.util.encrypt."+encryptName).newInstance();
		} catch (Exception e) {
			throw new ApplicationException("com.zjut.oj.util.encrypt."+encryptName+"类初始化异常", e);
		}

		return iEncrypt;
	}

	public static void main(String[] args) {
		try {
			String s = EncryptFactory.getInstance("MD5Encrypt").encodePassword("zjut123456", SystemConstantType.PASS_SALT);
			System.out.println(s);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

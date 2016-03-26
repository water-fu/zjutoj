package com.zjut.oj.util.encrypt;


/**
 * 总体说明
 * 	用户密码加密
 *
 * <p>具体说明</p>
 *
 * @author fushuijun
 * @version $Id: MD5Encrypt.java,v 0.1 2015-3-30 下午3:54:10 Exp $
 */
public class MD5Encrypt implements IEncrypt {

    public String encodePassword(String rawPass, Object salt) {
        String afterPass = salt.toString()+rawPass;
        return MD5.md5(afterPass);
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt){
        String afterPass = this.encodePassword(rawPass, salt);
        return encPass.equalsIgnoreCase(afterPass);
    }

    /**
     * @see com.zjut.oj.util.encrypt.IEncrypt#decodePassword(java.lang.String, java.lang.Object)
     */
    @Deprecated
    @Override
    public String decodePassword(String rawPass, Object salt) {
        // TODO Auto-generated method stub
        return null;
    }
}

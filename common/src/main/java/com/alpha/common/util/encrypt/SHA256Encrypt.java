package com.alpha.common.util.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA256加密算法
 * Created by kids on 2019/6/24.
 */
public class SHA256Encrypt {

    private static final Logger logger = LoggerFactory.getLogger(SHA256Encrypt.class);
    /**
     * SHA256J加密算法
     */
    public static String getEncryptString(String strSrc) {
        String encodeStr = "";
        byte[] bt = strSrc.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bt);
            encodeStr = bytes2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA256加密出错:{}", e);
        }
        return encodeStr;
    }

    public static String bytes2Hex(byte[] bts) {
        StringBuffer sb = new StringBuffer();
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //String password = "1";
        //System.out.println("SHA256加密:{} " + SHA256Encrypt.getEncryptString(password));
    }
}

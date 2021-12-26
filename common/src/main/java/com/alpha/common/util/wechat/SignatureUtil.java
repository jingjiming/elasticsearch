package com.alpha.common.util.wechat;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;

/**
 * Created by jiming.jing on 2020/4/16.
 */
public class SignatureUtil {

    /**
     * 数据真实性验证
     */
    public static boolean checkSignature(HttpServletRequest request) {
        String signature = request.getParameter("signature"); // 微信加密签名
        String timestamp = request.getParameter("timestamp"); // 时间戳
        String nonce = request.getParameter("nonce"); // 随机数

        // 通过检验signature对请求进行校验
        return check(signature, timestamp, nonce);
    }

    /**
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return 若确认匹配，返回true，否则返回false
     */
    public static boolean check(String signature, String timestamp, String nonce) {

        String[] arr = new String[]{"wx", timestamp, nonce};

        // 1.将token、timestamp、nonce（或msg_encrypt）三个参数进行字典序排序得字符串string
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
        }
        String hashcode = null;  //sha1 加密后字符串

        // 2.将string进行sha1加密得hashcode
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(sb.toString().getBytes());
            hashcode = bytesToStr(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3.hashcode == signature
        return hashcode != null ? hashcode.equals(signature.toUpperCase(Locale.ENGLISH)) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray 字节数组
     * @return 字符串
     */
    private static String bytesToStr(byte[] byteArray) {
        String str = "";
        for (int i = 0; i < byteArray.length; i++) {
            str += byteToHexStr(byteArray[i]);
        }
        return str;
    }

    /**
     * 将字节转换成为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];

        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];

        String str = new String(tempArr);
        return str;
    }
}

package com.alpha.common.util.encrypt;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * druid连接池数据库密码加密
 * Created by yangzhipeng on 2018/10/10.
 */
public class DruidEncryptUtil {

    public static void main(String[] args) throws Exception {
        // 密码明文
        String password = "Mwr_exam2_0501";

        System.out.println("密码[ " + password + " ]的加密信息如下：\n");

        String[] keyPair = ConfigTools.genKeyPair(512);
        // 私钥
        String privateKey = keyPair[0];
        // 公钥
        String publicKey = keyPair[1];
        // 用私钥加密后的密文
        // 明文密码+私钥(privateKey)加密=加密密码
        String cipherText = ConfigTools.encrypt(privateKey, password);

        System.out.println("privateKey:" + privateKey);
        System.out.println("publicKey:" + publicKey);
        System.out.println("cipherText:" + cipherText);

        // 加密密码+公钥(publicKey)解密=明文密码
        //String decryptPassword = ConfigTools.decrypt("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANDIIRq+jlEEOpxzwILjshA3jKU5IUlWhY8O4QTLSzqn4Sk4AkTC3/i1Z83Piq0NchI1vYIHcLfM90dwZJkteVECAwEAAQ==", "XpTewIvWSxl20rx/TL0jhgIR5h8EsLvv9jDPT30sPeXyr3T4uaiQELDyu6b7AQmki/3kc5YJpB0q3PG9k1mXEw==");
        //System.out.println("decryptPassword:" + decryptPassword);
    }
}

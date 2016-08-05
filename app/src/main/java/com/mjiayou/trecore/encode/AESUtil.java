package com.mjiayou.trecore.encode;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    /**
     * 密钥算法 java6支持56位密钥，bouncycastle支持64位
     */
    public static final String KEY_ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";
    public static final String KEY = "SoccerApp2015@BeiBanQiu#01234567"; // key长度为[128bit(16byte),192bit(24byte)，256bit(32byte)]中的一个
    public static final SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), KEY_ALGORITHM);

    /**
     * 加密数据
     *
     * @param str
     * @return String 加密后的数据
     */
    public static String encrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 执行操作
            byte[] data = cipher.doFinal(str.getBytes());
            data = Base64.encode(data, Base64.DEFAULT);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密数据
     *
     * @param str
     * @return String 解密后的数据
     */
    public static String decrypt(String str) {
        try {
            byte[] data = Base64.decode(str, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 执行操作
            return new String(cipher.doFinal(data), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void test() {
//        String str = "6B6620D9-6E7E-4A20-BB45-4913F1102809";
//        System.out.println("原始串:" + str);
//        String enc = AESUtil.encrypt(str);
//        System.out.println("加密后:" + enc);
//        String dec = AESUtil.decrypt(enc);
//        System.out.println("解密后:" + dec);
//        System.out.println("加密后对比：E7fboXhvis4ygUdatvVu43VHJQxLjBkDwxa8PZlUkbZJelqICJsYUGUErvoDixQQ");
//        System.out.println("加密后截取对比：E7fboXhvis4ygUdatvVu43VHJQxLjBkDwxa8PZlUkbZJelqICJsYUGUErvoDixQQ".substring(42, 64));
    }

}
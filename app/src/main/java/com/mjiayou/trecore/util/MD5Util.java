package com.mjiayou.trecore.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Util {

    private static final String TAG = "MD5Util";

    public static final String MD5 = "MD5";
    public static final String SHA1 = "SHA1";

    // ******************************** CheckSumBuilder ********************************

    /**
     * 计算并获取md5值
     * <p/>
     * eg. treason258 -> f554ed1bf62eb36afaa30e4bcb1e096a
     */
    public static String md5(String value) {
        return encode(MD5, value);
    }

    public static String md5(byte[] value) {
        return encode(MD5, value);
    }

    /**
     * 计算并获取sha1值
     * <p/>
     * eg. treason258 -> ecbf2def90be403f8a766c3826f8c2a57ca82d5c
     */
    public static String sha1(String value) {
        return encode(SHA1, value);
    }

    public static String sha1(byte[] value) {
        return encode(SHA1, value);
    }

    /**
     * encode
     */
    private static String encode(String algorithm, byte[] value) {
        if (value == null) {
            return null;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value);
            byte[] arrayOfByte = messageDigest.digest();
            int length = arrayOfByte.length;

            StringBuilder hex = new StringBuilder(length * 2);
            char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            for (byte b : arrayOfByte) {
                hex.append(HEX_DIGITS[(b >> 4) & 0x0f]);
                hex.append(HEX_DIGITS[b & 0x0f]);
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encode(String algorithm, String value) {
        return encode(algorithm, value.getBytes());
    }

    // ******************************** md5 ********************************

    /**
     * md5
     * <p/>
     * eg. treason258 -> f554ed1bf62eb36afaa30e4bcb1e096a
     */
    public static String md5B(String value) {
        return encodeB(MD5, value);
    }

    /**
     * sha1
     * <p/>
     * eg. treason258 -> ecbf2def90be403f8a766c3826f8c2a57ca82d5c
     */
    public static String sha1B(String value) {
        return encodeB(SHA1, value);
    }

    /**
     * encodeB
     */
    private static String encodeB(String algorithm, String value) {
        if (value == null) {
            return null;
        }

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes("UTF-8"));
            byte[] arrayOfByte = messageDigest.digest();
            int length = arrayOfByte.length;

            StringBuilder hex = new StringBuilder(length * 2);
            for (byte b : arrayOfByte) {
                if ((b & 0xFF) < 0x10) {
                    hex.append("0");
                }
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
    }
}

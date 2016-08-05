package com.mjiayou.trecore.encode;

/**
 * Created by treason on 15-10-22.
 */
public class SignatureUtil {

    public static String getSignature(String requestId) {
        String signature = "";

        try {
            if (requestId != null && !requestId.equals("") && requestId.length() >= 6) {
                // 取requestId长度
                int length = requestId.length();
                // 取requestId前六位
                String start6 = requestId.substring(0, 6);
                // 取requestId后六位
                String end6 = requestId.substring(length - 6, length);
                // 组成新的字符串
                String startToEnd = start6 + end6;
                // 进行AES加密
                String afterAES = AESUtil.encrypt(startToEnd);
                // 截取加密后的第3位开始（包含）到第22位（包含）
                signature = afterAES.substring(2, 22);

//                System.out.println("length -> " + length);
//                System.out.println("start6 -> " + start6);
//                System.out.println("end6 -> " + end6);
//                System.out.println("startToEnd -> " + startToEnd);
//                System.out.println("afterAES -> " + afterAES);
//                System.out.println("signature -> " + signature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signature;
    }
}

package com.mjiayou.trecore.bean;

/**
 * Created by treason on 15-10-21.
 */
public class TCRequest {

    private String param1;
    private String param2;

//    字段	类型	是否必选	说明
//    requestId	字符串	是	请求时的
//    tokenId	字符串	否	用户登陆后的认证令牌
//    appVersion	字符串	是	终端版本号

//    为判断和验证每次请求的合法性，需要在HTTP请求的Headers中添加“Signature”的参数字段。
//    由requestId的值截取出前6位和后6位，组合成新的字符串，利用秘钥进行AES加密，
//    Bsae64之后截取从第3位开始（包含）到第22位（包含）的字符串作为“Signature”的值。
//    例如：
//    加密之后的Base64是：YAnwrzfEsvwCXfi6OYwqgQ==
//    红色部分即为Signature的值

    private String requestId;
    private String tokenId;
    private String appVersion;
    private String Signature;

    private String page_number; // 当前页数 0-首页 1-第二页......
    private String page_rows; // 每页显示条数

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPage_number() {
        return page_number;
    }

    public void setPage_number(String page_number) {
        this.page_number = page_number;
    }

    public String getPage_rows() {
        return page_rows;
    }

    public void setPage_rows(String page_rows) {
        this.page_rows = page_rows;
    }
}

package com.mjiayou.trecore.bean;

/**
 * Created by treason on 15-10-21.
 */
public class TCResponseHeader {

//    字段	类型	是否必选	说明
//    errorCode	字符串	是	业务处理的返回码，成功为0
//    errorReason	字符串	否	错误说明

    private String errorCode;
    private String errorReason;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}

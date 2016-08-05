package com.mjiayou.trecore.bean;

/**
 * Created by treason on 16/5/20.
 */
public class TCResponse {

    public static final String SUCCESS_CODE = "0";

    private TCResponseHeader header;

    public TCResponseHeader getHeader() {
        return header;
    }

    public void setHeader(TCResponseHeader header) {
        this.header = header;
    }
}

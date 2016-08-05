package com.mjiayou.trecore.bean;

/**
 * Created by treason on 16/5/20.
 */
public class TCRequest {

    private TCRequestHeader header;

    private TCRequestBody body;

    public TCRequestBody getBody() {
        return body;
    }

    public void setBody(TCRequestBody body) {
        this.body = body;
    }

    public TCRequestHeader getHeader() {
        return header;
    }

    public void setHeader(TCRequestHeader header) {
        this.header = header;
    }
}

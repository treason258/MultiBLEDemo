package com.mjiayou.trecore.bean.bundle;

/**
 * Created by treason on 16/5/20.
 */
public class TCRequestBundle {

    private TCRequestBundleHeader header;

    private TCRequestBundleBody body;

    public TCRequestBundleBody getBody() {
        return body;
    }

    public void setBody(TCRequestBundleBody body) {
        this.body = body;
    }

    public TCRequestBundleHeader getHeader() {
        return header;
    }

    public void setHeader(TCRequestBundleHeader header) {
        this.header = header;
    }
}

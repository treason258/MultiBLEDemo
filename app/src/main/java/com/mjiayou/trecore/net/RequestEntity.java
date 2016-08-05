package com.mjiayou.trecore.net;

import com.android.volley.Request.Method;
import com.mjiayou.trecore.widget.TCParams;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestEntity implements Serializable {

    private static final long serialVersionUID = -2329339308310833689L;

    private int mMethodCode;
    private String mUrl = "";
    private String mRequestBody = "";
    private JSONObject mJsonObject = new JSONObject();
    private Map<String, String> mHeaders = new HashMap<>();
    private Map<String, String> mParams = new HashMap<>();
    private Map<String, File> mFiles = new HashMap<>();

    /**
     * 构造函数
     */
    public RequestEntity(String url) {

        this.mMethodCode = Method.POST;
        this.mUrl = url;

        // 公共参数-header
        mHeaders.put("Accept-Encoding", "gzip");
        mHeaders.put("Accept", "application/json");
        mHeaders.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"); // application/json; charset=UTF-8
        mHeaders.put(TCParams.KEY_PLATFORM, TCParams.VALUE_PLATFORM);

        // 公共参数-params
        mParams.put(TCParams.KEY_TIME, String.valueOf(System.currentTimeMillis()));
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    // getter and setter

    public int getMethodCode() {
        return mMethodCode;
    }

    public void setMethodCode(int mMethodCode) {
        this.mMethodCode = mMethodCode;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getRequestBody() {
        return mRequestBody;
    }

    public void setRequestBody(String mRequestBody) {
        this.mRequestBody = mRequestBody;
    }

    public JSONObject getJsonObject() {
        return mJsonObject;
    }

    public void setJsonObject(JSONObject mJsonObject) {
        this.mJsonObject = mJsonObject;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    public void setParams(Map<String, String> mParams) {
        this.mParams = mParams;
    }

    public Map<String, File> getFiles() {
        return mFiles;
    }

    public void setFiles(Map<String, File> mFiles) {
        this.mFiles = mFiles;
    }


    // operation

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    public void addParam(String key, String value) {
        mParams.put(key, value);
    }

    public void addFile(String name, File file) {
        mFiles.put(name, file);
    }

    public void addParams(Map<String, String> params) {
        mParams.putAll(params);
    }


}
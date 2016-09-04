package com.mjiayou.trecore.net;

import com.android.volley.Request.Method;
import com.mjiayou.trecore.helper.Configs;
import com.mjiayou.trecore.helper.Params;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestEntity implements Serializable {

    private static final long serialVersionUID = -2329339308310833689L;

    private int methodCode;
    private String url = "";
    private String requestBody = "";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private Map<String, File> files = new HashMap<>();
    private JSONObject jsonObject = new JSONObject();

    /**
     * 构造函数
     */
    public RequestEntity(String url) {

        this.methodCode = Method.POST;
        this.url = url;

        // 公共参数-header
        headers.put("Accept-Encoding", "gzip");
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"); // application/json; charset=UTF-8
//        headers.put("Content-Type", "application/json; charset=UTF-8");
//        headers.put("Content-Type", "text/html; charset=UTF-8");
        headers.put(Params.KEY_PLATFORM, Params.VALUE_PLATFORM);

        // 公共参数-params
        params.put(Params.KEY_PLATFORM, Params.VALUE_PLATFORM);
        params.put(Params.KEY_TIME, String.valueOf(System.currentTimeMillis()));
        params.put(Params.KEY_VERSION_CODE, String.valueOf(Configs.get().getVersionCode()));
    }

    // operation

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void addFile(String key, File file) {
        files.put(key, file);
    }

    public void addParams(Map<String, String> params) {
        this.params.putAll(params);
    }

    // getter and setter

    public int getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(int methodCode) {
        this.methodCode = methodCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }
}
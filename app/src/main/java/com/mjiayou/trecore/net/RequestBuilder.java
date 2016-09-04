package com.mjiayou.trecore.net;

import android.os.Handler;
import android.os.Message;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.util.ConvertUtil;
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.StreamUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecore.widget.Configs;
import com.mjiayou.trecoredemo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class RequestBuilder {

    private Object mTagObject;
    private RequestQueue mRequestQueue;
    private Handler mResponseHandler;
    private Gson mGson;

    /**
     * 构造函数
     */
    public RequestBuilder(Object tagObject, RequestQueue requestQueue, Handler responseHandler) {
        this.mTagObject = tagObject;
        this.mRequestQueue = requestQueue;
        this.mResponseHandler = responseHandler;
        this.mGson = GsonHelper.get();
    }

    /**
     * Volley请求
     */
    public <T> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category, Listener<T> responseListener) {
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_URL + " -> " + requestEntity.getUrl());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_METHOD + " -> " + requestEntity.getMethodCode());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_HEADERS + " -> " + requestEntity.getHeaders());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_BODY + " -> " + requestEntity.getRequestBody());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_PARAMS + " -> " + requestEntity.getParams());

        if (responseListener == null) {
            responseListener = new Listener<T>() {
                @Override
                public void onResponse(T response) {
                    mResponseHandler.sendMessage(Message.obtain(null, category, response));
                }
            };
        }
        GsonRequest<T> request = new GsonRequest<>(requestEntity, new TCErrorListener(category), clazz, responseListener);
        request.setTag(mTagObject);
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(Configs.DEFAULT_TIMEOUT_MS, Configs.DEFAULT_MAX_RETRIES, Configs.DEFAULT_BACKOFF_MULT)); // 设置超时时间、重连次数
        mRequestQueue.add(request);
    }

    public <T> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category) {
        buildAndAddRequest(requestEntity, clazz, category, null);
    }

    /**
     * Http请求
     */
    public <T> void buildAndAddRequestByHttpClient(final RequestEntity requestEntity, final Class<T> clazz, final int category, final Listener<T> responseListener) {
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_URL + " -> " + requestEntity.getUrl());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_METHOD + " -> " + requestEntity.getMethodCode());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_HEADERS + " -> " + requestEntity.getHeaders());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_BODY + " -> " + requestEntity.getRequestBody());
        LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_REQUEST_PARAMS + " -> " + requestEntity.getParams());

        new Thread() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(requestEntity.getUrl());
                MultipartEntity multipartEntity = new MultipartEntity();

                try {
                    for (Map.Entry<String, String> entry : requestEntity.getParams().entrySet()) {
                        if (entry.getValue() != null) {
                            multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName(HTTP.UTF_8)));
                        }
                    }
                    for (Map.Entry<String, File> entry : requestEntity.getFiles().entrySet()) {
                        if (entry.getValue() != null) {
                            multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue()));
                        }
                    }

                    httpPost.setEntity(multipartEntity);
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity httpEntity = response.getEntity();
                    if (httpEntity != null) {
                        InputStream inputStream = null;
                        String responseString;
                        try {
                            inputStream = httpEntity.getContent();
                            responseString = ConvertUtil.convertStreamToString(inputStream);

                            T result = mGson.fromJson(responseString, clazz);
                            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE + " -- ");
                            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE_STRING + " -> " + responseString);
                            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE_OBJECT + " -> " + mGson.toJson(result));
                            responseListener.onResponse(result);
                        } catch (Exception e) {
                            LogUtil.printStackTrace(e);
                        } finally {
                            StreamUtil.closeQuietly(inputStream);
                        }
                    }
                } catch (Exception e) {
                    LogUtil.printStackTrace(e);
                }
            }
        }.start();
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mRequestQueue.cancelAll(mTagObject);
    }

    /**
     * 异常处理
     */
    private class TCErrorListener implements ErrorListener {
        private final int mCategory;

        public TCErrorListener(int category) {
            mCategory = category;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mResponseHandler.sendMessage(Message.obtain(null, mCategory, null));
            if (error instanceof NoConnectionError) {
                ToastUtil.show(TCApp.get(), TCApp.get().getString(R.string.tc_error_no_connection));
            } else if (error instanceof TimeoutError) {
                ToastUtil.show(TCApp.get(), TCApp.get().getString(R.string.tc_error_time_out));
            } else if (error instanceof ServerError) {
                ToastUtil.show(TCApp.get(), TCApp.get().getString(R.string.tc_error_server));
            } else if (error instanceof ParseError) {
                ToastUtil.show(TCApp.get(), TCApp.get().getString(R.string.tc_error_parse));
            } else {
                ToastUtil.show(TCApp.get(), TCApp.get().getResources().getString(R.string.tc_error_other) + ":" + error.toString());
            }
        }
    }
}
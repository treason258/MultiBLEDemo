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
import com.mjiayou.trecore.util.LogUtil;
import com.mjiayou.trecore.util.ToastUtil;
import com.mjiayou.trecoredemo.R;

public class RequestBuilder {

    public static final String TAG = "net_volley";

    private RequestQueue mRequestQueue;
    private Handler mHandler;
    private Object mTagObject;
    private Gson mGson;

    /**
     * 构造函数
     */
    public RequestBuilder(Object tagObject, RequestQueue queue, Handler handler) {
        this.mRequestQueue = queue;
        this.mHandler = handler;
        this.mTagObject = tagObject;
        this.mGson = GsonHelper.get();
    }

    /**
     * Volley请求
     */
    public <T> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category, Listener<T> listener) {
        LogUtil.i(TAG, "request_url -> " + requestEntity.getUrl());
        LogUtil.i(TAG, "request_method -> " + requestEntity.getMethodCode());
        LogUtil.i(TAG, "request_headers -> " + requestEntity.getHeaders());
        LogUtil.i(TAG, "request_body -> " + requestEntity.getRequestBody());
        LogUtil.i(TAG, "request_params -> " + requestEntity.getParams());

        if (listener == null) {
            listener = new Listener<T>() {
                @Override
                public void onResponse(T response) {
                    mHandler.sendMessage(Message.obtain(null, category, response));
                }
            };
        }
        GsonRequest<T> request = new GsonRequest<>(requestEntity, clazz, listener, new ErrorListenerAdapter(category));
        request.setTag(mTagObject);
        request.setShouldCache(true);
        request.setRetryPolicy(new DefaultRetryPolicy(100 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); // 设置超时时间、重连次数
        mRequestQueue.add(request);
    }

    public <T> void buildAndAddRequest(RequestEntity requestEntity, Class<T> clazz, final int category) {
        buildAndAddRequest(requestEntity, clazz, category, null);
    }

//    /**
//     * Http请求
//     */
//    public <T> void httpRequestByPostFile(final RequestEntity requestEntity, final Class<T> clazz, final int category, final Listener<T> listener) {
//        LogUtil.i(TAG, "request_url -> " + requestEntity.getUrl());
//        LogUtil.i(TAG, "request_method -> " + requestEntity.getMethodCode());
//        LogUtil.i(TAG, "request_headers -> " + requestEntity.getHeaders());
//        LogUtil.i(TAG, "request_body -> " + requestEntity.getRequestBody());
//        LogUtil.i(TAG, "request_params -> " + requestEntity.getParams());
//
//        new Thread() {
//            @Override
//            public void run() {
//                HttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(requestEntity.getUrl());
//                MultipartEntity multipartEntity = new MultipartEntity();
//
//                try {
//                    for (Map.Entry<String, String> entry : requestEntity.getParams().entrySet()) {
//                        if (entry.getValue() != null) {
//                            multipartEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName(HTTP.UTF_8)));
//                        }
//                    }
//                    for (Map.Entry<String, File> entry : requestEntity.getFiles().entrySet()) {
//                        if (entry.getValue() != null) {
//                            multipartEntity.addPart(entry.getKey(), new FileBody(entry.getValue()));
//                        }
//                    }
//
//                    httpPost.setEntity(multipartEntity);
//                    HttpResponse response = httpClient.execute(httpPost);
//                    HttpEntity httpEntity = response.getEntity();
//                    if (httpEntity != null) {
//                        InputStream inputStream = null;
//                        String responseString;
//                        try {
//                            inputStream = httpEntity.getContent();
//                            responseString = ConvertUtil.convertStreamToString(inputStream);
//
//                            LogUtil.i(TAG, "response_data -> " + responseString);
//                            listener.onResponse(mGson.fromJson(responseString, clazz));
//                        } catch (Exception e) {
//                            LogUtil.printStackTrace(e);
//                        } finally {
//                            StreamUtil.closeQuietly(inputStream);
//                        }
//                    }
//                } catch (Exception e) {
//                    LogUtil.printStackTrace(e);
//                }
//            }
//        }.start();
//    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mRequestQueue.cancelAll(mTagObject);
    }

    /**
     * 异常处理
     */
    private class ErrorListenerAdapter implements ErrorListener {
        private final int mCategory;

        public ErrorListenerAdapter(int category) {
            mCategory = category;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            mHandler.sendMessage(Message.obtain(null, mCategory, null));
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
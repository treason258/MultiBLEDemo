package com.mjiayou.trecore.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.util.ConvertUtil;
import com.mjiayou.trecore.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private RequestEntity mRequestEntity;
    private final Class<T> mClazz;
    private final Listener<T> mListener;
    private final Gson mGson;

    /**
     * 构造函数
     */
    public GsonRequest(RequestEntity requestEntity, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(requestEntity.getMethodCode(), requestEntity.getUrl(), errorListener);
        mRequestEntity = requestEntity;
        mClazz = clazz;
        mListener = listener;
        mGson = GsonHelper.get();
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        if (!TextUtils.isEmpty(mRequestEntity.getRequestBody())) {
            return mRequestEntity.getRequestBody().getBytes();
        }
        return super.getBody();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mRequestEntity.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestEntity.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json;
            String encoding = response.headers.get("Content-Encoding");
            if (!TextUtils.isEmpty(encoding) && encoding.equals("gzip")) {
                json = ConvertUtil.getRealString(response.data);
            } else {
                json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            }
            T result = mGson.fromJson(json, mClazz);
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            LogUtil.i(RequestBuilder.TAG, "response_data -- ");
            LogUtil.i(RequestBuilder.TAG, "response_data_json -> " + json);
            LogUtil.i(RequestBuilder.TAG, "response_data_result -> " + mGson.toJson(result));
            return Response.success(result, cacheEntry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}

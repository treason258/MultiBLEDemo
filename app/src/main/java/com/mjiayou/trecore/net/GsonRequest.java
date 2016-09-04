package com.mjiayou.trecore.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
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
import com.mjiayou.trecore.helper.Configs;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {

    private RequestEntity mRequestEntity;
    private final Class<T> mClazz;
    private final Listener<T> mResponseListener;
    private final Gson mGson;

    /**
     * 构造函数
     */
    public GsonRequest(RequestEntity requestEntity, ErrorListener errorListener, Class<T> clazz, Listener<T> responseListener) {
        super(requestEntity.getMethodCode(), requestEntity.getUrl(), errorListener);
        this.mRequestEntity = requestEntity;
        this.mClazz = clazz;
        this.mResponseListener = responseListener;
        this.mGson = GsonHelper.get();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mRequestEntity.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (!TextUtils.isEmpty(mRequestEntity.getRequestBody())) {
            return mRequestEntity.getRequestBody().getBytes();
        }
        return super.getBody();
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mRequestEntity.getParams();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String responseString;
            String encoding = response.headers.get("Content-Encoding");
            if (!TextUtils.isEmpty(encoding) && encoding.equals("gzip")) {
                responseString = ConvertUtil.getRealString(response.data);
            } else {
                responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            }
            T result = mGson.fromJson(responseString, mClazz);
            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE + " -- ");
            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE_STRING + " -> " + responseString);
            LogUtil.i(Configs.TAG_VOLLEY, Configs.TAG_RESPONSE_OBJECT + " -> " + mGson.toJson(result));
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
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
        mResponseListener.onResponse(response);
    }
}

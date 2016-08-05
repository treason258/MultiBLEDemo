package com.mjiayou.trecore.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.mjiayou.trecore.TCApp;
import com.mjiayou.trecore.bean.TCRequest;
import com.mjiayou.trecore.bean.TCRequestBody;
import com.mjiayou.trecore.bean.TCRequestHeader;
import com.mjiayou.trecore.bean.TCResponseBody;
import com.mjiayou.trecore.bean.TCSinaStatusesResponseBody;
import com.mjiayou.trecore.encode.SignatureUtil;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.util.AppUtil;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.UserUtil;

import java.lang.ref.WeakReference;

/**
 * 数据请求适配器
 */
public class RequestAdapter {

    // 生产服务器
    public static String SERVER_HOST = "http://api.xxx.com/";
    public static String SERVER_PATH = "hoho/";

    static {
        if (TCApp.get().DEBUG_SERVER) {
            // 测试服务器 - 阿里云
            SERVER_HOST = "http://123.57.223.153:8111/";
            SERVER_PATH = "";
        }
    }

    // 接口CATEGORY
    public static final int BASE = 0;
    public static final int SINA_STATUSES = 259;

    private final Context mContext;
    private RequestBuilder mRequestBuilder;
    private DataHandler mDataHandler;
    private Gson mGson;

    /**
     * 构造函数
     */
    public RequestAdapter(Object tagObject, Context context, DataResponse dataResponse) {
        this.mContext = context;
        Looper looper = Looper.myLooper();
        if (looper != null && mDataHandler == null) {
            this.mDataHandler = new DataHandler(dataResponse);
        }
        this.mRequestBuilder = new RequestBuilder(tagObject, TCApp.get().getRequestQueue(), mDataHandler);
        this.mGson = GsonHelper.get();
    }

    /**
     * 数据请求
     */
    public interface DataRequest {

        /**
         * 初始化数据
         */
        void initView();

        /**
         * 获取数据
         */
        void getData(int pageNumber);

        /**
         * 下拉刷新数据
         */
        void refreshData();

        /**
         * 上拉加载数据
         */
        void loadMoreData();

        /**
         * 提交数据
         */
        void submitData();
    }

    /**
     * 数据响应
     */
    public interface DataResponse {

        /**
         * 数据响应回调
         */
        void callback(Message msg);

        /**
         * 刷新页面
         */
        void refreshView(TCResponseBody responseBody);
    }

    /**
     * 回调
     */
    static class DataHandler extends Handler {
        private final WeakReference<DataResponse> mWeakReference;

        public DataHandler(DataResponse dataResponse) {
            mWeakReference = new WeakReference<>(dataResponse);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg == null) {
                return;
            }

            if (mWeakReference.get() == null) {
                return;
            }

            DataResponse dataResponse = mWeakReference.get();
            if (dataResponse != null) {
                dataResponse.callback(msg);
            }
        }
    }

    /**
     * 公共参数
     */
    public static String getSign() {
        StringBuilder builder = new StringBuilder();
//        // GET公共参数
//        builder.append("?" + Params.KEY_PLATFORM + "=" + Params.VALUE_PLATFORM);
//        builder.append("&" + Params.KEY_TIME + "=" + String.valueOf(System.currentTimeMillis()));
        return builder.toString();
    }

    /**
     * 拼接url
     */
    private String getUrl(String category) {
        String url = SERVER_HOST + SERVER_PATH + category;
        if (!TextUtils.isEmpty(getSign())) {
            url += getSign();
        }
        return url;
    }

    /**
     * 获取请求数据
     */
    private String getRequestBodyString(TCRequestBody request) {
        return mGson.toJson(request);
    }

    /**
     * 获取请求数据
     */
    private String getRequestString(TCRequestBody requestBody) {
        String requestId = DeviceUtil.getUUID();
        String tokenId = UserUtil.getToken();
        String appVersion = String.valueOf(AppUtil.getVersionCode(TCApp.get()));
        String signature = SignatureUtil.getSignature(requestId);

        TCRequest request = new TCRequest();
        request.setHeader(new TCRequestHeader(requestId, tokenId, appVersion, signature));
        request.setBody(requestBody);

        return mGson.toJson(request);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        mRequestBuilder.cancelAll();
    }

    // ******************************** TEST ********************************

    /**
     * 0-1.BASE
     */
    public void base1(String base) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.addParam("base", base);
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponseBody.class, BASE);
    }

    /**
     * 0-2.BASE
     */
    public void base2(TCRequestBody request) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.addParam("base", getRequestBodyString(request));
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponseBody.class, BASE);
    }

    /**
     * 0-3.BASE
     */
    public void base3(TCRequestBody request) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.setRequestBody(getRequestBodyString(request));
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponseBody.class, BASE);
    }

    /**
     * 0-4.新浪微博
     * https://api.weibo.com/2/statuses/public_timeline.json?source=812913819&count=20
     */
    public void sinaStatuses() {
        String url = "https://api.weibo.com/2/statuses/public_timeline.json?source=812913819&count=10";
        RequestEntity requestEntity = new RequestEntity(url);
        requestEntity.setMethodCode(Method.GET);
        mRequestBuilder.buildAndAddRequest(requestEntity, TCSinaStatusesResponseBody.class, SINA_STATUSES);
    }

    // ******************************** custom ********************************
}
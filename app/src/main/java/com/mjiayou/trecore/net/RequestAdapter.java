package com.mjiayou.trecore.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.mjiayou.trecore.bean.TCRequest;
import com.mjiayou.trecore.bean.TCResponse;
import com.mjiayou.trecore.bean.TCSinaStatusesResponse;
import com.mjiayou.trecore.encode.SignatureUtil;
import com.mjiayou.trecore.helper.Configs;
import com.mjiayou.trecore.helper.GsonHelper;
import com.mjiayou.trecore.helper.Params;
import com.mjiayou.trecore.helper.VolleyHelper;
import com.mjiayou.trecore.util.DeviceUtil;
import com.mjiayou.trecore.util.UserUtil;

import java.lang.ref.WeakReference;

/**
 * 数据请求适配器
 */
public class RequestAdapter {

    // 生产服务器
    public static String SERVER_HOST = "http://api.soccerapp.cn/";
    public static String SERVER_PATH = "hoho/";

    static {
        if (Configs.DEBUG_SERVER) {
            // 测试服务器
            SERVER_HOST = "http://123.57.223.153:8111/";
            SERVER_PATH = "";
        }
    }

    // 接口CATEGORY
    public static final int BASE = 258;
    public static final int SINA_STATUSES = 259;

    private final Context mContext;
    private ResponseHandler mResponseHandler;
    private RequestBuilder mRequestBuilder;
    private Gson mGson;

    /**
     * 构造函数
     */
    public RequestAdapter(Context context, DataResponse dataResponse) {
        this.mContext = context;

        if (mResponseHandler == null && Looper.myLooper() != null) {
            this.mResponseHandler = new ResponseHandler(dataResponse);
        }
        this.mRequestBuilder = new RequestBuilder(context, VolleyHelper.get().getRequestQueue(), mResponseHandler);
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
        void refreshView(TCResponse response);
    }

    /**
     * 回调
     */
    static class ResponseHandler extends Handler {
        private final WeakReference<DataResponse> mWeakReference;

        public ResponseHandler(DataResponse dataResponse) {
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
//        builder.append("&" + Params.KEY_VERSION_CODE + "=" + String.valueOf(Configs.getVersionCode()));
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
    private String getRequestString(TCRequest request) {
        String requestId = DeviceUtil.getUUID();
        String tokenId = UserUtil.getToken();
        String appVersion = String.valueOf(Configs.get().getVersionCode());
        String signature = SignatureUtil.getSignature(requestId);

        request.setRequestId(requestId);
        request.setTokenId(tokenId);
        request.setAppVersion(appVersion);
        request.setSignature(signature);

        return mGson.toJson(request);
    }
//
//    /**
//     * 获取请求数据
//     */
//    private String getRequestBundleString(TCRequestBundleBody requestBody) {
//        String requestId = DeviceUtil.getUUID();
//        String tokenId = UserUtil.getToken();
//        String appVersion = String.valueOf(Configs.get().getVersionCode());
//        String signature = SignatureUtil.getSignature(requestId);
//
//        TCRequestBundle request = new TCRequestBundle();
//        request.setHeader(new TCRequestBundleHeader(requestId, tokenId, appVersion, signature));
//        request.setBody(requestBody);
//
//        return mGson.toJson(request);
//    }

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
    public void base1(String param1, String param2) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.addParam("param1", param1);
        requestEntity.addParam("param2", param2);
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponse.class, BASE);
    }

    /**
     * 0-2.BASE
     */
    public void base2(TCRequest request) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.addParam("param1", request.getParam1());
        requestEntity.addParam("param2", request.getParam2());
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponse.class, BASE);
    }

    /**
     * 0-3.BASE
     */
    public void base3(TCRequest request) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.addParam(Params.KEY_PCONTENT, getRequestString(request));
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponse.class, BASE);
    }

//    /**
//     * 0-4.BASE
//     */
//    public void base4(TCRequestBundleBody requestBundleBody) {
//        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
//        requestEntity.addParam(Params.KEY_PCONTENT, getRequestBundleString(requestBundleBody));
//        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponse.class, BASE);
//    }

    /**
     * 0-5.BASE
     */
    public void base5(TCRequest request) {
        RequestEntity requestEntity = new RequestEntity(getUrl("base"));
        requestEntity.setRequestBody(getRequestString(request));
        mRequestBuilder.buildAndAddRequest(requestEntity, TCResponse.class, BASE);
    }

    /**
     * 0-4.新浪微博
     * https://api.weibo.com/2/statuses/public_timeline.json?source=812913819&count=20
     */
    public void sinaStatuses() {
        String url = "https://api.weibo.com/2/statuses/public_timeline.json?source=812913819&count=10";
        RequestEntity requestEntity = new RequestEntity(url);
        requestEntity.setMethodCode(Method.GET);
        mRequestBuilder.buildAndAddRequest(requestEntity, TCSinaStatusesResponse.class, SINA_STATUSES);
    }

    // ******************************** project ********************************
}
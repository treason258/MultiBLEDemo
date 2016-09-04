package com.mjiayou.trecore.helper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/5/14.
 */
public class VolleyHelper {

    private static final String TAG = "VolleyHelper";

    private static VolleyHelper mInstance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    /**
     * 单例模式，获取实例
     */
    public static VolleyHelper get() {
        if (mInstance == null) {
            synchronized (VolleyHelper.class) {
                if (mInstance == null) {
                    mInstance = new VolleyHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);

        mContext = context;
        VolleyLog.DEBUG = Configs.DEBUG_VOLLEY;
    }

    /**
     * 获取RequestQueue对象
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (VolleyHelper.class) {
                mRequestQueue = Volley.newRequestQueue(mContext);
            }
        }
        return mRequestQueue;
    }
}

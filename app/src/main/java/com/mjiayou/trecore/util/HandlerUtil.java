package com.mjiayou.trecore.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by treason on 15-11-10.
 */
public class HandlerUtil {

    private static HandlerUtil mHandlerUtil;
    private static Handler mHandler;

    /**
     * 构造函数
     */
    private HandlerUtil() {
    }

    /**
     * 单例模式，获取SharedUtil实例
     */
    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    /**
     * destory
     */
    public static void destory() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 延迟操作
     */
    public static void postDelayed(Runnable runnable, long delayMillis) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, delayMillis);
    }

    /**
     * 更新UI线程
     */
    public static void runOnUiThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }
}

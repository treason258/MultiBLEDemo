package com.mjiayou.trecore.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于临时存放中间量
 *
 * @author treason
 * @date 2014年4月12日
 */
public class SessionUtil {

    private static final String TAG = "SessionUtil";

    private static SessionUtil mSessionUtil;
    private Map<Object, Object> mObjectContainer;

    // Attention here, DO NOT USE keyword 'new' to create this object.
    // Instead, use getSession method.

    /**
     * 构造函数
     */
    private SessionUtil() {
        mObjectContainer = new HashMap<>();
    }

    /**
     * 单例模式，获取实例
     */
    public static SessionUtil get() {
        if (mSessionUtil == null) {
            mSessionUtil = new SessionUtil();
        }
        return mSessionUtil;
    }

    // ******************************** 封装 ********************************

    /**
     * 日志规范
     */
    private void logPut(Object key, Object value) {
        LogUtil.i(TAG, "PUT " + key + " -> " + value);
    }

    private void logPutFailed() {
        LogUtil.i(TAG, "SET failed");
    }

    private void logGet(Object key, Object value) {
        LogUtil.i(TAG, "GET " + key + " -> " + value);
    }

    private void logGetFailed() {
        LogUtil.i(TAG, "GET failed");
    }

    private void logRemove(Object key) {
        LogUtil.i(TAG, "REMOVE " + key);
    }

    private void logRemoveFailed() {
        LogUtil.i(TAG, "REMOVE failed");
    }

    private void logClean() {
        LogUtil.i(TAG, "CLEAN succeed");
    }

    private void logCleanFailed() {
        LogUtil.i(TAG, "CLEAN failed");
    }

    /**
     * 保存数据
     */
    public void put(Object key, Object value) {
        if (key != null && value != null) {
            mObjectContainer.put(key, value);
            logPut(key, value);
        } else {
            logPutFailed();
        }
    }

    /**
     * 读取数据
     */
    public Object get(Object key) {
        try {
            Object value = mObjectContainer.get(key);
            logGet(key, value);
            return value;
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logGetFailed();
        }
        return null;
    }

    /**
     * 移除数据
     */
    public void remove(Object key) {
        try {
            mObjectContainer.remove(key);
            logRemove(key);
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logRemoveFailed();
        }
    }

    /**
     * 清除全部数据
     */
    public void clean() {
        try {
            mObjectContainer.clear();
            logClean();
        } catch (Exception e) {
            LogUtil.printStackTrace(e);
            logCleanFailed();
        }
    }
}
 
 
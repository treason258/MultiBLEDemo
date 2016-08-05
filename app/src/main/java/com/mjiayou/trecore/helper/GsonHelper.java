package com.mjiayou.trecore.helper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mjiayou.trecore.util.LogUtil;

/**
 * Created by treason on 16/5/14.
 */
public class GsonHelper {

    private static final String TAG = "GsonHelper";

    /**
     * 初始化
     */
    public static void init(Context context) {
        LogUtil.i("初始化数据 -> " + TAG);
    }

    /**
     * 获取gson对象
     */
    public static Gson get() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }
}
